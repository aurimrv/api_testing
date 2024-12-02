
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
import static org.evomaster.client.java.controller.expect.ExpectationHandler.expectationHandler;
import org.evomaster.client.java.controller.expect.ExpectationHandler;
import io.restassured.path.json.JsonPath;
import java.util.Arrays;

public class v3_gpt4o_run01_RestErrorResponseTest {

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
        given()
            .queryParam("name", "invalid_user")
            .get(baseUrlOfSut + "/customer")
            .then()
            .statusCode(401);
    }

    @Test
    public void testGetCart() {
        given()
            .queryParam("name", "invalid_user")
            .get(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(401);
    }

    @Test
    public void testAddItemToCart() {
        given()
            .queryParam("name", "invalid_user")
            .contentType("application/json")
            .body("{\"productId\": 999, \"quantity\": 1}")
            .put(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(500)
            .body("message", equalTo("Content type 'text/plain;charset=ISO-8859-1' not supported"));
    }

    @Test
    public void testClearCart() {
        given()
            .queryParam("name", "invalid_user")
            .delete(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(401);
    }

    @Test
    public void testSetDelivery() {
        given()
            .queryParam("name", "invalid_user")
            .queryParam("included", true)
            .put(baseUrlOfSut + "/customer/cart/delivery")
            .then()
            .statusCode(401);
    }

    @Test
    public void testPayByCard() {
        given()
            .queryParam("name", "invalid_user")
            .contentType("application/json")
            .body("{\"ccNumber\": \"1234-5678-9876-5432\"}")
            .post(baseUrlOfSut + "/customer/cart/pay")
            .then()
            .statusCode(500)
            .body("message", equalTo("Content type 'text/plain;charset=ISO-8859-1' not supported"));
    }

    @Test
    public void testGetContacts() {
        given()
            .queryParam("name", "invalid_user")
            .get(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(401);
    }

    @Test
    public void testUpdateContacts() {
        given()
            .queryParam("name", "invalid_user")
            .contentType("application/json")
            .body("{\"address\":\"New Address\",\"phone\":\"+7 123 456 78 90\"}")
            .put(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(500)
            .body("message", equalTo("Content type 'text/plain;charset=ISO-8859-1' not supported"));
    }

    @Test
    public void testGetOrders() {
        given()
            .queryParam("name", "invalid_user")
            .get(baseUrlOfSut + "/customer/orders")
            .then()
            .statusCode(401);
    }

    @Test
    public void testGetOrder() {
        given()
            .queryParam("name", "invalid_user")
            .pathParam("orderId", 999)
            .get(baseUrlOfSut + "/customer/orders/{orderId}")
            .then()
            .statusCode(401);
    }

    @Test
    public void testGetProducts() {
        given()
            .get(baseUrlOfSut + "/products")
            .then()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }

    @Test
    public void testGetProduct() {
        given()
            .pathParam("productId", 999)
            .get(baseUrlOfSut + "/products/{productId}")
            .then()
            .statusCode(404)
            .body("message", equalTo("Requested entity doesn't exist"));
    }

    @Test
    public void testCreateCustomer() {
        given()
            .contentType("application/json")
            .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(500)
            .body("message", equalTo("Content type 'text/plain;charset=ISO-8859-1' not supported"));
    }
}
