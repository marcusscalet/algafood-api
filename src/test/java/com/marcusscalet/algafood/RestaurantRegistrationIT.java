package com.marcusscalet.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;

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
import com.marcusscalet.algafood.domain.model.Restaurant;
import com.marcusscalet.algafood.domain.repository.CuisineRepository;
import com.marcusscalet.algafood.domain.repository.RestaurantRepository;
import com.marcusscalet.algafood.util.DatabaseCleaner;
import com.marcusscalet.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RestaurantRegistrationIT {

    private static final String BUSINESS_RULE_VIOLATION = "Violação de regra de negócio";

    private static final String INVALID_DATA_PROBLEM_TITLE = "Dados inválidos";

    private static final int RESTAURANT_ID_NON_EXISTENT = 100;

    @LocalServerPort
    private int port;
    
    @Autowired
    private DatabaseCleaner databaseCleaner;
    
    @Autowired
    private CuisineRepository cuisineRepository;
    
    @Autowired
    private RestaurantRepository restaurantRepository;
    
    private String jsonCorrectRestaurant;
    private String jsonRestaurantWithoutShippingFee;
    private String jsonRestaurantWithoutCuisine;
    private String jsonRestaurantWithNonExistentCuisine;
    
    private Restaurant burgerTopRestaurant;
    
    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurants";

        jsonCorrectRestaurant = ResourceUtils.getContentFromResource(
                "/json/correct/restaurant-new-york-barbecue.json");
        
        jsonRestaurantWithoutShippingFee = ResourceUtils.getContentFromResource(
                "/json/incorrect/restaurant-new-york-barbecue-without-shippingFee.json");
        
        jsonRestaurantWithoutCuisine = ResourceUtils.getContentFromResource(
                "/json/incorrect/restaurant-new-york-barbecue-without-cuisine.json");
        
        jsonRestaurantWithNonExistentCuisine = ResourceUtils.getContentFromResource(
                "/json/incorrect/restaurant-new-york-barbecue-with-non-existent-cuisine.json");
        
        databaseCleaner.clearTables();
        prepareData();
    }
    
    @Test
    public void mustReturnStatus200_WhenQueryByRestaurants() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }
    
    @Test
    public void mustReturnStatus201_WhenRegisteringRestaurant() {
        given()
            .body(jsonCorrectRestaurant)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }
    
    @Test
    public void mustReturnStatus400_WhenRegisteringRestaurantWithouShippingFee() {
        given()
            .body(jsonRestaurantWithoutShippingFee)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(INVALID_DATA_PROBLEM_TITLE));
    }

    @Test
    public void mustReturnStatus400_WhenRegisteringRestaurantWithouCuisine() {
        given()
            .body(jsonRestaurantWithoutCuisine)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(INVALID_DATA_PROBLEM_TITLE));
    }
    
    @Test
    public void mustReturnStatus400_WhenRegisteringRestaurantWithNonExistentCuisine() {
        given()
            .body(jsonRestaurantWithNonExistentCuisine)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(BUSINESS_RULE_VIOLATION));
    }
    
    @Test
    public void mustReturnCorrectStatusAndResponse_WhenQueryByExistentRestaurant() {
        given()
            .pathParam("restaurantId", burgerTopRestaurant.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{restaurantId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("name", equalTo(burgerTopRestaurant.getName()));
    }
    
    @Test
    public void mustReturnStatus404_WhenQueryByNonExistentRestaurant() {
        given()
            .pathParam("restaurantId", RESTAURANT_ID_NON_EXISTENT)
            .accept(ContentType.JSON)
        .when()
            .get("/{restaurantId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
    
    private void prepareData() {
        Cuisine brazilianCuisine = new Cuisine();
        brazilianCuisine.setName("Brasileira");
        cuisineRepository.save(brazilianCuisine);

        Cuisine americanCuisine = new Cuisine();
        americanCuisine.setName("Americana");
        cuisineRepository.save(americanCuisine);
        
        burgerTopRestaurant = new Restaurant();
        burgerTopRestaurant.setName("Burger Top");
        burgerTopRestaurant.setShippingFee(new BigDecimal(10));
        burgerTopRestaurant.setCuisine(americanCuisine);
        restaurantRepository.save(burgerTopRestaurant);
        
        Restaurant mineirosRestaurant = new Restaurant();
        mineirosRestaurant.setName("Comida Mineira");
        mineirosRestaurant.setShippingFee(new BigDecimal(10));
        mineirosRestaurant.setCuisine(brazilianCuisine);
        restaurantRepository.save(mineirosRestaurant);
    }            
}