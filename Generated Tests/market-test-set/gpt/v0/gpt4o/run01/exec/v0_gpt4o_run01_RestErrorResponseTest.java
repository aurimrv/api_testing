
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

public class v0_gpt4o_run01_RestErrorResponseTest {

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

    // Test Cases

    @Test
    public void testGetCustomer() {
        given().queryParam("name", "Ivan%20Petrov")
            .when().get(baseUrlOfSut + "/customer")
            .then().statusCode(200);
        
        given().queryParam("name", "NonExistentUser")
            .when().get(baseUrlOfSut + "/customer")
            .then().statusCode(404);
    }

    @Test
    public void testGetCart() {
        given().queryParam("name", "Ivan%20Petrov")
            .when().get(baseUrlOfSut + "/customer/cart")
            .then().statusCode(200);

        given().queryParam("name", "NonExistentUser")
            .when().get(baseUrlOfSut + "/customer/cart")
            .then().statusCode(404);
    }

    @Test
    public void testAddItemToCart() {
        String itemJson = "{ \"productId\": 1, \"quantity\": 2 }";

        given().queryParam("name", "Ivan%20Petrov")
            .body(itemJson).contentType("application/json")
            .when().put(baseUrlOfSut + "/customer/cart")
            .then().statusCode(200);

        given().queryParam("name", "NonExistentUser")
            .body(itemJson).contentType("application/json")
            .when().put(baseUrlOfSut + "/customer/cart")
            .then().statusCode(404);
    }

    @Test
    public void testClearCart() {
        given().queryParam("name", "Ivan%20Petrov")
            .when().delete(baseUrlOfSut + "/customer/cart")
            .then().statusCode(200);

        given().queryParam("name", "NonExistentUser")
            .when().delete(baseUrlOfSut + "/customer/cart")
            .then().statusCode(404);
    }

    @Test
    public void testSetDelivery() {
        given().queryParam("name", "Ivan%20Petrov")
            .queryParam("included", true)
            .when().put(baseUrlOfSut + "/customer/cart/delivery")
            .then().statusCode(200);

        given().queryParam("name", "NonExistentUser")
            .queryParam("included", true)
            .when().put(baseUrlOfSut + "/customer/cart/delivery")
            .then().statusCode(404);
    }

    @Test
    public void testPayByCard() {
        String cardJson = "{ \"ccNumber\": \"4111111111111111\" }";

        given().queryParam("name", "Ivan%20Petrov")
            .body(cardJson).contentType("application/json")
            .when().post(baseUrlOfSut + "/customer/cart/pay")
            .then().statusCode(201);

        given().queryParam("name", "NonExistentUser")
            .body(cardJson).contentType("application/json")
            .when().post(baseUrlOfSut + "/customer/cart/pay")
            .then().statusCode(404);
    }

    @Test
    public void testGetContacts() {
        given().queryParam("name", "Ivan%20Petrov")
            .when().get(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(200);

        given().queryParam("name", "NonExistentUser")
            .when().get(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(404);
    }

    @Test
    public void testUpdateContacts() {
        String contactsJson = "{ \"address\": \"New Address\", \"phone\": \"+7 123 456 78 90\" }";

        given().queryParam("name", "Ivan%20Petrov")
            .body(contactsJson).contentType("application/json")
            .when().put(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(200);

        given().queryParam("name", "NonExistentUser")
            .body(contactsJson).contentType("application/json")
            .when().put(baseUrlOfSut + "/customer/contacts")
            .then().statusCode(404);
    }

    @Test
    public void testGetOrders() {
        given().queryParam("name", "Ivan%20Petrov")
            .when().get(baseUrlOfSut + "/customer/orders")
            .then().statusCode(200);

        given().queryParam("name", "NonExistentUser")
            .when().get(baseUrlOfSut + "/customer/orders")
            .then().statusCode(404);
    }

    @Test
    public void testGetOrder() {
        given().queryParam("name", "Ivan%20Petrov")
            .pathParam("orderId", 1)
            .when().get(baseUrlOfSut + "/customer/orders/{orderId}")
            .then().statusCode(200);

        given().queryParam("name", "NonExistentUser")
            .pathParam("orderId", 1)
            .when().get(baseUrlOfSut + "/customer/orders/{orderId}")
            .then().statusCode(404);
    }

    @Test
    public void testGetProducts() {
        given()
            .when().get(baseUrlOfSut + "/products")
            .then().statusCode(200);
    }

    @Test
    public void testGetProduct() {
        given().pathParam("productId", 1)
            .when().get(baseUrlOfSut + "/products/{productId}")
            .then().statusCode(200);

        given().pathParam("productId", 99999)
            .when().get(baseUrlOfSut + "/products/{productId}")
            .then().statusCode(404);
    }

    @Test
    public void testCreateCustomer() {
        String userJson = "{ \"email\":\"ivan.petrov@yandex.ru\", \"password\":\"petrov\", \"name\":\"Ivan Petrov\", \"phone\":\"+7 123 456 78 90\", \"address\":\"Riesstrasse 18\" }";

        given().body(userJson).contentType("application/json")
            .when().post(baseUrlOfSut + "/register")
            .then().statusCode(201);

        String invalidUserJson = "{ \"email\":\"ivan.petrov@yandex.ru\", \"password\":\"\", \"name\":\"Ivan Petrov\", \"phone\":\"+7 123 456 78 90\", \"address\":\"Riesstrasse 18\" }";

        given().body(invalidUserJson).contentType("application/json")
            .when().post(baseUrlOfSut + "/register")
            .then().statusCode(400);
    }
}
