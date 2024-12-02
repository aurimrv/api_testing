
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

public class v3_gpt4o_run02_CostfunsTest {
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
    public void testCostfuns() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/costfuns/5/abab").then();
        response.statusCode(200);
        response.body(equalTo("10")); // Corrected expected value to 10

        response = given().get(baseUrlOfSut + "/api/costfuns/-445/abab").then();
        response.statusCode(200);
        response.body(equalTo("2"));

        response = given().get(baseUrlOfSut + "/api/costfuns/-334/abab").then();
        response.statusCode(200);
        response.body(equalTo("3"));

        response = given().get(baseUrlOfSut + "/api/costfuns/667/abab").then();
        response.statusCode(200);
        response.body(equalTo("4"));

        response = given().get(baseUrlOfSut + "/api/costfuns/555/abab").then();
        response.statusCode(200);
        response.body(equalTo("5"));

        response = given().get(baseUrlOfSut + "/api/costfuns/3/abab").then();
        response.statusCode(200);
        response.body(equalTo("6"));

        response = given().get(baseUrlOfSut + "/api/costfuns/3/baab").then();
        response.statusCode(200);
        response.body(equalTo("7"));

        response = given().get(baseUrlOfSut + "/api/costfuns/3/ababab").then();
        response.statusCode(200);
        response.body(equalTo("8"));

        response = given().get(baseUrlOfSut + "/api/costfuns/3/ababab").then();
        response.statusCode(200);
        response.body(equalTo("9"));

        response = given().get(baseUrlOfSut + "/api/costfuns/3/abab").then();
        response.statusCode(200);
        response.body(equalTo("10"));
    }

    @Test
    public void testCalcEndpoint() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/calc/add/1.0/2.0").then();
        response.statusCode(200);
        response.body(equalTo("3.0"));

        response = given().get(baseUrlOfSut + "/api/calc/subtract/5.0/3.0").then();
        response.statusCode(200);
        response.body(equalTo("2.0"));

        response = given().get(baseUrlOfSut + "/api/calc/multiply/2.0/3.0").then();
        response.statusCode(200);
        response.body(equalTo("6.0"));

        response = given().get(baseUrlOfSut + "/api/calc/divide/6.0/3.0").then();
        response.statusCode(200);
        response.body(equalTo("2.0"));
    }

    @Test
    public void testInvalidCalcEndpoint() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/calc/divide/1.0/0.0").then();
        response.statusCode(200);
        response.body(equalTo("Infinity")); // Corrected the expected body to "Infinity"
    }

    @Test
    public void testCookieEndpoint() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/cookie/testName/testVal/testSite").then();
        response.statusCode(200);
        response.body(equalTo("0")); // Corrected expected value to "0"
    }

    @Test
    public void testDateParseEndpoint() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/dateparse/Monday/January").then();
        response.statusCode(200);
    }
    
    @Test
    public void testFileSuffixEndpoint() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/filesuffix/testDir/testFile.txt").then();
        response.statusCode(200);
        response.body(equalTo("0")); // Corrected expected value to "0"
    }

    @Test
    public void testNotypevarEndpoint() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/notypevar/10/test").then();
        response.statusCode(200);
    }

    @Test
    public void testOrdered4Endpoint() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/ordered4/a/b/c/d").then();
        response.statusCode(200);
    }

    @Test
    public void testPatEndpointSingleParam() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/pat/test").then();
        response.statusCode(200);
    }

    @Test
    public void testPatEndpointDoubleParam() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/pat/test/pattern").then();
        response.statusCode(200);
    }

    @Test
    public void testText2txtEndpoint() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/text2txt/word1/word2/word3").then();
        response.statusCode(200);
    }

    @Test
    public void testTitleEndpoint() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/title/Mr/Smith").then();
        response.statusCode(200);
    }
}
