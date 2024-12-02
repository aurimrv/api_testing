
package market;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        controller.resetDatabase(Arrays.asList("USER_ROLE", "CUSTOMER_ORDER", "CART_ITEM", "PRODUCT", "CART", "CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    void testGetCartValidUser() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/cart")
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when()
            .get()
            .then()
            .statusCode(200)
            .body("user", equalTo("ivan.petrov@yandex.ru"));

        JsonPath jsonPath = new JsonPath(response.extract().asString());
        assertEquals("ivan.petrov@yandex.ru", jsonPath.getString("user"));
        assertFalse(jsonPath.getBoolean("empty"));
    }

    @Test
    void testAddItemUnknownProduct() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/cart")
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body("{\"productId\":9999,\"quantity\":1}")
            .when()
            .put()
            .then()
            .statusCode(404); // Assuming 404 Not Found for unknown product
    }

    @Test
    void testClearCartValidUser() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/cart")
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .when()
            .delete()
            .then()
            .statusCode(200)
            .body("empty", equalTo(true));
    }

    @Test
    void testSetDeliveryOption() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/cart/delivery")
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .param("included", true)
            .when()
            .put()
            .then()
            .statusCode(200)
            .body("deliveryIncluded", equalTo(true));
    }

    @Test
    void testPayByCardEmptyCart() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/customer/cart/pay")
            .auth().preemptive().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body("{\"ccNumber\":\"1234123412341234\"}")
            .when()
            .post()
            .then()
            .statusCode(406)
            .body("fieldErrors.field", hasItem("ccNumber"),
                  "fieldErrors.message", hasItem("Not a valid credit card number"));
    }
}
