
package market.rest;

import market.domain.Cart;
import market.dto.CartDTO;
import market.dto.CartItemDTO;
import market.exception.EmptyCartException;
import market.exception.UnknownEntityException;
import market.service.CartService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.evomaster.client.java.controller.SutHandler;
import java.util.Arrays;
import java.security.Principal;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class run01_CartRestControllerTest {

    private static final SutHandler controller = new em.embedded.market.EmbeddedEvoMasterController();
    private static String baseUrlOfSut;

    @BeforeAll
    public static void initClass() {
        controller.setupForGeneratedTest();
        baseUrlOfSut = controller.startSut();
        controller.registerOrExecuteInitSqlCommandsIfNeeded();
        assertNotNull(baseUrlOfSut);
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.urlEncodingEnabled = false;
        RestAssured.config = RestAssured.config()
                .jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.DOUBLE))
                .redirect(redirectConfig().followRedirects(false));
    }

    @AfterAll
    public static void tearDown() {
        controller.stopSut();
    }

    @BeforeEach
    public void initTest() {
        controller.resetDatabase(Arrays.asList("USER_ROLE","CUSTOMER_ORDER","CART_ITEM","PRODUCT","CART","CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    public void testGetCart() {
        // Test to get the cart
        given()
            .when()
            .get(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200);
        // Add more assertions for schema validation if needed
    }

    @Test
    public void testAddItem() {
        // Test to add an item to the cart
        CartItemDTO item = new CartItemDTO();
        // Set item details
        given()
            .contentType("application/json")
            .body(item)
            .when()
            .put(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200);
        // Add more assertions for schema validation if needed
    }

    @Test
    public void testClearCart() {
        // Test to clear the cart
        given()
            .when()
            .delete(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200);
        // Add more assertions for schema validation if needed
    }

    @Test
    public void testSetDelivery() {
        // Test to set delivery option
        given()
            .queryParam("included", true)
            .when()
            .put(baseUrlOfSut + "/customer/cart/delivery")
            .then()
            .statusCode(200);
        // Add more assertions for schema validation if needed
    }

    @Test
    public void testPayByCard() {
        // Test to pay by card
        given()
            .contentType("application/json")
            .body(new CreditCardDTO())
            .when()
            .post(baseUrlOfSut + "/customer/cart/pay")
            .then()
            .statusCode(201);
        // Add more assertions for schema validation if needed
    }

    // Additional tests for error scenarios, schema validation, and business rule enforcement can be added here

}
