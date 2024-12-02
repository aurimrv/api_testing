
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

public class v3_gpt4o_run01_CookieTest {

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
    public void testCookieAPI() {
        // Test case for userid with valid and invalid values
        given().get(baseUrlOfSut + "/api/cookie/userid/user1234/site").then().statusCode(200).body(equalTo("1"));
        given().get(baseUrlOfSut + "/api/cookie/userid/short/site").then().statusCode(200).body(equalTo("0"));

        // Test case for session with site abc.com
        given().get(baseUrlOfSut + "/api/cookie/session/am/abc.com").then().statusCode(200).body(equalTo("1"));
        given().get(baseUrlOfSut + "/api/cookie/session/am/xyz.com").then().statusCode(200).body(equalTo("2"));
        given().get(baseUrlOfSut + "/api/cookie/session/pm/abc.com").then().statusCode(200).body(equalTo("2"));

        // Test 404 Not Found for invalid paths
        given().get(baseUrlOfSut + "/api/cookie/invalid/name/site").then().statusCode(404);
    }

    @Test
    public void testCalcAPI() {
        // Test valid operations
        given().get(baseUrlOfSut + "/api/calc/add/1/2").then().statusCode(200).body(equalTo("3.0"));
        given().get(baseUrlOfSut + "/api/calc/subtract/5/2").then().statusCode(200).body(equalTo("3.0"));

        // Test invalid operations
        given().get(baseUrlOfSut + "/api/calc/divide/1/0").then().statusCode(500);
        given().get(baseUrlOfSut + "/api/calc/unknown/1/2").then().statusCode(404);
    }

    @Test
    public void testCostfunsAPI() {
        // Test valid inputs
        given().get(baseUrlOfSut + "/api/costfuns/1/abc").then().statusCode(200).body(equalTo("someExpectedResult"));

        // Test not found for invalid paths
        given().get(baseUrlOfSut + "/api/costfuns/1/").then().statusCode(404);
    }

    @Test
    public void testDateParseAPI() {
        // Test valid date parse
        given().get(baseUrlOfSut + "/api/dateparse/Monday/January").then().statusCode(200).body(equalTo("someExpectedDate"));

        // Test not found for invalid paths
        given().get(baseUrlOfSut + "/api/dateparse/InvalidDay/InvalidMonth").then().statusCode(404);
    }

    @Test
    public void testFileSuffixAPI() {
        // Test valid file suffix
        given().get(baseUrlOfSut + "/api/filesuffix/dir/file.txt").then().statusCode(200).body(equalTo(".txt"));

        // Test not found for invalid paths
        given().get(baseUrlOfSut + "/api/filesuffix/dir/file").then().statusCode(404);
    }

    @Test
    public void testNotyPevarAPI() {
        // Test valid input
        given().get(baseUrlOfSut + "/api/notypevar/1/abc").then().statusCode(200).body(equalTo("someExpectedResult"));

        // Test not found for invalid paths
        given().get(baseUrlOfSut + "/api/notypevar/1/").then().statusCode(404);
    }

    @Test
    public void testOrdered4API() {
        // Test valid input
        given().get(baseUrlOfSut + "/api/ordered4/a/b/c/d").then().statusCode(200).body(equalTo("someExpectedResult"));

        // Test not found for invalid paths
        given().get(baseUrlOfSut + "/api/ordered4/a/").then().statusCode(404);
    }

    @Test
    public void testPatternAPI() {
        // Test valid pattern
        given().get(baseUrlOfSut + "/api/pat/abc/def").then().statusCode(200).body(equalTo("someExpectedResult"));

        // Test not found for invalid paths
        given().get(baseUrlOfSut + "/api/pat/abc").then().statusCode(404);
    }

    @Test
    public void testText2txtAPI() {
        // Test valid input
        given().get(baseUrlOfSut + "/api/text2txt/a/b/c").then().statusCode(200).body(equalTo("someExpectedResult"));

        // Test not found for invalid paths
        given().get(baseUrlOfSut + "/api/text2txt/a/b").then().statusCode(404);
    }

    @Test
    public void testTitleAPI() {
        // Test valid input
        given().get(baseUrlOfSut + "/api/title/mr/smith").then().statusCode(200).body(equalTo("someExpectedResult"));

        // Test not found for invalid paths
        given().get(baseUrlOfSut + "/api/title/mr/").then().statusCode(404);
    }
}
