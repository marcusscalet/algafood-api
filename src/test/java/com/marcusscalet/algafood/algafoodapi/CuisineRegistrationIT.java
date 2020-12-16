package com.marcusscalet.algafood.algafoodapi;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CuisineRegistrationIT {

	@LocalServerPort
	private int port;
	
	@Autowired
	private Flyway flyway;
	
	@BeforeEach
	public void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cuisines";
		
		flyway.migrate();
	}
	
	@Test
	void mustReturnStatus200WhenQueryByCuisines() {
				
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	void mustContain4CuisinesWhenQueryByCuisines() {
		
		given()
		.accept(ContentType.JSON)
	.when()
		.get()
	.then()
		.body("", hasSize(4))
		.body("name", hasItems("Indiana", "Tailandesa"));
	}
	
	@Test
	public void mustReturnStatus201WhenRegisteringCuisine() {
		given()
			.body("{\"name\": \"Chinesa\"}")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
			}
}
