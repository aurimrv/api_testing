
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

public class v0_gpt4o_run01_CostfunsTest {

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
    public void testCostfunsAllResponses() {
        // All possible values for 'i' and 's' to maximize decision coverage
        testCostfuns(5, "test", "1");
        testCostfuns(-445, "test", "2");
        testCostfuns(-333, "test", "3");
        testCostfuns(667, "test", "4");
        testCostfuns(555, "test", "5");
        testCostfuns(-3, "test", "6");
        testCostfuns(0, "baab", "7");
        testCostfuns(0, "ababababab", "8");
        testCostfuns(0, "abababababab", "9");
        testCostfuns(0, "abab", "10");

        // Additional checks for other response codes
        testInvalidRequest(401);
        testInvalidRequest(403);
        testInvalidRequest(404);
    }

    private void testCostfuns(int i, String s, String expectedResult) {
        given()
            .pathParam("i", i)
            .pathParam("s", s)
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo(expectedResult));
    }

    private void testInvalidRequest(int statusCode) {
        given()
            .pathParam("i", "invalid")
            .pathParam("s", "invalid")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(statusCode);
    }
}
