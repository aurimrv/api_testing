
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

public class v1_gpt4o_run02_RestExceptionHandlerTest {

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
    @Timeout(5)
    public void testGetCustomer_200() {
        given()
            .queryParam("name", "Ivan%20Petrov")
            .when()
            .get(baseUrlOfSut + "/customer")
            .then()
            .statusCode(200)
            .body("name", equalTo("Ivan Petrov"));
    }

    @Test
    @Timeout(5)
    public void testGetCustomer_401() {
        given()
            .queryParam("name", "Unauthorized%20User")
            .when()
            .get(baseUrlOfSut + "/customer")
            .then()
            .statusCode(401);
    }

    @Test
    @Timeout(5)
    public void testGetCustomer_404() {
        given()
            .queryParam("name", "Non%20Existent%20User")
            .when()
            .get(baseUrlOfSut + "/customer")
            .then()
            .statusCode(404);
    }

    @Test
    @Timeout(5)
    public void testGetCart_200() {
        given()
            .queryParam("name", "Ivan%20Petrov")
            .when()
            .get(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200)
            .body("totalItems", equalTo(0));
    }

    @Test
    @Timeout(5)
    public void testGetCart_401() {
        given()
            .queryParam("name", "Unauthorized%20User")
            .when()
            .get(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(401);
    }

    @Test
    @Timeout(5)
    public void testGetCart_404() {
        given()
            .queryParam("name", "Non%20Existent%20User")
            .when()
            .get(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(404);
    }

    @Test
    @Timeout(5)
    public void testAddItemToCart_200() {
        String itemJson = "{ \"productId\": 1, \"quantity\": 1 }";

        given()
            .contentType("application/json")
            .body(itemJson)
            .queryParam("name", "Ivan%20Petrov")
            .when()
            .put(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200)
            .body("totalItems", equalTo(1));
    }

    @Test
    @Timeout(5)
    public void testAddItemToCart_401() {
        String itemJson = "{ \"productId\": 1, \"quantity\": 1 }";

        given()
            .contentType("application/json")
            .body(itemJson)
            .queryParam("name", "Unauthorized%20User")
            .when()
            .put(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(401);
    }

    @Test
    @Timeout(5)
    public void testAddItemToCart_404() {
        String itemJson = "{ \"productId\": 999, \"quantity\": 1 }";

        given()
            .contentType("application/json")
            .body(itemJson)
            .queryParam("name", "Ivan%20Petrov")
            .when()
            .put(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(404);
    }

    @Test
    @Timeout(5)
    public void testClearCart_200() {
        given()
            .queryParam("name", "Ivan%20Petrov")
            .when()
            .delete(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200)
            .body("totalItems", equalTo(0));
    }

    @Test
    @Timeout(5)
    public void testClearCart_401() {
        given()
            .queryParam("name", "Unauthorized%20User")
            .when()
            .delete(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(401);
    }

    @Test
    @Timeout(5)
    public void testSetDelivery_200() {
        given()
            .queryParam("included", true)
            .queryParam("name", "Ivan%20Petrov")
            .when()
            .put(baseUrlOfSut + "/customer/cart/delivery")
            .then()
            .statusCode(200)
            .body("deliveryIncluded", equalTo(true));
    }

    @Test
    @Timeout(5)
    public void testSetDelivery_401() {
        given()
            .queryParam("included", true)
            .queryParam("name", "Unauthorized%20User")
            .when()
            .put(baseUrlOfSut + "/customer/cart/delivery")
            .then()
            .statusCode(401);
    }

    @Test
    @Timeout(5)
    public void testPayByCard_201() {
        String cardJson = "{ \"ccNumber\": \"4111111111111111\" }";

        given()
            .contentType("application/json")
            .body(cardJson)
            .queryParam("name", "Ivan%20Petrov")
            .when()
            .post(baseUrlOfSut + "/customer/cart/pay")
            .then()
            .statusCode(201);
    }

    @Test
    @Timeout(5)
    public void testPayByCard_401() {
        String cardJson = "{ \"ccNumber\": \"4111111111111111\" }";

        given()
            .contentType("application/json")
            .body(cardJson)
            .queryParam("name", "Unauthorized%20User")
            .when()
            .post(baseUrlOfSut + "/customer/cart/pay")
            .then()
            .statusCode(401);
    }

    @Test
    @Timeout(5)
    public void testGetContacts_200() {
        given()
            .queryParam("name", "Ivan%20Petrov")
            .when()
            .get(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(200)
            .body("phone", equalTo("+7 123 456 78 90"));
    }

    @Test
    @Timeout(5)
    public void testGetContacts_401() {
        given()
            .queryParam("name", "Unauthorized%20User")
            .when()
            .get(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(401);
    }

    @Test
    @Timeout(5)
    public void testGetOrders_200() {
        given()
            .queryParam("name", "Ivan%20Petrov")
            .when()
            .get(baseUrlOfSut + "/customer/orders")
            .then()
            .statusCode(200);
    }

    @Test
    @Timeout(5)
    public void testGetOrders_401() {
        given()
            .queryParam("name", "Unauthorized%20User")
            .when()
            .get(baseUrlOfSut + "/customer/orders")
            .then()
            .statusCode(401);
    }

    @Test
    @Timeout(5)
    public void testGetOrder_200() {
        given()
            .queryParam("name", "Ivan%20Petrov")
            .pathParam("orderId", 1)
            .when()
            .get(baseUrlOfSut + "/customer/orders/{orderId}")
            .then()
            .statusCode(200);
    }

    @Test
    @Timeout(5)
    public void testGetOrder_401() {
        given()
            .queryParam("name", "Unauthorized%20User")
            .pathParam("orderId", 1)
            .when()
            .get(baseUrlOfSut + "/customer/orders/{orderId}")
            .then()
            .statusCode(401);
    }

    @Test
    @Timeout(5)
    public void testGetOrder_404() {
        given()
            .queryParam("name", "Ivan%20Petrov")
            .pathParam("orderId", 999)
            .when()
            .get(baseUrlOfSut + "/customer/orders/{orderId}")
            .then()
            .statusCode(404);
    }

    @Test
    @Timeout(5)
    public void testGetProducts_200() {
        given()
            .when()
            .get(baseUrlOfSut + "/products")
            .then()
            .statusCode(200);
    }

    @Test
    @Timeout(5)
    public void testGetProduct_200() {
        given()
            .pathParam("productId", 1)
            .when()
            .get(baseUrlOfSut + "/products/{productId}")
            .then()
            .statusCode(200);
    }

    @Test
    @Timeout(5)
    public void testGetProduct_404() {
        given()
            .pathParam("productId", 999)
            .when()
            .get(baseUrlOfSut + "/products/{productId}")
            .then()
            .statusCode(404);
    }

    @Test
    @Timeout(5)
    public void testCreateCustomer_201() {
        String userJson = "{ \"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}";

        given()
            .contentType("application/json")
            .body(userJson)
            .when()
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(201)
            .body("email", equalTo("ivan.petrov@yandex.ru"));
    }

    @Test
    @Timeout(5)
    public void testCreateCustomer_409() {
        String userJson = "{ \"email\":\"duplicate@yandex.ru\",\"password\":\"duplicate\",\"name\":\"Duplicate User\",\"phone\":\"+7 999 999 99 99\",\"address\":\"Duplicate Address\"}";

        given()
            .contentType("application/json")
            .body(userJson)
            .when()
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(409);
    }
}
