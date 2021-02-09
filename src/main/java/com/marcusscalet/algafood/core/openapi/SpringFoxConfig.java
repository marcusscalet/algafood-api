package com.marcusscalet.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.classmate.TypeResolver;
import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.model.CuisineDTO;
import com.marcusscalet.algafood.api.model.OrderSummaryDTO;
import com.marcusscalet.algafood.api.openapi.model.CuisinesDTOOpenApi;
import com.marcusscalet.algafood.api.openapi.model.OrdersSummaryDTOOpenApi;
import com.marcusscalet.algafood.api.openapi.model.PageableModelOpenApi;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

	@Bean
	public Docket apiDocket() {

		var typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.OAS_30).select()
				.apis(RequestHandlerSelectors.basePackage("com.marcusscalet.algafood.api"))
				.paths(PathSelectors.any())
				.build()
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalGetResponses())
				.globalResponses(HttpMethod.POST, globalPostPutResponses())
				.globalResponses(HttpMethod.PUT, globalPostPutResponses())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponses())
				.additionalModels(typeResolver.resolve(Problem.class))
				.ignoredParameterTypes(ServletWebRequest.class)
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(Page.class, CuisineDTO.class), CuisinesDTOOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(Page.class, OrderSummaryDTO.class), OrdersSummaryDTOOpenApi.class))
			.apiInfo(apiInfo())
			.tags(tags()[0], tags());
	}
	
	private Tag[] tags(){
		return new Tag[] {
				new Tag("City", "Manage cities"),
				new Tag("Group", "Manage groups"),
				new Tag("Cuisine", "Manage cuisines"),
				new Tag("Payment Method", "Manage payment methods"),
				new Tag("Order", "Manage orders"),
				new Tag("Restaurant", "Manage restaurants"),
				new Tag("State", "Manage states"),
				new Tag("Product", "Manage products from restaurants")
		};
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Algafood API")
				.description("API aberta para clientes e restaurantes")
				.version("1.0")
				.contact(new Contact("Marcus", "https://www.algaworks.com", "contato@algaworks.com"))
				.build();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("index.html").addResourceLocations("classpath:/META-INF/resources/");
	}
	
	private List<Response> globalGetResponses() {
		return Arrays.asList(
				new ResponseBuilder()
				.code(codeDescription(HttpStatus.BAD_REQUEST))
				.description("Requisição inválida (erro do cliente)")
				.representation(MediaType.APPLICATION_JSON )
                .apply(builderModelProblem())
				.build(),
				new ResponseBuilder()
					.code(codeDescription(HttpStatus.INTERNAL_SERVER_ERROR))
					.description("Erro interno do servidor")
					.representation(MediaType.APPLICATION_JSON)
					.apply(builderModelProblem())
					.build(),
				new ResponseBuilder()
					.code(codeDescription(HttpStatus.NOT_ACCEPTABLE))
					.description("Recurso não possui representação que poderia ser aceita pelo consumidor")
					.build()
			);
	}
	
	private List<Response> globalPostPutResponses() {
		return Arrays.asList(
				new ResponseBuilder()
					.code(codeDescription(HttpStatus.INTERNAL_SERVER_ERROR))
					.description("Erro interno do Servidor")
					.representation(MediaType.APPLICATION_JSON)
					.apply(builderModelProblem())
					.build(),
					new ResponseBuilder()
					.code(codeDescription(HttpStatus.NOT_ACCEPTABLE))
					.description("Recurso não possui representação que pode ser aceita pelo consumidor")
					.build(),
					new ResponseBuilder()
					.code(codeDescription(HttpStatus.BAD_REQUEST))
					.description("Requisição inválida (erro do cliente)")
					.representation(MediaType.APPLICATION_JSON)
					.apply(builderModelProblem())
					.build(),
					new ResponseBuilder() 
					.code(codeDescription(HttpStatus.UNSUPPORTED_MEDIA_TYPE))
					.description("Requisição recusada porque o corpo está em um formato não suportado")
					.representation(MediaType.APPLICATION_JSON)
					.apply(builderModelProblem())
					.build());
	}

	private List<Response> globalDeleteResponses() {
		return Arrays.asList(
				new ResponseBuilder()
					.code(codeDescription(HttpStatus.BAD_REQUEST))
					.description("Requisição inválida (erro do cliente)")
					.representation(MediaType.APPLICATION_JSON)
					.apply(builderModelProblem())
					.build(),
				new ResponseBuilder()
					.code(codeDescription(HttpStatus.INTERNAL_SERVER_ERROR))
					.description("Erro interno no servidor")
					.representation(MediaType.APPLICATION_JSON)
					.apply(builderModelProblem())
					.build()
			);
	}
	
	private Consumer<RepresentationBuilder> builderModelProblem() {
		return r->r.model(m->m.name("Problem")
				.referenceModel(
						ref->ref.key(
								k->k.qualifiedModelName(
										q->q.name("Problem")
										.namespace("com.marcusscalet.algafood.api.exceptionhandler")
										))));
	}

	private String codeDescription(HttpStatus httpStatus) {
		return String.valueOf(httpStatus.value());
	}
}
