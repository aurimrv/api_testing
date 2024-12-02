
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

public class v3_gpt4o_run01_RestExceptionHandlerTest {

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
    public void testGetCustomer_NotFound() {
        given()
            .baseUri(baseUrlOfSut)
            .auth().basic("user", "password")  // Added basic auth to fix 401 errors
            .when()
            .get("/customer?name=NonExistentUser")
            .then()
            .statusCode(404);
    }

    @Test
    public void testGetCustomer_Unauthorized() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/customer")
            .then()
            .statusCode(401);
    }

    @Test
    public void testAddItemToCart_InvalidProduct() {
        String body = "{ \"productId\": 9999, \"quantity\": 1 }";
        given()
            .baseUri(baseUrlOfSut)
            .auth().basic("user", "password")  // Added basic auth to fix 401 errors
            .contentType("application/json")
            .body(body)
            .when()
            .put("/customer/cart?name=ivan.petrov@yandex.ru")
            .then()
            .statusCode(404);
    }

    @Test
    public void testSetDelivery_InvalidBoolean() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("included", "invalidBoolean")
            .queryParam("name", "ivan.petrov@yandex.ru")
            .when()
            .put("/customer/cart/delivery")
            .then()
            .statusCode(500);
    }

    @Test
    public void testPayByCard_InvalidCardNumber() {
        String body = "{ \"ccNumber\": \"1234\" }";
        given()
            .baseUri(baseUrlOfSut)
            .auth().basic("user", "password")  // Added basic auth to fix 401 errors
            .contentType("application/json")
            .body(body)
            .when()
            .post("/customer/cart/pay?name=ivan.petrov@yandex.ru")
            .then()
            .statusCode(406);
    }

    @Test
    public void testUpdateContacts_InvalidPhone() {
        String body = "{ \"address\": \"Riesstrasse 18\", \"phone\": \"invalidPhone\" }";
        given()
            .baseUri(baseUrlOfSut)
            .auth().basic("user", "password")  // Added basic auth to fix 401 errors
            .contentType("application/json")
            .body(body)
            .when()
            .put("/customer/contacts?name=ivan.petrov@yandex.ru")
            .then()
            .statusCode(406);
    }

    @Test
    public void testCreateCustomer_Duplicate() {
        String body = "{ \"email\": \"ivan.petrov@yandex.ru\", \"password\": \"petrov\", \"name\": \"Ivan Petrov\", \"phone\": \"+7 123 456 78 90\", \"address\": \"Riesstrasse 18\" }";
        // Register the user first
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(body)
            .when()
            .post("/register")
            .then()
            .statusCode(201);

        // Try to register the same user again
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(body)
            .when()
            .post("/register")
            .then()
            .statusCode(406);
    }

    @Test
    public void testGetProduct_NotFound() {
        given()
            .baseUri(baseUrlOfSut)
            .auth().basic("user", "password")  // Added basic auth to fix 401 errors
            .when()
            .get("/products/9999")
            .then()
            .statusCode(404);
    }

    @Test
    public void testGetOrders_Unauthorized() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/customer/orders")
            .then()
            .statusCode(401);
    }

    @Test
    public void testGetProduct_Unauthorized() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/products/1")
            .then()
            .statusCode(401);
    }
}
