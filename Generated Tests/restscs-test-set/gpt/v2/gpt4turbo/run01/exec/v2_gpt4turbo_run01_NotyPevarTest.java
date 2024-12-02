
package org.restscs;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
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

public class v2_gpt4turbo_run01_NotyPevarTest {

    private static final SutHandler controller = new em.embedded.org.restscs.EmbeddedEvoMasterController();
    private static String baseUrlOfSut;

    @BeforeClass
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

    @AfterClass
    public static void tearDown() {
        controller.stopSut();
    }

    @Before
    public void initTest() {
        controller.resetStateOfSUT();
    }

    @Test
    public void testNotyPevarValidInput() {
        int i = 28; // This should trigger the correct response based on API's business logic (assumed here as "3")
        String s = "hello";
        given()
            .pathParam("i", i)
            .pathParam("s", s)
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("3"));
    }

    @Test
    public void testNotyPevarInvalidInput() {
        int i = 100; // Invalid input to force error, expecting error handling logic instead of just the 500 status
        String s = "test";
        given()
            .pathParam("i", i)
            .pathParam("s", s)
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then()
            .statusCode(400); // Expecting 400 Bad Request or any handled error response code
    }

    @Test
    public void testNotyPevarBoundaryInput() {
        int i = 0; // Boundary value for integer
        String s = "a"; // Minimal string input
        given()
            .pathParam("i", i)
            .pathParam("s", s)
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("0")); // Expecting result based on API's logic for boundary input
    }

    @Test
    public void testNotyPevarSchemaValidation() {
        int i = 28;
        String s = "hello";
        given()
            .pathParam("i", i)
            .pathParam("s", s)
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then()
            .statusCode(200)
            .body(matchesPattern("^[0-3]$")); // Schema specifies result should be a single digit string [0-3]
    }
}
