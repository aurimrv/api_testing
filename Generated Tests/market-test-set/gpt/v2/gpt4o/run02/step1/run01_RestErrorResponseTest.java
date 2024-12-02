
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
import static org.hamcrest.Matchers.*;
import io.restassured.config.JsonConfig;
import io.restassured.path.json.config.JsonPathConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static org.evomaster.client.java.sql.dsl.SqlDsl.sql;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionResultsDto;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionDto;
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
        controller.resetDatabase(Arrays.asList("USER_ROLE", "CUSTOMER_ORDER", "CART_ITEM", "PRODUCT", "CART", "CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    @Timeout(30)
    public void testGetCustomerInvalidInput() {
        given().baseUri(baseUrlOfSut)
                .queryParam("name", "")
                .when().get("/customer")
                .then().statusCode(400);
    }

    @Test
    @Timeout(30)
    public void testGetCustomerNotFound() {
        given().baseUri(baseUrlOfSut)
                .queryParam("name", "nonexistent")
                .when().get("/customer")
                .then().statusCode(404);
    }

    @Test
    @Timeout(30)
    public void testGetCustomerInternalError() {
        // Simulate server error by sending request to a non-existent endpoint
        given().baseUri(baseUrlOfSut)
                .queryParam("name", "Ivan")
                .when().get("/customer/invalid")
                .then().statusCode(500);
    }

    @Test
    @Timeout(30)
    public void testSchemaValidationGetCustomer() {
        given().baseUri(baseUrlOfSut)
                .queryParam("name", "Ivan")
                .when().get("/customer")
                .then().statusCode(200)
                .body("email", matchesPattern("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]+$"))
                .body("name", matchesPattern("^[\\pL '-]+$"))
                .body("phone", matchesPattern("^\\+[1-9][0-9]?[\\s]*\\(?\\d{3}\\)?[-\\s]?\\d{3}[-\\s]?\\d{2}[-\\s]?\\d{2}$"));
    }

    @Test
    @Timeout(30)
    public void testCreateCustomer() {
        String payload = "{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}";

        given().baseUri(baseUrlOfSut)
                .contentType("application/json")
                .body(payload)
                .when().post("/register")
                .then().statusCode(201)
                .body("email", equalTo("ivan.petrov@yandex.ru"))
                .body("name", equalTo("Ivan Petrov"))
                .body("phone", equalTo("+7 123 456 78 90"))
                .body("address", equalTo("Riesstrasse 18"));
    }

    @Test
    @Timeout(30)
    public void testUpdateContacts() {
        String payload = "{\"address\":\"New Address\",\"phone\":\"+7 987 654 32 10\"}";

        given().baseUri(baseUrlOfSut)
                .contentType("application/json")
                .body(payload)
                .when().put("/customer/contacts")
                .then().statusCode(200)
                .body("address", equalTo("New Address"))
                .body("phone", equalTo("+7 987 654 32 10"));
    }

    @Test
    @Timeout(30)
    public void testUpdateContactsInvalidInput() {
        String payload = "{\"address\":\"\",\"phone\":\"invalid phone\"}";

        given().baseUri(baseUrlOfSut)
                .contentType("application/json")
                .body(payload)
                .when().put("/customer/contacts")
                .then().statusCode(400);
    }

    @Test
    @Timeout(30)
    public void testAddCartItem() {
        String payload = "{\"productId\":1,\"quantity\":2}";

        given().baseUri(baseUrlOfSut)
                .contentType("application/json")
                .body(payload)
                .when().put("/customer/cart")
                .then().statusCode(200)
                .body("cartItems[0].productId", equalTo(1))
                .body("cartItems[0].quantity", equalTo(2));
    }

    @Test
    @Timeout(30)
    public void testAddCartItemInvalidInput() {
        String payload = "{\"productId\":-1,\"quantity\":0}";

        given().baseUri(baseUrlOfSut)
                .contentType("application/json")
                .body(payload)
                .when().put("/customer/cart")
                .then().statusCode(400);
    }

    @Test
    @Timeout(30)
    public void testClearCart() {
        given().baseUri(baseUrlOfSut)
                .when().delete("/customer/cart")
                .then().statusCode(200);
    }

    @Test
    @Timeout(30)
    public void testSetDelivery() {
        given().baseUri(baseUrlOfSut)
                .queryParam("included", true)
                .when().put("/customer/cart/delivery")
                .then().statusCode(200)
                .body("deliveryIncluded", equalTo(true));
    }

    @Test
    @Timeout(30)
    public void testPayByCard() {
        String payload = "{\"ccNumber\":\"1234 5678 9012 3456\"}";

        given().baseUri(baseUrlOfSut)
                .contentType("application/json")
                .body(payload)
                .when().post("/customer/cart/pay")
                .then().statusCode(201)
                .body("payed", equalTo(true));
    }

    @Test
    @Timeout(30)
    public void testPayByCardInvalidInput() {
        String payload = "{\"ccNumber\":\"invalid card number\"}";

        given().baseUri(baseUrlOfSut)
                .contentType("application/json")
                .body(payload)
                .when().post("/customer/cart/pay")
                .then().statusCode(400);
    }
}
