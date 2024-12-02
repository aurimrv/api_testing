
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
        controller.resetDatabase(Arrays.asList("USER_ROLE","CUSTOMER_ORDER","CART_ITEM","PRODUCT","CART","CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    @Timeout(60)
    public void testGetCustomer() {
        // Unauthenticated request should return 401
        given().baseUri(baseUrlOfSut)
               .when().get("/customer")
               .then().statusCode(401);

        // Register a new user
        String userJson = "{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}";
        given().baseUri(baseUrlOfSut)
               .contentType("application/json")
               .body(userJson)
               .when().post("/register")
               .then().statusCode(201).body("fieldErrors", hasSize(0));

        // Successful request should return 200
        given().baseUri(baseUrlOfSut)
               .auth().basic("ivan.petrov@yandex.ru", "petrov")
               .when().get("/customer")
               .then().statusCode(200);
    }

    @Test
    @Timeout(60)
    public void testGetNonExistentCustomer() {
        // Register a new user
        String userJson = "{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}";
        given().baseUri(baseUrlOfSut)
               .contentType("application/json")
               .body(userJson)
               .when().post("/register")
               .then().statusCode(201).body("fieldErrors", hasSize(0));

        // Request non-existent customer should return 404
        given().baseUri(baseUrlOfSut)
               .auth().basic("ivan.petrov@yandex.ru", "petrov")
               .when().get("/customer?name=NonExistent")
               .then().statusCode(404);
    }

    @Test
    @Timeout(60)
    public void testGetUnauthorized() {
        // Attempt to access a protected resource without authentication
        given().baseUri(baseUrlOfSut)
               .when().get("/customer/cart")
               .then().statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testAddItemUnauthorized() {
        // Attempt to add an item to the cart without authentication
        String itemJson = "{\"productId\":1,\"quantity\":1}";
        given().baseUri(baseUrlOfSut)
               .contentType("application/json")
               .body(itemJson)
               .when().put("/customer/cart")
               .then().statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testClearCartUnauthorized() {
        // Attempt to clear the cart without authentication
        given().baseUri(baseUrlOfSut)
               .when().delete("/customer/cart")
               .then().statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testSetDeliveryUnauthorized() {
        // Attempt to set delivery without authentication
        given().baseUri(baseUrlOfSut)
               .queryParam("included", true)
               .when().put("/customer/cart/delivery")
               .then().statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testPayByCardUnauthorized() {
        // Attempt to pay by card without authentication
        String cardJson = "{\"ccNumber\":\"1234-5678-9012-3456\"}";
        given().baseUri(baseUrlOfSut)
               .contentType("application/json")
               .body(cardJson)
               .when().post("/customer/cart/pay")
               .then().statusCode(401).body("fieldErrors", hasSize(0));
    }

    @Test
    @Timeout(60)
    public void testUpdateContactsUnauthorized() {
        // Attempt to update contacts without authentication
        String contactsJson = "{\"phone\":\"+7 123 456 78 90\",\"address\":\"New Address\"}";
        given().baseUri(baseUrlOfSut)
               .contentType("application/json")
               .body(contactsJson)
               .when().put("/customer/contacts")
               .then().statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testGetOrdersUnauthorized() {
        // Attempt to get orders without authentication
        given().baseUri(baseUrlOfSut)
               .when().get("/customer/orders")
               .then().statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testGetOrderUnauthorized() {
        // Attempt to get a specific order without authentication
        given().baseUri(baseUrlOfSut)
               .when().get("/customer/orders/1")
               .then().statusCode(401);
    }

    @Test
    @Timeout(60)
    public void testGetProducts() {
        // Successful request should return 200
        given().baseUri(baseUrlOfSut)
               .when().get("/products")
               .then().statusCode(200);
    }

    @Test
    @Timeout(60)
    public void testGetProduct() {
        // Successful request should return 200
        given().baseUri(baseUrlOfSut)
               .when().get("/products/1")
               .then().statusCode(200);
    }
}
