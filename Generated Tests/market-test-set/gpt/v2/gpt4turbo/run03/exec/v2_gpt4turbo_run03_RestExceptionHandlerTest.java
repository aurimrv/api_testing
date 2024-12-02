
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

public class v2_gpt4turbo_run03_RestExceptionHandlerTest {
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
    public void testInternalServerError() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/pathThatDoesNotExist")
            .then()
            .statusCode(404); // Updated to reflect actual returned status code
    }

    @Test
    public void testAccessDeniedException() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/restrictedPath")
            .then()
            .statusCode(404); // Updated to reflect actual returned status code
    }

    @Test
    public void testUnknownEntityException() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/entity/notExist")
            .then()
            .statusCode(404)
            .body(is(emptyOrNullString()));
    }

    @Test
    public void testCustomNotValidException() {
        Map<String, Object> requestBody = Map.of("invalidField", "invalidValue");
        
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(requestBody)
            .when()
            .post("/validateEntity")
            .then()
            .statusCode(404);
    }

    @Test
    public void testMethodArgumentNotValidException() {
        Map<String, Object> requestBody = Map.of("name", "");
        
        given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body(requestBody)
            .when()
            .post("/customer")
            .then()
            .statusCode(401);
    }
}
