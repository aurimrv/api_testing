
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
import static org.evomaster.client.java.controller.contentMatchers.NumberMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.StringMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.SubStringMatcher.*;
import static org.evomaster.client.java.controller.expect.ExpectationHandler.expectationHandler;
import org.evomaster.client.java.controller.expect.ExpectationHandler;
import io.restassured.path.json.JsonPath;
import java.util.Arrays;

public class run02_CartRestControllerTest {

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
    void testGetCart() {
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/cart")
            .then().statusCode(200)
            .body("empty", equalTo(false));
    }

    @Test
    void testAddItem() {
        JsonPath item = new JsonPath("{\"productId\": 1, \"quantity\": 2}");
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(item)
            .when().put(baseUrlOfSut + "/customer/cart")
            .then().statusCode(200)
            .body("totalItems", greaterThan(0));
    }

    @Test
    void testClearCart() {
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when().delete(baseUrlOfSut + "/customer/cart")
            .then().statusCode(200)
            .body("empty", equalTo(true));
    }

    @Test
    void testSetDelivery() {
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .queryParam("included", true)
            .when().put(baseUrlOfSut + "/customer/cart/delivery")
            .then().statusCode(200)
            .body("deliveryIncluded", equalTo(true));
    }

    @Test
    void testPayByCard() {
        JsonPath card = new JsonPath("{\"ccNumber\": \"4111111111111111\"}");
        given().auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body(card)
            .when().post(baseUrlOfSut + "/customer/cart/pay")
            .then().statusCode(201)
            .body("payed", equalTo(true));
    }
}
