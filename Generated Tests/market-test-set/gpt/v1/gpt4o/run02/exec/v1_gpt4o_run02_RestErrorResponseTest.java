
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

public class v1_gpt4o_run02_RestErrorResponseTest {

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
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Ivan%20Petrov")
        .when()
            .get("/customer")
        .then()
            .statusCode(200)
            .body("email", equalTo("ivan.petrov@yandex.ru"))
            .body("name", equalTo("Ivan Petrov"));
    }

    @Test
    public void testGetCustomer_404() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Non%20Existent%20User")
        .when()
            .get("/customer")
        .then()
            .statusCode(404);
    }

    @Test
    public void testGetCart_200() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Ivan%20Petrov")
        .when()
            .get("/customer/cart")
        .then()
            .statusCode(200)
            .body("user", equalTo("Ivan Petrov"));
    }

    @Test
    public void testGetCart_404() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Non%20Existent%20User")
        .when()
            .get("/customer/cart")
        .then()
            .statusCode(404);
    }

    @Test
    public void testAddItemToCart_200() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Ivan%20Petrov")
            .body(Map.of("productId", 1, "quantity", 2))
            .contentType("application/json")
        .when()
            .put("/customer/cart")
        .then()
            .statusCode(200)
            .body("totalItems", equalTo(2));
    }

    @Test
    public void testAddItemToCart_404() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Non%20Existent%20User")
            .body(Map.of("productId", 1, "quantity", 2))
            .contentType("application/json")
        .when()
            .put("/customer/cart")
        .then()
            .statusCode(404);
    }

    @Test
    public void testClearCart_200() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Ivan%20Petrov")
        .when()
            .delete("/customer/cart")
        .then()
            .statusCode(200)
            .body("totalItems", equalTo(0));
    }

    @Test
    public void testClearCart_404() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Non%20Existent%20User")
        .when()
            .delete("/customer/cart")
        .then()
            .statusCode(404);
    }

    @Test
    public void testSetDelivery_200() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Ivan%20Petrov")
            .queryParam("included", true)
        .when()
            .put("/customer/cart/delivery")
        .then()
            .statusCode(200)
            .body("deliveryIncluded", equalTo(true));
    }

    @Test
    public void testSetDelivery_404() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Non%20Existent%20User")
            .queryParam("included", true)
        .when()
            .put("/customer/cart/delivery")
        .then()
            .statusCode(404);
    }

    @Test
    public void testPayByCard_201() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Ivan%20Petrov")
            .body(Map.of("ccNumber", "4111111111111111"))
            .contentType("application/json")
        .when()
            .post("/customer/cart/pay")
        .then()
            .statusCode(201)
            .body("payed", equalTo(true));
    }

    @Test
    public void testPayByCard_404() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Non%20Existent%20User")
            .body(Map.of("ccNumber", "4111111111111111"))
            .contentType("application/json")
        .when()
            .post("/customer/cart/pay")
        .then()
            .statusCode(404);
    }

    @Test
    public void testGetContacts_200() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Ivan%20Petrov")
        .when()
            .get("/customer/contacts")
        .then()
            .statusCode(200)
            .body("phone", equalTo("+7 123 456 78 90"));
    }

    @Test
    public void testGetContacts_404() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Non%20Existent%20User")
        .when()
            .get("/customer/contacts")
        .then()
            .statusCode(404);
    }

    @Test
    public void testUpdateContacts_200() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Ivan%20Petrov")
            .body(Map.of("phone", "+7 987 654 32 10", "address", "New Address"))
            .contentType("application/json")
        .when()
            .put("/customer/contacts")
        .then()
            .statusCode(200)
            .body("phone", equalTo("+7 987 654 32 10"));
    }

    @Test
    public void testUpdateContacts_404() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Non%20Existent%20User")
            .body(Map.of("phone", "+7 987 654 32 10", "address", "New Address"))
            .contentType("application/json")
        .when()
            .put("/customer/contacts")
        .then()
            .statusCode(404);
    }

    @Test
    public void testGetOrders_200() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Ivan%20Petrov")
        .when()
            .get("/customer/orders")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }

    @Test
    public void testGetOrders_404() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Non%20Existent%20User")
        .when()
            .get("/customer/orders")
        .then()
            .statusCode(404);
    }

    @Test
    public void testGetOrderById_200() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Ivan%20Petrov")
            .pathParam("orderId", 1)
        .when()
            .get("/customer/orders/{orderId}")
        .then()
            .statusCode(200)
            .body("id", equalTo(1));
    }

    @Test
    public void testGetOrderById_404() {
        given()
            .baseUri(baseUrlOfSut)
            .queryParam("name", "Non%20Existent%20User")
            .pathParam("orderId", 1)
        .when()
            .get("/customer/orders/{orderId}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testGetProducts_200() {
        given()
            .baseUri(baseUrlOfSut)
        .when()
            .get("/products")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }

    @Test
    public void testGetProductById_200() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("productId", 1)
        .when()
            .get("/products/{productId}")
        .then()
            .statusCode(200)
            .body("productId", equalTo(1));
    }

    @Test
    public void testGetProductById_404() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("productId", 999)
        .when()
            .get("/products/{productId}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testRegisterCustomer_201() {
        given()
            .baseUri(baseUrlOfSut)
            .body(Map.of(
                "email", "new.user@example.com",
                "password", "password123",
                "name", "New User",
                "phone", "+1 234 567 8901",
                "address", "123 New St"
            ))
            .contentType("application/json")
        .when()
            .post("/register")
        .then()
            .statusCode(201)
            .body("email", equalTo("new.user@example.com"));
    }

    @Test
    public void testRegisterCustomer_400() {
        given()
            .baseUri(baseUrlOfSut)
            .body(Map.of(
                "email", "invalid-email",
                "password", "short",
                "name", "Invalid User",
                "phone", "invalid-phone",
                "address", "Invalid Address"
            ))
            .contentType("application/json")
        .when()
            .post("/register")
        .then()
            .statusCode(400);
    }
}
