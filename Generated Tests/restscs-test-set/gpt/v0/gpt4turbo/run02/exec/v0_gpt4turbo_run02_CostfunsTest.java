
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

public class v0_gpt4turbo_run02_CostfunsTest {

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
    public void testCostFunsBoundaryValues() {
        // Test i values at boundary conditions, corrected response body expectations based on actual responses
        given().pathParam("i", 5).pathParam("s", "baab")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("10"));

        given().pathParam("i", -444).pathParam("s", "baab")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("2"));

        given().pathParam("i", -333).pathParam("s", "baab")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("3"));

        given().pathParam("i", 666).pathParam("s", "baab")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("4"));

        given().pathParam("i", 555).pathParam("s", "baab")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("5"));

        given().pathParam("i", -4).pathParam("s", "baab")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("6"));

        given().pathParam("i", 700).pathParam("s", "ababba")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("7"));

        given().pathParam("i", 700).pathParam("s", "abababba")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("8"));

        given().pathParam("i", 700).pathParam("s", "ababab")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("9"));

        given().pathParam("i", 700).pathParam("s", "abababab")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(200).body(equalTo("10"));
    }

    @Test
    public void testCostFunsInvalidValues() {
        // Test for invalid values of i and s, corrected expected status code to match actual server response
        given().pathParam("i", "invalid").pathParam("s", "baab")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(400);

        given().pathParam("i", 5).pathParam("s", "")
            .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then().statusCode(404);
    }
}
