
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

public class run02_RestExceptionHandlerTest {

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
        given().baseUri(baseUrlOfSut)
               .when().get("/customer")
               .then().statusCode(401)
               .body("message", equalTo("Access is denied"));
    }

    @Test
    void testUnknownEntityException() {
        given().baseUri(baseUrlOfSut)
               .when().get("/customer/999")
               .then().statusCode(404)
               .body("message", containsString("Entity not found"));
    }

    @Test
    void testCustomNotValidException() {
        given().baseUri(baseUrlOfSut)
               .contentType("application/json")
               .body("{\"email\":\"invalid-email\", \"password\":\"123\", \"name\":\"\", \"phone\":\"invalid\", \"address\":\"\"}")
               .when().post("/register")
               .then().statusCode(406)
               .body("message", containsString("Validation failed"));
    }

    @Test
    void testMethodArgumentNotValidException() {
        given().baseUri(baseUrlOfSut)
               .contentType("application/json")
               .body("{\"email\":\"ivan.petrov@yandex.ru\", \"password\":\"petrov\", \"name\":\"Ivan Petrov\", \"phone\":\"+7 123 456 78 90\", \"address\":\"Riesstrasse 18\"}")
               .when().post("/register")
               .then().statusCode(406)
               .body("message", containsString("Validation failed"));
    }

    @Test
    void testOtherExceptions() {
        given().baseUri(baseUrlOfSut)
               .when().get("/nonexistentpath")
               .then().statusCode(500)
               .body("message", equalTo("Internal Server Error"));
    }
}
