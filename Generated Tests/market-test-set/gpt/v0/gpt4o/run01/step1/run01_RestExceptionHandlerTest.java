
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
        controller.resetDatabase(Arrays.asList("USER_ROLE", "CUSTOMER_ORDER", "CART_ITEM", "PRODUCT", "CART", "CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    public void testGetCustomer() {
        given().queryParam("name", "Ivan Petrov")
               .when().get(baseUrlOfSut + "/customer")
               .then().statusCode(200);
    }

    @Test
    public void testGetCustomerUnauthorized() {
        given().queryParam("name", "Unknown User")
               .when().get(baseUrlOfSut + "/customer")
               .then().statusCode(401);
    }

    @Test
    public void testGetCustomerNotFound() {
        given().queryParam("name", "Nonexistent User")
               .when().get(baseUrlOfSut + "/customer")
               .then().statusCode(404);
    }

    @Test
    public void testAddItemToCart() {
        given().contentType("application/json")
               .body("{\"productId\":1, \"quantity\":2}")
               .when().put(baseUrlOfSut + "/customer/cart")
               .then().statusCode(200);
    }

    @Test
    public void testAddItemToCartUnauthorized() {
        given().contentType("application/json")
               .body("{\"productId\":1, \"quantity\":2}")
               .when().put(baseUrlOfSut + "/customer/cart")
               .then().statusCode(401);
    }

    @Test
    public void testAddItemToCartNotFound() {
        given().contentType("application/json")
               .body("{\"productId\":999, \"quantity\":2}")
               .when().put(baseUrlOfSut + "/customer/cart")
               .then().statusCode(404);
    }

    @Test
    public void testClearCart() {
        given().when().delete(baseUrlOfSut + "/customer/cart")
               .then().statusCode(200);
    }

    @Test
    public void testClearCartUnauthorized() {
        given().when().delete(baseUrlOfSut + "/customer/cart")
               .then().statusCode(401);
    }

    @Test
    public void testClearCartNotFound() {
        given().when().delete(baseUrlOfSut + "/customer/cart")
               .then().statusCode(404);
    }

    @Test
    public void testSetDelivery() {
        given().queryParam("included", true)
               .when().put(baseUrlOfSut + "/customer/cart/delivery")
               .then().statusCode(200);
    }

    @Test
    public void testSetDeliveryUnauthorized() {
        given().queryParam("included", true)
               .when().put(baseUrlOfSut + "/customer/cart/delivery")
               .then().statusCode(401);
    }

    @Test
    public void testSetDeliveryNotFound() {
        given().queryParam("included", true)
               .when().put(baseUrlOfSut + "/customer/cart/delivery")
               .then().statusCode(404);
    }

    @Test
    public void testPayByCard() {
        given().contentType("application/json")
               .body("{\"ccNumber\":\"1234-5678-9876-5432\"}")
               .when().post(baseUrlOfSut + "/customer/cart/pay")
               .then().statusCode(201);
    }

    @Test
    public void testPayByCardUnauthorized() {
        given().contentType("application/json")
               .body("{\"ccNumber\":\"1234-5678-9876-5432\"}")
               .when().post(baseUrlOfSut + "/customer/cart/pay")
               .then().statusCode(401);
    }

    @Test
    public void testPayByCardNotFound() {
        given().contentType("application/json")
               .body("{\"ccNumber\":\"1234-5678-9876-5432\"}")
               .when().post(baseUrlOfSut + "/customer/cart/pay")
               .then().statusCode(404);
    }

    @Test
    public void testGetContacts() {
        given().queryParam("name", "Ivan Petrov")
               .when().get(baseUrlOfSut + "/customer/contacts")
               .then().statusCode(200);
    }

    @Test
    public void testUpdateContacts() {
        given().contentType("application/json")
               .body("{\"address\":\"New Address\", \"phone\":\"+7 987 654 32 10\"}")
               .when().put(baseUrlOfSut + "/customer/contacts")
               .then().statusCode(200);
    }

    @Test
    public void testUpdateContactsUnauthorized() {
        given().contentType("application/json")
               .body("{\"address\":\"New Address\", \"phone\":\"+7 987 654 32 10\"}")
               .when().put(baseUrlOfSut + "/customer/contacts")
               .then().statusCode(401);
    }

    @Test
    public void testUpdateContactsNotFound() {
        given().contentType("application/json")
               .body("{\"address\":\"New Address\", \"phone\":\"+7 987 654 32 10\"}")
               .when().put(baseUrlOfSut + "/customer/contacts")
               .then().statusCode(404);
    }

    @Test
    public void testGetOrders() {
        given().queryParam("name", "Ivan Petrov")
               .when().get(baseUrlOfSut + "/customer/orders")
               .then().statusCode(200);
    }

    @Test
    public void testGetOrder() {
        given().queryParam("name", "Ivan Petrov")
               .pathParam("orderId", 1)
               .when().get(baseUrlOfSut + "/customer/orders/{orderId}")
               .then().statusCode(200);
    }

    @Test
    public void testGetOrderUnauthorized() {
        given().queryParam("name", "Ivan Petrov")
               .pathParam("orderId", 1)
               .when().get(baseUrlOfSut + "/customer/orders/{orderId}")
               .then().statusCode(401);
    }

    @Test
    public void testGetOrderNotFound() {
        given().queryParam("name", "Ivan Petrov")
               .pathParam("orderId", 999)
               .when().get(baseUrlOfSut + "/customer/orders/{orderId}")
               .then().statusCode(404);
    }

    @Test
    public void testGetProducts() {
        given().when().get(baseUrlOfSut + "/products")
               .then().statusCode(200);
    }

    @Test
    public void testGetProduct() {
        given().pathParam("productId", 1)
               .when().get(baseUrlOfSut + "/products/{productId}")
               .then().statusCode(200);
    }

    @Test
    public void testGetProductUnauthorized() {
        given().pathParam("productId", 1)
               .when().get(baseUrlOfSut + "/products/{productId}")
               .then().statusCode(401);
    }

    @Test
    public void testGetProductNotFound() {
        given().pathParam("productId", 999)
               .when().get(baseUrlOfSut + "/products/{productId}")
               .then().statusCode(404);
    }

    @Test
    public void testCreateCustomer() {
        given().contentType("application/json")
               .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
               .when().post(baseUrlOfSut + "/register")
               .then().statusCode(201);
    }

    @Test
    public void testCreateCustomerUnauthorized() {
        given().contentType("application/json")
               .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
               .when().post(baseUrlOfSut + "/register")
               .then().statusCode(401);
    }

    @Test
    public void testCreateCustomerNotFound() {
        given().contentType("application/json")
               .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
               .when().post(baseUrlOfSut + "/register")
               .then().statusCode(404);
    }
}
