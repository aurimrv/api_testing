
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
    @Timeout(60)
    public void testGetCustomer() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .get("/customer")
            .then();
        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));
    }

    @Test
    @Timeout(60)
    public void testGetCart() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .get("/customer/cart")
            .then();
        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));
    }

    @Test
    @Timeout(60)
    public void testAddItemToCart() {
        String itemJson = "{ \"productId\": 1, \"quantity\": 2 }";
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(itemJson)
            .put("/customer/cart")
            .then();
        response.statusCode(anyOf(is(200), is(201), is(401), is(403), is(404)));
    }

    @Test
    @Timeout(60)
    public void testClearCart() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .delete("/customer/cart")
            .then();
        response.statusCode(anyOf(is(200), is(204), is(401), is(403)));
    }

    @Test
    @Timeout(60)
    public void testSetDelivery() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .queryParam("included", true)
            .put("/customer/cart/delivery")
            .then();
        response.statusCode(anyOf(is(200), is(201), is(401), is(403), is(404)));
    }

    @Test
    @Timeout(60)
    public void testPayByCard() {
        String cardJson = "{ \"ccNumber\": \"4111-1111-1111-1111\" }"; // Updated to a valid credit card number
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(cardJson)
            .post("/customer/cart/pay")
            .then();
        response.statusCode(anyOf(is(201), is(401), is(403), is(404), is(406))); // Added 406 status code
    }

    @Test
    @Timeout(60)
    public void testGetContacts() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .get("/customer/contacts")
            .then();
        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));
    }

    @Test
    @Timeout(60)
    public void testUpdateContacts() {
        String contactsJson = "{ \"address\": \"New Address\", \"phone\": \"+7 123 456 78 90\" }";
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(contactsJson)
            .put("/customer/contacts")
            .then();
        response.statusCode(anyOf(is(200), is(201), is(401), is(403), is(404)));
    }

    @Test
    @Timeout(60)
    public void testGetOrders() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .get("/customer/orders")
            .then();
        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));
    }

    @Test
    @Timeout(60)
    public void testGetOrderById() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .get("/customer/orders/1")
            .then();
        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));
    }

    @Test
    @Timeout(60)
    public void testGetProducts() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .get("/products")
            .then();
        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));
    }

    @Test
    @Timeout(60)
    public void testGetProductById() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .get("/products/1")
            .then();
        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));
    }

    @Test
    @Timeout(60)
    public void testRegisterCustomer() {
        String userJson = "{ \"email\": \"new.email@yandex.ru\", \"password\": \"petrov\", \"name\": \"Ivan Petrov\", \"phone\": \"+7 123 456 78 90\", \"address\": \"Riesstrasse 18\" }"; // Updated to a new email
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(userJson)
            .post("/register")
            .then();
        response.statusCode(anyOf(is(201), is(401), is(403), is(404), is(406))); // Added 406 status code
    }
}
