
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

public class v2_gpt4turbo_run03_NotyPevarTest {
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
    public void testSubjectWithInvalidInputLeadingToServerError() {
        int invalidInput = 1000; // Assuming this input would lead to a server error.
        String invalidString = "ThisIsInvalidString";
        
        given().pathParam("i", invalidInput).pathParam("s", invalidString)
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(500); // Checking for Internal Server Error
    }

    @Test
    public void testSubjectWithValidInput() {
        int validInput = 28; // This should pass the condition if x + y = 56
        String validString = "hello";
        
        given().pathParam("i", validInput).pathParam("s", validString)
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(200)
            .body(is("0")); // Expecting the result as string "0"
    }

    @Test
    public void testSubjectResponseSchema() {
        int testInput = 5;
        String testString = "test";
        
        given().pathParam("i", testInput).pathParam("s", testString)
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(200)
            .body(matchesPattern("\\d+")); // Pattern to match numeric string
    }

    @Test
    public void testSubjectBusinessRule() {
        // This test ensures that the business rule "x + y = 56" correctly affects the output
        int testInput = 28; // This should pass the condition if x + y = 56
        String testString = "hello";
        
        given().pathParam("i", testInput).pathParam("s", testString)
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("28")); // Asserting that result is '28' as per business rule
    }
}
