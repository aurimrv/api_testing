
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
import org.evomaster.client.java.controller.expectation.ExpectationHandler;
import io.restassured.path.json.JsonPath;
import java.util.Arrays;

public class run01_RestExceptionHandlerTest {

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
    public void testGetCustomerUnauthorized() {
        given()
            .queryParam("name", "ivan.petrov@yandex.ru")
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(401);
    }

    @Test
    public void testGetCustomerNotFound() {
        given()
            .queryParam("name", "non.existent@yandex.ru")
        .when()
            .get(baseUrlOfSut + "/customer")
        .then()
            .statusCode(404);
    }

    @Test
    public void testGetCartNotFound() {
        given()
            .queryParam("name", "non.existent@yandex.ru")
        .when()
            .get(baseUrlOfSut + "/customer/cart")
        .then()
            .statusCode(404);
    }

    @Test
    public void testAddItemUnauthorized() {
        Map<String, Object> item = Map.of("productId", 1, "quantity", 1);

        given()
            .contentType("application/json")
            .body(item)
        .when()
            .put(baseUrlOfSut + "/customer/cart")
        .then()
            .statusCode(401);
    }

    @Test
    public void testClearCartUnauthorized() {
        given()
        .when()
            .delete(baseUrlOfSut + "/customer/cart")
        .then()
            .statusCode(401);
    }

    @Test
    public void testPayByCardUnauthorized() {
        Map<String, Object> card = Map.of("ccNumber", "4111111111111111");

        given()
            .contentType("application/json")
            .body(card)
        .when()
            .post(baseUrlOfSut + "/customer/cart/pay")
        .then()
            .statusCode(401);
    }

    @Test
    public void testGetContactsUnauthorized() {
        given()
        .when()
            .get(baseUrlOfSut + "/customer/contacts")
        .then()
            .statusCode(401);
    }

    @Test
    public void testUpdateContactsUnauthorized() {
        Map<String, Object> contactsDto = Map.of("phone", "+7 123 456 78 90", "address", "Riesstrasse 18");

        given()
            .contentType("application/json")
            .body(contactsDto)
        .when()
            .put(baseUrlOfSut + "/customer/contacts")
        .then()
            .statusCode(401);
    }

    @Test
    public void testGetOrdersUnauthorized() {
        given()
        .when()
            .get(baseUrlOfSut + "/customer/orders")
        .then()
            .statusCode(401);
    }

    @Test
    public void testGetOrderUnauthorized() {
        given()
            .queryParam("name", "ivan.petrov@yandex.ru")
            .pathParam("orderId", 1)
        .when()
            .get(baseUrlOfSut + "/customer/orders/{orderId}")
        .then()
            .statusCode(401);
    }

    @Test
    public void testGetProductNotFound() {
        given()
            .pathParam("productId", 999)
        .when()
            .get(baseUrlOfSut + "/products/{productId}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testCreateCustomer() {
        Map<String, Object> user = Map.of(
            "email", "ivan.petrov@yandex.ru",
            "password", "petrov",
            "name", "Ivan Petrov",
            "phone", "+7 123 456 78 90",
            "address", "Riesstrasse 18"
        );

        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(201)
            .body("email", equalTo("ivan.petrov@yandex.ru"));
    }

    @Test
    public void testCreateCustomerInvalidData() {
        Map<String, Object> user = Map.of(
            "email", "invalid-email",
            "password", "short",
            "name", "",
            "phone", "invalid-phone",
            "address", ""
        );

        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post(baseUrlOfSut + "/register")
        .then()
            .statusCode(406);
    }
}
