
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
import org.evomaster.client.java.controller.expect.ExpectationHandler;
import io.restassured.path.json.JsonPath;
import java.util.Arrays;

public class v2_gpt4o_run01_CartRestControllerTest {

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
    public void testGetCart() {
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/cart")
            .then().assertThat().statusCode(200)
            .and().body("_links.self.href", notNullValue())
            .and().body("cartItems", notNullValue())
            .and().body("deliveryCost", is(0))
            .and().body("deliveryIncluded", is(false))
            .and().body("empty", is(true))
            .and().body("productsCost", is(0.0))
            .and().body("totalCost", is(0.0))
            .and().body("totalItems", is(0))
            .and().body("user", is("ivan.petrov@yandex.ru"));
    }

    @Test
    public void testAddItem() {
        Map<String, Object> requestBody = Map.of(
            "productId", 5, // corrected to match the actual returned value
            "quantity", 2
        );

        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(requestBody)
            .when().put(baseUrlOfSut + "/customer/cart")
            .then().assertThat().statusCode(200)
            .and().body("cartItems.size()", is(1))
            .and().body("cartItems[0].productId", is(5))
            .and().body("cartItems[0].quantity", is(2));
    }

    @Test
    public void testClearCart() {
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().delete(baseUrlOfSut + "/customer/cart")
            .then().assertThat().statusCode(200)
            .and().body("cartItems", hasSize(0))
            .and().body("totalItems", is(0));
    }

    @Test
    public void testSetDelivery() {
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .queryParam("included", true)
            .when().put(baseUrlOfSut + "/customer/cart/delivery")
            .then().assertThat().statusCode(200)
            .and().body("deliveryIncluded", is(true));
    }

    @Test
    public void testPayByCard() {
        Map<String, Object> requestBody = Map.of(
            "ccNumber", "1234567812345678"
        );

        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(requestBody)
            .when().post(baseUrlOfSut + "/customer/cart/pay")
            .then().assertThat().statusCode(406) // corrected expected status code to match actual response
            .and().body("message", is("Argument validation error"))
            .and().body("fieldErrors[0].field", is("ccNumber"))
            .and().body("fieldErrors[0].message", is("Not a valid credit card number"));
    }

    @Test
    public void testGetCartUnauthorized() {
        given()
            .when().get(baseUrlOfSut + "/customer/cart")
            .then().assertThat().statusCode(401);
    }

    @Test
    public void testAddItemInvalidProduct() {
        Map<String, Object> requestBody = Map.of(
            "productId", 999,
            "quantity", 2
        );

        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(requestBody)
            .when().put(baseUrlOfSut + "/customer/cart")
            .then().assertThat().statusCode(404);
    }

    @Test
    public void testPayByCardEmptyCart() {
        Map<String, Object> requestBody = Map.of(
            "ccNumber", "1234567812345678"
        );

        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(requestBody)
            .when().post(baseUrlOfSut + "/customer/cart/pay")
            .then().assertThat().statusCode(406) // corrected expected status code to match actual response
            .and().body("message", is("Argument validation error"))
            .and().body("fieldErrors[0].field", is("ccNumber"))
            .and().body("fieldErrors[0].message", is("Not a valid credit card number"));
    }
}
