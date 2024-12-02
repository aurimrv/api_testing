
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

public class v1_gpt4turbo_run02_RestExceptionHandlerTest {

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
    void testAccessDeniedException() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .when().get("/customer")
            .then().statusCode(401)
            .body("message", equalTo("Access is denied"));

        response.log().ifValidationFails();
    }

    @Test
    void testUnknownEntityException() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("productId", 999) // Assuming 999 is a non-existing product ID
            .when().get("/products/{productId}")
            .then().statusCode(404)
            .body("message", equalTo("Entity not found"));

        response.log().ifValidationFails();
    }

    @Test
    void testCustomNotValidException() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"email\":\"invalid-email-format\", \"password\":\"123\", \"name\":\"\", \"phone\":\"123\", \"address\":\"\"}")
            .when().post("/register")
            .then().statusCode(406) // NOT_ACCEPTABLE
            .body("message", equalTo("Validation failed for argument"));

        response.log().ifValidationFails();
    }

    @Test
    void testMethodArgumentNotValidException() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"email\":\"ivan.petrov@yandex.ru\", \"password\":\"petrov\", \"name\":\"Ivan Petrov\", \"phone\":\"invalid-phone-number\", \"address\":\"Riesstrasse 18\"}")
            .when().post("/register")
            .then().statusCode(406) // NOT_ACCEPTABLE
            .body("message", equalTo("Validation failed for argument"));

        response.log().ifValidationFails();
    }

    @Test
    void testOtherExceptions() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .when().delete("/products/999") // Assuming DELETE is not allowed for products
            .then().statusCode(500)
            .body("message", containsString("Internal Server Error"));

        response.log().ifValidationFails();
    }
}
