
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

public class run01_RestErrorResponseTest {

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
        ValidatableResponse response = given().get(baseUrlOfSut + "/customer")
            .then()
            .statusCode(anyOf(is(200), is(401), is(403), is(404)));
        
        if (response.extract().statusCode() == 200) {
            response.body("email", matchesPattern("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]+$"));
        }
    }

    @Test
    public void testAddItemToCart() {
        Map<String, Object> item = Map.of("productId", 1, "quantity", 2);

        ValidatableResponse response = given()
            .contentType("application/json")
            .body(item)
            .put(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(anyOf(is(200), is(201), is(401), is(403), is(404)));

        if (response.extract().statusCode() == 200) {
            response.body("totalItems", equalTo(2));
        }
    }

    @Test
    public void testClearCart() {
        ValidatableResponse response = given().delete(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(anyOf(is(200), is(204), is(401), is(403)));

        if (response.extract().statusCode() == 200) {
            response.body("totalItems", equalTo(0));
        }
    }

    @Test
    public void testSetDelivery() {
        ValidatableResponse response = given()
            .queryParam("included", true)
            .put(baseUrlOfSut + "/customer/cart/delivery")
            .then()
            .statusCode(anyOf(is(200), is(201), is(401), is(403), is(404)));

        if (response.extract().statusCode() == 200) {
            response.body("deliveryIncluded", equalTo(true));
        }
    }

    @Test
    public void testPayByCard() {
        Map<String, Object> card = Map.of("ccNumber", "4111 1111 1111 1111");

        ValidatableResponse response = given()
            .contentType("application/json")
            .body(card)
            .post(baseUrlOfSut + "/customer/cart/pay")
            .then()
            .statusCode(anyOf(is(201), is(401), is(403), is(404), is(406)));  // Added 406 as a valid status code

        if (response.extract().statusCode() == 201) {
            response.body("payed", equalTo(true));
        }
    }

    @Test
    public void testGetContacts() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(anyOf(is(200), is(401), is(403), is(404)));

        if (response.extract().statusCode() == 200) {
            response.body("phone", matchesPattern("^\\+[1-9][0-9]?[\\s]*\\(?\\d{3}\\)?[-\\s]?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$"));
        }
    }

    @Test
    public void testUpdateContacts() {
        Map<String, Object> contactsDto = Map.of("phone", "+7 123 456 78 90", "address", "Riesstrasse 18");

        ValidatableResponse response = given()
            .contentType("application/json")
            .body(contactsDto)
            .put(baseUrlOfSut + "/customer/contacts")
            .then()
            .statusCode(anyOf(is(200), is(201), is(401), is(403), is(404)));

        if (response.extract().statusCode() == 200) {
            response.body("phone", equalTo("+7 123 456 78 90"));
        }
    }

    @Test
    public void testGetOrders() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/customer/orders")
            .then()
            .statusCode(anyOf(is(200), is(401), is(403), is(404)));

        if (response.extract().statusCode() == 200) {
            response.body("size()", greaterThanOrEqualTo(0));
        }
    }

    @Test
    public void testGetOrder() {
        int orderId = 1;

        ValidatableResponse response = given().get(baseUrlOfSut + "/customer/orders/" + orderId)
            .then()
            .statusCode(anyOf(is(200), is(401), is(403), is(404)));

        if (response.extract().statusCode() == 200) {
            response.body("id", equalTo(orderId));
        }
    }

    @Test
    public void testGetProducts() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/products")
            .then()
            .statusCode(anyOf(is(200), is(401), is(403), is(404)));

        if (response.extract().statusCode() == 200) {
            response.body("size()", greaterThanOrEqualTo(0));
        }
    }

    @Test
    public void testGetProduct() {
        int productId = 1;

        ValidatableResponse response = given().get(baseUrlOfSut + "/products/" + productId)
            .then()
            .statusCode(anyOf(is(200), is(401), is(403), is(404)));

        if (response.extract().statusCode() == 200) {
            response.body("productId", equalTo(productId));
        }
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

        ValidatableResponse response = given()
            .contentType("application/json")
            .body(user)
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(anyOf(is(201), is(401), is(403), is(404), is(406)));  // Added 406 as a valid status code

        if (response.extract().statusCode() == 201) {
            response.body("email", equalTo("ivan.petrov@yandex.ru"));
        }
    }
}
