package com.marcusscalet.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.marcusscalet.algafood.domain.model.Cuisine;
import com.marcusscalet.algafood.domain.repository.CuisineRepository;
import com.marcusscalet.algafood.util.DatabaseCleaner;
import com.marcusscalet.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CuisineRegistrationIT {

	private static final int CUISINE_ID_NON_EXISTENT = 100;

	private Cuisine italianCuisine;
	private int cuisineQty;
	private String jsonCuisine;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CuisineRepository cuisineRepository;
	
	@BeforeEach
	public void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cuisines";
		
		databaseCleaner.clearTables();
		

		jsonCuisine = ResourceUtils.getContentFromResource(
				"/json/correct/cuisine.json");
		
		prepareData();
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
	void mustReturnQtyOfCuisines_WhenQueryByAllCuisines() {
		
		given()
		.accept(ContentType.JSON)
	.when()
		.get()
	.then()
		.body("", hasSize(cuisineQty));
	}
	
	@Test
	public void mustReturnStatus201_WhenRegisteringCuisine() {
		given()
			.body(jsonCuisine)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void mustReturnCorrectResponseAndStatus_WhenQueryByCuisineThatExists() {
		given()
			.pathParam("cuisineId", italianCuisine.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cuisineId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("name",  equalTo(italianCuisine.getName()));
	}
	
	@Test
	public void mustReturnStatus404_WhenQueryByCuisineThatNotExists() {
		given()
			.pathParam("cuisineId", CUISINE_ID_NON_EXISTENT)
			.accept(ContentType.JSON)
		.when()
			.get("/{cuisineId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornarStatus409_QuandoTentarRemoverCozinhaEmUso() {
		RestAssured.given()
			.pathParam("cuisineId", 1)
			.accept(ContentType.JSON)
		.when()
			.delete("/{cuisineId}")
		.then()
			.statusCode(HttpStatus.CONFLICT.value());
		
	}
	
	private void prepareData() {
		Cuisine thaiCuisine = new Cuisine();
		thaiCuisine.setName("Tailandesa");
		cuisineRepository.save(thaiCuisine);
		

		italianCuisine = new Cuisine();
		italianCuisine.setName("Italiana");
		cuisineRepository.save(italianCuisine);

	    cuisineQty = (int) cuisineRepository.count();
	}
}
