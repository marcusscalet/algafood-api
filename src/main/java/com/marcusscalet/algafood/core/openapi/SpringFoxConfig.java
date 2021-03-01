package com.marcusscalet.algafood.core.openapi;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.classmate.TypeResolver;
import com.marcusscalet.algafood.api.exceptionhandler.Problem;
import com.marcusscalet.algafood.api.v1.model.CityModel;
import com.marcusscalet.algafood.api.v1.model.CuisineModel;
import com.marcusscalet.algafood.api.v1.model.GroupModel;
import com.marcusscalet.algafood.api.v1.model.OrderSummaryModel;
import com.marcusscalet.algafood.api.v1.model.PaymentMethodModel;
import com.marcusscalet.algafood.api.v1.model.PermissionModel;
import com.marcusscalet.algafood.api.v1.model.ProductModel;
import com.marcusscalet.algafood.api.v1.model.RestaurantBasicModel;
import com.marcusscalet.algafood.api.v1.model.StateModel;
import com.marcusscalet.algafood.api.v1.model.UserModel;
import com.marcusscalet.algafood.api.v1.openapi.model.CitiesModelOpenApi;
import com.marcusscalet.algafood.api.v1.openapi.model.CuisinesModelOpenApi;
import com.marcusscalet.algafood.api.v1.openapi.model.GroupsModelOpenApi;
import com.marcusscalet.algafood.api.v1.openapi.model.LinksModelOpenApi;
import com.marcusscalet.algafood.api.v1.openapi.model.OrdersSummaryModelOpenApi;
import com.marcusscalet.algafood.api.v1.openapi.model.PageableModelOpenApi;
import com.marcusscalet.algafood.api.v1.openapi.model.PaymentMethodsModelOpenApi;
import com.marcusscalet.algafood.api.v1.openapi.model.PermissionsModelOpenApi;
import com.marcusscalet.algafood.api.v1.openapi.model.ProductsModelOpenApi;
import com.marcusscalet.algafood.api.v1.openapi.model.RestaurantsBasicModelOpenApi;
import com.marcusscalet.algafood.api.v1.openapi.model.StatesModelOpenApi;
import com.marcusscalet.algafood.api.v1.openapi.model.UsersModelOpenApi;
import com.marcusscalet.algafood.api.v2.model.CuisineModelV2;
import com.marcusscalet.algafood.api.v2.openapi.model.CuisinesModelV2OpenApi;

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
	public Docket apiDocketV1() {
		var typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.OAS_30)
				.groupName("V1")
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.marcusscalet.algafood.api"))
					.paths(PathSelectors.ant("/v1/**"))
					.build()
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalGetResponses())
				.globalResponses(HttpMethod.POST, globalPostPutResponses())
				.globalResponses(HttpMethod.PUT, globalPostPutResponses())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponses())
				.additionalModels(typeResolver.resolve(Problem.class))
				.ignoredParameterTypes(ServletWebRequest.class)
				.ignoredParameterTypes(ServletWebRequest.class,
						URL.class, URI.class, URLStreamHandler.class, Resource.class,
						File.class, InputStream.class)
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, CuisineModel.class),
						CuisinesModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, OrderSummaryModel.class),
						OrdersSummaryModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, CityModel.class),
						CitiesModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, StateModel.class),
						StatesModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, PaymentMethodModel.class),
						PaymentMethodsModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, GroupModel.class),
						GroupsModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, PermissionModel.class),
						PermissionsModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, ProductModel.class),
						ProductsModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, RestaurantBasicModel.class),
						RestaurantsBasicModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, UserModel.class),
						UsersModelOpenApi.class))
			.apiInfo(apiInfoV1())
			
			.tags(tagsV1()[0], tagsV1());
	}
	
	@Bean
	public Docket apiDocketV2() {
		var typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.OAS_30)
				.groupName("V2")
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.marcusscalet.algafood.api"))
					.paths(PathSelectors.ant("/v2/**"))
					.build()
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalGetResponses())
				.globalResponses(HttpMethod.POST, globalPostPutResponses())
				.globalResponses(HttpMethod.PUT, globalPostPutResponses())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponses())
				.additionalModels(typeResolver.resolve(Problem.class))
				.ignoredParameterTypes(ServletWebRequest.class)
				.ignoredParameterTypes(ServletWebRequest.class,
						URL.class, URI.class, URLStreamHandler.class, Resource.class,
						File.class, InputStream.class)
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
						
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, CuisineModelV2.class),
						CuisinesModelV2OpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, CuisineModelV2.class),
						CuisinesModelV2OpenApi.class))
				
				.apiInfo(apiInfoV2())
	
				.tags(new Tag("Cities", "Manage cities"),
						new Tag("Cuisines", "Manage cuisines"));
	}
			
	
	private Tag[] tagsV1(){
		return new Tag[] {
				new Tag("Cities", "Manage cities"),
				new Tag("Groups", "Manage groups"),
				new Tag("Cuisines", "Manage cuisines"),
				new Tag("Payment Methods", "Manage payment methods"),
				new Tag("Orders", "Manage orders"),
				new Tag("Restaurants", "Manage restaurants"),
				new Tag("States", "Manage states"),
				new Tag("Products", "Manage products from restaurants"),
				new Tag("Users", "Manage users"),
		        new Tag("Statistics", "Algafood Statistics"),
		        new Tag("Permissions", "Manage permissions")
		};
	}
	
	private ApiInfo apiInfoV1() {
		return new ApiInfoBuilder()
				.title("Algafood API (Depreciada)")
				.description("API aberta para clientes e restaurantes. <br>"
						+ "<strong>Essa versão da API está depreciada  e deixará de existir a partir de 02/02/2022.<br>"
						+ "Use a versão mais atual da API.")
				.version("1.0")
				.contact(new Contact("Marcus", "https://www.algaworks.com", "contato@algaworks.com"))
				.build();
	}
	
	private ApiInfo apiInfoV2() {
		return new ApiInfoBuilder()
				.title("Algafood API")
				.description("API aberta para clientes e restaurantes")
				.version("2.0")
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
