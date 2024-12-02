
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

public class v1_gpt4turbo_run03_RestExceptionHandlerTest {
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
    void testAccessDeniedExceptionHandling() {
        given().baseUri(baseUrlOfSut)
               .when()
               .get("/customer")
               .then()
               .statusCode(401)
               .body("message", containsString("Access Denied"));
    }

    @Test
    void testUnknownEntityExceptionHandling() {
        given().baseUri(baseUrlOfSut)
               .when()
               .get("/customer/orders/99999")
               .then()
               .statusCode(404)
               .body("message", containsString("entity not found"));
    }

    @Test
    void testCustomNotValidExceptionHandling() {
        given().baseUri(baseUrlOfSut)
               .contentType("application/json")
               .body("{\"name\":\"\",\"email\":\"invalid-email\"}")
               .when()
               .post("/register")
               .then()
               .statusCode(406)
               .body("message", containsString("Validation failed"));
    }

    @Test
    void testMethodArgumentNotValidExceptionHandling() {
        given().baseUri(baseUrlOfSut)
               .contentType("application/json")
               .body("{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"address\":\"\",\"phone\":\"+999 999 999\"}")
               .when()
               .post("/customer/contacts")
               .then()
               .statusCode(406)
               .body("fieldErrors", not(empty()));
    }

    @Test
    void testOtherExceptionsHandling() {
        // Simulate an endpoint that causes an unexpected server error
        given().baseUri(baseUrlOfSut)
               .when()
               .get("/simulate-server-error")
               .then()
               .statusCode(500)
               .body("message", containsString("Unexpected error occurred"));
    }
}
