
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

public class v2_gpt4turbo_run02_CostfunsTest {
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
    public void testCostFunsValidResponse() {
        // Testing successful response
        given().when().get(baseUrlOfSut + "/api/costfuns/5/baab").then()
            .statusCode(200)
            .body(is("10"));
    }

    @Test
    public void testCostFunsInvalidInput() {
        // Testing error scenario with invalid integer input
        given().when().get(baseUrlOfSut + "/api/costfuns/-1000/baab").then()
            .statusCode(200) // Assuming no 500 error, as the service handles negatives gracefully
            .body(is("10"));
    }

    @Test
    public void testCostFunsSchemaValidation() {
        // Testing response schema validation
        given().when().get(baseUrlOfSut + "/api/costfuns/5/baab").then()
            .body(matchesPattern("\\d+")); // Assuming the response should be numeric
    }

    @Test
    public void testCostFunsBusinessRule() {
        // Testing business rule that i should be 5 to get a result of '10'
        given().when().get(baseUrlOfSut + "/api/costfuns/5/baab").then()
            .body(is("10"));
        given().when().get(baseUrlOfSut + "/api/costfuns/1/baab").then()
            .body(not(is("10")));
    }

    @Test
    public void testCostFunsErrorHandling() {
        // Testing how the application handles unexpected inputs leading to server errors
        given().when().get(baseUrlOfSut + "/api/costfuns/5/").then()
            .statusCode(404);
    }
}
