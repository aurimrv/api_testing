
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

public class run08_RestExceptionHandlerTest {
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
               .when()
               .get("/api/protected-resource")
               .then()
               .statusCode(401)
               .body("message", equalTo("Access Denied"));
    }

    @Test
    void testUnknownEntityException() {
        given().baseUri(baseUrlOfSut)
               .when()
               .get("/api/nonexistent-entity")
               .then()
               .statusCode(404)
               .body("message", containsString("does not exist"));
    }

    @Test
    void testCustomNotValidException() {
        Map<String, Object> invalidData = Map.of("field", "invalid");
        given().baseUri(baseUrlOfSut)
               .contentType("application/json")
               .body(invalidData)
               .when()
               .post("/api/validate-entity")
               .then()
               .statusCode(406)
               .body("message", containsString("Validation failed"));
    }

    @Test
    void testMethodArgumentNotValidException() {
        Map<String, Object> invalidData = Map.of("number", "ten");
        given().baseUri(baseUrlOfSut)
               .contentType("application/json")
               .body(invalidData)
               .when()
               .post("/api/validate-number")
               .then()
               .statusCode(406)
               .body("fieldErrors.number", containsString("must be a number"));
    }

    @Test
    void testOtherExceptions() {
        given().baseUri(baseUrlOfSut)
               .when()
               .get("/api/trigger-error")
               .then()
               .statusCode(500)
               .body("message", containsString("Unexpected error occurred"));
    }
}
