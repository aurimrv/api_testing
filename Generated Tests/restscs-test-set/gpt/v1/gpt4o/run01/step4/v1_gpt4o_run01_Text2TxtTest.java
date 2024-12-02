
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

public class v1_gpt4o_run01_Text2TxtTest {
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
    public void testCalcEndpoint() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/calc/add/1.0/2.0")
            .then().statusCode(200);
        res.body(equalTo("3.0"));

        res = given().baseUri(baseUrlOfSut)
            .when().get("/api/calc/subtract/5.0/3.0")
            .then().statusCode(200);
        res.body(equalTo("2.0"));

        // Add more operations and boundary tests
    }

    @Test
    public void testCookieEndpoint() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/cookie/session/1234/google.com")
            .then().statusCode(200);
        res.body(equalTo("Cookie Set"));

        // Add more tests for different cookies and sites
    }

    @Test
    public void testCostfunsEndpoint() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/costfuns/1/test")
            .then().statusCode(200);
        res.body(equalTo("Cost function result"));

        // Add more tests for different integer and string values
    }

    @Test
    public void testDateParseEndpoint() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/dateparse/Monday/January")
            .then().statusCode(200);
        res.body(equalTo("Parsed Date"));

        // Add more tests for different day and month names
    }

    @Test
    public void testFileSuffixEndpoint() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/filesuffix/home/test.txt")
            .then().statusCode(200);
        res.body(equalTo(".txt"));

        // Add more tests for different directories and file names
    }

    @Test
    public void testNotyPevarEndpoint() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/notypevar/5/sample")
            .then().statusCode(200);
        res.body(equalTo("NotyPevar result"));

        // Add more tests for different integer and string values
    }

    @Test
    public void testOrdered4Endpoint() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/ordered4/a/b/c/d")
            .then().statusCode(200);
        res.body(equalTo("Ordered Result"));

        // Add more tests for different string combinations
    }

    @Test
    public void testRegexEndpoint() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/pat/sample")
            .then().statusCode(200);
        res.body(equalTo("Regex match"));

        // Add more tests for different patterns and texts
    }

    @Test
    public void testPatEndpoint() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/pat/sample/\\d+")
            .then().statusCode(200);
        res.body(equalTo("Pattern match"));

        // Add more tests for different patterns and texts
    }

    @Test
    public void testText2TxtEndpoint() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/text2txt/two/see/you")
            .then().statusCode(200);
        res.body(equalTo("2"));

        res = given().baseUri(baseUrlOfSut)
            .when().get("/api/text2txt/for/see/you")
            .then().statusCode(200);
        res.body(equalTo("4"));

        res = given().baseUri(baseUrlOfSut)
            .when().get("/api/text2txt/and/by/the/way")
            .then().statusCode(200);
        res.body(equalTo("n"));

        res = given().baseUri(baseUrlOfSut)
            .when().get("/api/text2txt/by/the/way")
            .then().statusCode(200);
        res.body(equalTo("btw"));

        // Add more tests for different word combinations
    }

    @Test
    public void testTitleEndpoint() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/title/mr/mrs")
            .then().statusCode(200);
        res.body(equalTo("Title result"));

        // Add more tests for different titles and sex
    }
}
