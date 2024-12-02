
package market;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import java.util.List;
import static org.evomaster.client.java.controller.api.EMTestUtils.*;
import org.evomaster.client.java.controller.SutHandler;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.ValidatableResponse;
import static org.evomaster.client.java.sql.dsl.SqlDsl.sql;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionResultsDto;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionDto;
import static org.hamcrest.Matchers.*;
import io.restassured.config.JsonConfig;
import io.restassured.path.json.config.JsonPathConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static org.evomaster.client.java.contentMatchers.NumberMatcher.*;
import static org.evomaster.client.java.contentMatchers.StringMatcher.*;
import static org.evomaster.client.java.contentMatchers.SubStringMatcher.*;
import static org.evomaster.client.java.controller.expect.ExpectationHandler.expectationHandler;
import io.restassured.path.json.JsonPath;
import java.util.Arrays;

public class run01_RestErrorResponseTest {

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
    public void testGetCustomer() {
        ValidatableResponse response = given()
            .queryParam("name", "invalid_user")
            .get(baseUrlOfSut + "/customer")
            .then()
            .statusCode(404);
        
        response
            .body("message", equalTo("User not found"));
    }

    @Test
    public void testGetCart() {
        ValidatableResponse response = given()
            .queryParam("name", "invalid_user")
            .get(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(404);
        
        response
            .body("message", equalTo("Cart not found"));
    }

    @Test
    public void testAddItemToCart() {
        ValidatableResponse response = given()
            .queryParam("name", "invalid_user")
            .body("{\"productId\": 999, \"quantity\": 1}")
            .put(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(404);
        
        response
            .body("message", equalTo("User not found"));
    }

    @Test
    public void testClearCart() {
        ValidatableResponse response = given()
            .queryParam("name", "invalid_user")
            .delete(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(404);
        
        response
            .body("message", equalTo("User not found"));
    }

    @Test
    public void testSetDelivery() {
        ValidatableResponse response = given()
            .queryParam("name", "invalid_user")
            .queryParam("included", true)
            .put(baseUrlOfSut + "/customer/cart/delivery")
            .then()
            .statusCode(404);
        
        response
            .body("message", equalTo("User not found"));
    }

    @Test
    public void testPayByCard() {
        ValidatableResponse response = given()
            .queryParam("name", "invalid_user")
            .body("{\"ccNumber\": \"1234-5678-9876-5432\"}")
            .post(baseUrlOfSut + "/customer/cart/pay")
            .then()
            .statusCode(404);
        
        response
            .body("message", equalTo("User not found"));
    }

    @Test
    public void testGetContacts() {
        ValidatableResponse response = given()
            .queryParam("name", "invalid_user")
            .get(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(404);
        
        response
            .body("message", equalTo("User not found"));
    }

    @Test
    public void testUpdateContacts() {
        ValidatableResponse response = given()
            .queryParam("name", "invalid_user")
            .body("{\"address\":\"New Address\",\"phone\":\"+7 123 456 78 90\"}")
            .put(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(404);
        
        response
            .body("message", equalTo("User not found"));
    }

    @Test
    public void testGetOrders() {
        ValidatableResponse response = given()
            .queryParam("name", "invalid_user")
            .get(baseUrlOfSut + "/customer/orders")
            .then()
            .statusCode(404);
        
        response
            .body("message", equalTo("User not found"));
    }

    @Test
    public void testGetOrder() {
        ValidatableResponse response = given()
            .queryParam("name", "invalid_user")
            .pathParam("orderId", 999)
            .get(baseUrlOfSut + "/customer/orders/{orderId}")
            .then()
            .statusCode(404);
        
        response
            .body("message", equalTo("Order not found"));
    }

    @Test
    public void testGetProducts() {
        ValidatableResponse response = given()
            .get(baseUrlOfSut + "/products")
            .then()
            .statusCode(200);
        
        response
            .body("size()", greaterThan(0));
    }

    @Test
    public void testGetProduct() {
        ValidatableResponse response = given()
            .pathParam("productId", 999)
            .get(baseUrlOfSut + "/products/{productId}")
            .then()
            .statusCode(404);
        
        response
            .body("message", equalTo("Product not found"));
    }

    @Test
    public void testCreateCustomer() {
        ValidatableResponse response = given()
            .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(201);
        
        response
            .body("email", equalTo("ivan.petrov@yandex.ru"));
    }
}
