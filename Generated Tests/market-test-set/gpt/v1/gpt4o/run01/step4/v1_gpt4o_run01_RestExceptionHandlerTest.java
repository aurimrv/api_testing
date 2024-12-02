
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
import static org.evomaster.client.java.controller.contentMatchers.NumberMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.StringMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.SubStringMatcher.*;
import static org.evomaster.client.java.controller.expect.ExpectationHandler.expectationHandler;
import org.evomaster.client.java.controller.expect.ExpectationHandler;
import io.restassured.path.json.JsonPath;
import java.util.Arrays;

public class v1_gpt4o_run01_RestExceptionHandlerTest {
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
    public void testGetCustomer_200() {
        given()
            .queryParam("name", "Ivan Petrov")
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(200)
            .body("email", equalTo("ivan.petrov@yandex.ru"))
            .body("name", equalTo("Ivan Petrov"))
            .body("phone", equalTo("+7 123 456 78 90"))
            .body("address", equalTo("Riesstrasse 18"));
    }

    @Test
    public void testGetCustomer_404() {
        given()
            .queryParam("name", "Nonexistent User")
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(404);
    }

    @Test
    public void testGetCart_200() {
        given()
            .queryParam("name", "Ivan Petrov")
        .when()
            .get(baseUrlOfSut + "/customer/cart")
        .then()
            .statusCode(200);
    }

    @Test
    public void testAddItemToCart_200() {
        given()
            .queryParam("name", "Ivan Petrov")
            .body("{ \"productId\": 1, \"quantity\": 2 }")
            .contentType("application/json")
        .when()
            .put(baseUrlOfSut + "/customer/cart")
        .then()
            .statusCode(200);
    }

    @Test
    public void testClearCart_200() {
        given()
            .queryParam("name", "Ivan Petrov")
        .when()
            .delete(baseUrlOfSut + "/customer/cart")
        .then()
            .statusCode(200);
    }

    @Test
    public void testSetDelivery_200() {
        given()
            .queryParam("included", true)
            .queryParam("name", "Ivan Petrov")
            .contentType("application/json")
        .when()
            .put(baseUrlOfSut + "/customer/cart/delivery")
        .then()
            .statusCode(200);
    }

    @Test
    public void testPayByCard_201() {
        given()
            .queryParam("name", "Ivan Petrov")
            .body("{ \"ccNumber\": \"1234-5678-9012-3456\" }")
            .contentType("application/json")
        .when()
            .post(baseUrlOfSut + "/customer/cart/pay")
        .then()
            .statusCode(201);
    }

    @Test
    public void testGetContacts_200() {
        given()
            .queryParam("name", "Ivan Petrov")
        .when()
            .get(baseUrlOfSut + "/customer/contacts")
        .then()
            .statusCode(200);
    }

    @Test
    public void testUpdateContacts_200() {
        given()
            .queryParam("name", "Ivan Petrov")
            .body("{ \"address\": \"New Address\", \"phone\": \"+7 987 654 32 10\" }")
            .contentType("application/json")
        .when()
            .put(baseUrlOfSut + "/customer/contacts")
        .then()
            .statusCode(200);
    }

    @Test
    public void testGetOrders_200() {
        given()
            .queryParam("name", "Ivan Petrov")
        .when()
            .get(baseUrlOfSut + "/customer/orders")
        .then()
            .statusCode(200);
    }

    @Test
    public void testGetOrder_200() {
        given()
            .queryParam("name", "Ivan Petrov")
            .pathParam("orderId", 1)
        .when()
            .get(baseUrlOfSut + "/customer/orders/{orderId}")
        .then()
            .statusCode(200);
    }

    @Test
    public void testGetProducts_200() {
        given()
        .when()
            .get(baseUrlOfSut + "/products")
        .then()
            .statusCode(200);
    }

    @Test
    public void testGetProduct_200() {
        given()
            .pathParam("productId", 1)
        .when()
            .get(baseUrlOfSut + "/products/{productId}")
        .then()
            .statusCode(200);
    }

    @Test
    public void testCreateCustomer_201() {
        given()
            .body("{ \"email\": \"new.user@yandex.ru\", \"password\": \"newpass\", \"name\": \"New User\", \"phone\": \"+7 123 456 78 91\", \"address\": \"New Address\" }")
            .contentType("application/json")
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201);
    }
}
