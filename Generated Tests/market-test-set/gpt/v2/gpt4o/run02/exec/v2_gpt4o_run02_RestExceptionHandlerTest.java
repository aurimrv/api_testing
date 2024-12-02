
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

public class v2_gpt4o_run02_RestExceptionHandlerTest {

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
    public void testGetCustomerInvalidName() {
        given()
            .queryParam("name", "invalidName")
            .auth().basic("username", "password")
            .when()
            .get(baseUrlOfSut + "/customer")
            .then()
            .statusCode(404);
    }

    @Test
    public void testGetCartUnauthorized() {
        given()
            .when()
            .get(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(401);
    }

    @Test
    public void testAddItemUnauthorized() {
        given()
            .body("{\"productId\": 1, \"quantity\": 1}")
            .contentType("application/json")
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
    public void testSetDeliveryUnauthorized() {
        given()
            .queryParam("included", true)
            .when()
            .put(baseUrlOfSut + "/customer/cart/delivery")
            .then()
            .statusCode(401);
    }

    @Test
    public void testPayByCardUnauthorized() {
        given()
            .body("{\"ccNumber\": \"1234 5678 9012 3456\"}")
            .contentType("application/json")
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
        given()
            .body("{\"address\": \"New Address\", \"phone\": \"+7 123 456 78 90\"}")
            .contentType("application/json")
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
    public void testGetOrderNotFound() {
        given()
            .pathParam("orderId", 999)
            .auth().basic("username", "password")
            .when()
            .get(baseUrlOfSut + "/customer/orders/{orderId}")
            .then()
            .statusCode(404);
    }

    @Test
    public void testGetProductsUnauthorized() {
        given()
            .when()
            .get(baseUrlOfSut + "/products")
            .then()
            .statusCode(401);
    }

    @Test
    public void testGetProductNotFound() {
        given()
            .pathParam("productId", 999)
            .auth().basic("username", "password")
            .when()
            .get(baseUrlOfSut + "/products/{productId}")
            .then()
            .statusCode(404);
    }

    @Test
    public void testCreateCustomerInvalidData() {
        given()
            .body("{\"email\":\"invalidEmail\",\"password\":\"short\",\"name\":\"\",\"phone\":\"invalidPhone\",\"address\":\"invalidAddress\"}")
            .contentType("application/json")
            .when()
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(406);
    }
    
    @Test
    public void testCreateCustomerValidData() {
        given()
            .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
            .contentType("application/json")
            .when()
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(201)
            .body("email", equalTo("ivan.petrov@yandex.ru"))
            .body("name", equalTo("Ivan Petrov"))
            .body("phone", equalTo("+7 123 456 78 90"))
            .body("address", equalTo("Riesstrasse 18"));
    }

    @Test
    public void testGetCustomerValidData() {
        // First, create the user
        testCreateCustomerValidData();

        // Then, retrieve the user
        given()
            .queryParam("name", "Ivan Petrov")
            .auth().basic("username", "password")
            .when()
            .get(baseUrlOfSut + "/customer")
            .then()
            .statusCode(200)
            .body("email", equalTo("ivan.petrov@yandex.ru"))
            .body("name", equalTo("Ivan Petrov"))
            .body("phone", equalTo("+7 123 456 78 90"))
            .body("address", equalTo("Riesstrasse 18"));
    }
}
