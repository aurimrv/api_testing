
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

public class v1_gpt4o_run01_ProductsRestControllerTest {

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
        controller.resetDatabase(Arrays.asList("USER_ROLE", "CUSTOMER_ORDER", "CART_ITEM", "PRODUCT", "CART", "CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    public void testGetProducts() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/products")
            .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    public void testGetProductById() {
        long productId = 1;  // Assuming a product with ID 1 exists
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/products/{productId}", productId)
            .then()
            .statusCode(200)
            .body("productId", equalTo((int) productId));
    }

    @Test
    public void testGetProductByIdNotFound() {
        long productId = 9999;  // A non-existing product ID
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/products/{productId}", productId)
            .then()
            .statusCode(404);
    }

    @Test
    public void testGetCustomer() {
        given()
            .baseUri(baseUrlOfSut)
            .auth().basic("username", "password")
            .when()
            .get("/customer")
            .then()
            .statusCode(200);
    }

    @Test
    public void testGetCustomerCart() {
        given()
            .baseUri(baseUrlOfSut)
            .auth().basic("username", "password")
            .when()
            .get("/customer/cart")
            .then()
            .statusCode(200);
    }

    @Test
    public void testAddItemToCart() {
        String itemJson = "{\"productId\": 1, \"quantity\": 2}";
        given()
            .baseUri(baseUrlOfSut)
            .auth().basic("username", "password")
            .contentType("application/json")
            .body(itemJson)
            .when()
            .put("/customer/cart")
            .then()
            .statusCode(201);
    }

    @Test
    public void testClearCart() {
        given()
            .baseUri(baseUrlOfSut)
            .auth().basic("username", "password")
            .when()
            .delete("/customer/cart")
            .then()
            .statusCode(204);
    }

    @Test
    public void testSetDelivery() {
        given()
            .baseUri(baseUrlOfSut)
            .auth().basic("username", "password")
            .queryParam("included", true)
            .when()
            .put("/customer/cart/delivery")
            .then()
            .statusCode(201);
    }

    @Test
    public void testPayByCard() {
        String cardJson = "{\"ccNumber\": \"4111111111111111\"}";
        given()
            .baseUri(baseUrlOfSut)
            .auth().basic("username", "password")
            .contentType("application/json")
            .body(cardJson)
            .when()
            .post("/customer/cart/pay")
            .then()
            .statusCode(201);
    }

    @Test
    public void testGetContacts() {
        given()
            .baseUri(baseUrlOfSut)
            .auth().basic("username", "password")
            .when()
            .get("/customer/contacts")
            .then()
            .statusCode(200);
    }

    @Test
    public void testUpdateContacts() {
        String contactsJson = "{\"address\": \"New Address\", \"phone\": \"+7 987 654 32 10\"}";
        given()
            .baseUri(baseUrlOfSut)
            .auth().basic("username", "password")
            .contentType("application/json")
            .body(contactsJson)
            .when()
            .put("/customer/contacts")
            .then()
            .statusCode(201);
    }

    @Test
    public void testGetOrders() {
        given()
            .baseUri(baseUrlOfSut)
            .auth().basic("username", "password")
            .when()
            .get("/customer/orders")
            .then()
            .statusCode(200);
    }

    @Test
    public void testGetOrderById() {
        long orderId = 1;  // Assuming an order with ID 1 exists
        given()
            .baseUri(baseUrlOfSut)
            .auth().basic("username", "password")
            .when()
            .get("/customer/orders/{orderId}", orderId)
            .then()
            .statusCode(200)
            .body("id", equalTo((int) orderId));
    }
}
