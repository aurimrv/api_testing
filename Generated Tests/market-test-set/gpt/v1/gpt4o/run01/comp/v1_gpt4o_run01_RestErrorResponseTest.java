
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

public class v1_gpt4o_run01_RestErrorResponseTest {

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
            .baseUri(baseUrlOfSut)
            .basePath("/customer")
            .queryParam("name", "Ivan Petrov")
            .when()
            .get()
            .then();

        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));
        if (response.extract().statusCode() == 200) {
            response.body("email", equalTo("ivan.petrov@yandex.ru"));
        }
    }

    @Test
    public void testGetCart() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/cart")
            .queryParam("name", "Ivan Petrov")
            .when()
            .get()
            .then();

        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));
    }

    @Test
    public void testAddItemToCart() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/cart")
            .queryParam("name", "Ivan Petrov")
            .contentType("application/json")
            .body("{\"productId\": 1, \"quantity\": 2}")
            .when()
            .put()
            .then();

        response.statusCode(anyOf(is(200), is(201), is(401), is(403), is(404)));
    }

    @Test
    public void testClearCart() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/cart")
            .queryParam("name", "Ivan Petrov")
            .when()
            .delete()
            .then();

        response.statusCode(anyOf(is(200), is(204), is(401), is(403)));
    }

    @Test
    public void testSetDelivery() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/cart/delivery")
            .queryParam("included", true)
            .queryParam("name", "Ivan Petrov")
            .contentType("application/json")
            .when()
            .put()
            .then();

        response.statusCode(anyOf(is(200), is(201), is(401), is(403), is(404)));
    }

    @Test
    public void testPayByCard() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/cart/pay")
            .queryParam("name", "Ivan Petrov")
            .contentType("application/json")
            .body("{\"ccNumber\": \"4111111111111111\"}")
            .when()
            .post()
            .then();

        response.statusCode(anyOf(is(201), is(401), is(403), is(404)));
    }

    @Test
    public void testGetContacts() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/contacts")
            .queryParam("name", "Ivan Petrov")
            .when()
            .get()
            .then();

        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));
    }

    @Test
    public void testUpdateContacts() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/contacts")
            .queryParam("name", "Ivan Petrov")
            .contentType("application/json")
            .body("{\"address\": \"Riesstrasse 18\", \"phone\": \"+7 123 456 78 90\"}")
            .when()
            .put()
            .then();

        response.statusCode(anyOf(is(200), is(201), is(401), is(403), is(404)));
    }

    @Test
    public void testGetOrders() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/orders")
            .queryParam("name", "Ivan Petrov")
            .when()
            .get()
            .then();

        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));
    }

    @Test
    public void testGetOrder() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/orders/1")
            .queryParam("name", "Ivan Petrov")
            .when()
            .get()
            .then();

        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));
    }

    @Test
    public void testGetProducts() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/products")
            .when()
            .get()
            .then();

        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));
    }

    @Test
    public void testGetProduct() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/products/1")
            .when()
            .get()
            .then();

        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));
    }

    @Test
    public void testCreateCustomer() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/register")
            .contentType("application/json")
            .body("{\"email\": \"ivan.petrov@yandex.ru\", \"password\": \"petrov\", \"name\": \"Ivan Petrov\", \"phone\": \"+7 123 456 78 90\", \"address\": \"Riesstrasse 18\"}")
            .when()
            .post()
            .then();

        response.statusCode(anyOf(is(201), is(401), is(403), is(404)));
    }
}
