
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
import io.restassured.module.jsv.JsonSchemaValidator;

public class run01_CartRestControllerTest {
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
        ValidatableResponse res = given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when()
            .get(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200)
            .body("deliveryIncluded", is(false))
            .body("empty", is(true))
            .body("totalItems", is(0));
    }

    @Test
    public void testAddItemToCart() {
        given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .body("{\"productId\":1, \"quantity\":2}")
            .contentType("application/json")
            .when()
            .put(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200)
            .body("totalItems", is(2));
    }

    @Test
    public void testClearCart() {
        given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when()
            .delete(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200)
            .body("totalItems", is(0))
            .body("empty", is(true));
    }

    @Test
    public void testSetDeliveryOption() {
        given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .queryParam("included", true)
            .when()
            .put(baseUrlOfSut + "/customer/cart/delivery")
            .then()
            .statusCode(200)
            .body("deliveryIncluded", is(true));
    }

    @Test
    public void testPayByCard() {
        given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .body("{\"ccNumber\":\"4242424242424242\"}")
            .contentType("application/json")
            .when()
            .post(baseUrlOfSut + "/customer/cart/pay")
            .then()
            .statusCode(201)
            .body("payed", is(true));
    }

    @Test
    public void testAddItemInvalidProduct() {
        given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .body("{\"productId\":9999, \"quantity\":2}")
            .contentType("application/json")
            .when()
            .put(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(404);
    }

    @Test
    public void testPayWithEmptyCart() {
        given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .body("{\"ccNumber\":\"4242424242424242\"}")
            .contentType("application/json")
            .when()
            .post(baseUrlOfSut + "/customer/cart/pay")
            .then()
            .statusCode(500);
    }

    @Test
    public void testSchemaValidation() {
        ValidatableResponse res = given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when()
            .get(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(200)
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/CartDTO.json"));
    }

    @Test
    public void testBusinessRuleEnforcementOnAddItem() {
        given()
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .body("{\"productId\":1, \"quantity\":-1}")
            .contentType("application/json")
            .when()
            .put(baseUrlOfSut + "/customer/cart")
            .then()
            .statusCode(400);
    }
}
