
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

public class run08_CartRestControllerTest {

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
        given().auth().basic("ivan.petrov@yandex.ru", "petrov")
            .when().get(baseUrlOfSut + "/customer/cart")
            .then().statusCode(401);
    }

    @Test
    public void testAddItem() {
        given().auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body("{\"productId\":1, \"quantity\":3}")
            .when().put(baseUrlOfSut + "/customer/cart")
            .then().statusCode(401);
    }

    @Test
    public void testClearCart() {
        given().auth().basic("ivan.petrov@yandex.ru", "petrov")
            .when().delete(baseUrlOfSut + "/customer/cart")
            .then().statusCode(401);
    }

    @Test
    public void testSetDelivery() {
        given().auth().basic("ivan.petrov@yandex.ru", "petrov")
            .queryParam("included", true)
            .when().put(baseUrlOfSut + "/customer/cart/delivery")
            .then().statusCode(401);
    }

    @Test
    public void testPayByCard() {
        given().auth().basic("ivan.petrov@yandex.ru", "petrov")
            .contentType("application/json")
            .body("{\"ccNumber\":\"4111111111111111\"}")
            .when().post(baseUrlOfSut + "/customer/cart/pay")
            .then().statusCode(401);
    }
}
