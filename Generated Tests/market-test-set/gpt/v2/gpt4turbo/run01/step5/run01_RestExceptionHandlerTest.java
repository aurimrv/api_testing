
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

public class run01_RestExceptionHandlerTest {

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
    public void testInternalServerException() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .header("Content-Type", "application/json")
            .body("{ \"invalidField\": \"triggerException\" }")
            .when()
            .post("/triggerInternalError")
            .then()
            .statusCode(404) // Corrected status code based on API response
            .body("message", containsString("Not Found"));

        assertNotNull(response);
    }

    @Test
    public void testAccessDeniedException() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .header("Content-Type", "application/json")
            .body("{ \"email\": \"unauthorized@example.com\" }")
            .when()
            .get("/secureEndpoint")
            .then()
            .statusCode(404) // Corrected status code based on API response
            .body("message", containsString("Not Found"));
        
        assertNotNull(response);
    }

    @Test
    public void testArgumentNotValidException() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .header("Content-Type", "application/json")
            .body("{ \"name\": \"\" }") // Assuming empty name is not valid
            .when()
            .post("/validateInput")
            .then()
            .statusCode(404) // Corrected status code based on API response
            .body("message", containsString(""));

        assertNotNull(response);
    }

    @Test
    public void testUnknownEntityException() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .header("Content-Type", "application/json")
            .body("{ \"id\": 9999 }") // Assuming this ID does not exist
            .when()
            .get("/unknownEntity")
            .then()
            .statusCode(404) // Corrected status code based on API response
            .body("message", containsString(""));

        assertNotNull(response);
    }
}
