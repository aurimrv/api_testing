
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

public class v1_gpt4o_run02_Text2TxtTest {

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
        // Test for calc endpoint with valid parameters
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/calc/add/1/2").then().statusCode(200);
        response.body(equalTo("3.0"));
        
        // Check for different status codes
        given().get(baseUrlOfSut + "/api/calc/invalid/1/2").then().statusCode(404);
    }

    @Test
    public void testCookieEndpoint() {
        // Test for cookie endpoint with valid parameters
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/cookie/testName/testVal/testSite").then().statusCode(200);
        response.body(equalTo("Cookie set: testName=testVal; Domain=testSite"));

        // Check for different status codes
        given().get(baseUrlOfSut + "/api/cookie/testName/testVal").then().statusCode(404);
    }

    @Test
    public void testCostfunsEndpoint() {
        // Test for costfuns endpoint with valid parameters
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/costfuns/1/testString").then().statusCode(200);
        response.body(equalTo("Calculated cost function for input: 1 and testString"));

        // Check for different status codes
        given().get(baseUrlOfSut + "/api/costfuns/abc/testString").then().statusCode(404);
    }

    @Test
    public void testDateParseEndpoint() {
        // Test for dateParse endpoint with valid parameters
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/dateparse/Monday/January").then().statusCode(200);
        response.body(equalTo("Parsed date: Monday January"));

        // Check for different status codes
        given().get(baseUrlOfSut + "/api/dateparse/Monday").then().statusCode(404);
    }

    @Test
    public void testFileSuffixEndpoint() {
        // Test for fileSuffix endpoint with valid parameters
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/filesuffix/folder/file.txt").then().statusCode(200);
        response.body(equalTo("File suffix for file.txt in folder"));

        // Check for different status codes
        given().get(baseUrlOfSut + "/api/filesuffix/folder").then().statusCode(404);
    }

    @Test
    public void testNoTypeVarEndpoint() {
        // Test for notyPevar endpoint with valid parameters
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/notypevar/1/testString").then().statusCode(200);
        response.body(equalTo("Processed no type variable for input: 1 and testString"));

        // Check for different status codes
        given().get(baseUrlOfSut + "/api/notypevar/abc/testString").then().statusCode(404);
    }

    @Test
    public void testOrdered4Endpoint() {
        // Test for ordered4 endpoint with valid parameters
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/ordered4/a/b/c/d").then().statusCode(200);
        response.body(equalTo("Ordered parameters: a, b, c, d"));

        // Check for different status codes
        given().get(baseUrlOfSut + "/api/ordered4/a/b/c").then().statusCode(404);
    }

    @Test
    public void testRegexEndpoint() {
        // Test for regex endpoint with valid parameters
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/pat/testString").then().statusCode(200);
        response.body(equalTo("Regex matched for input: testString"));

        // Check for different status codes
        given().get(baseUrlOfSut + "/api/pat").then().statusCode(404);
    }

    @Test
    public void testPatEndpoint() {
        // Test for pat endpoint with valid parameters
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/pat/testString/testPattern").then().statusCode(200);
        response.body(equalTo("Pattern matched for input: testString and pattern: testPattern"));

        // Check for different status codes
        given().get(baseUrlOfSut + "/api/pat/testString").then().statusCode(404);
    }

    @Test
    public void testText2TxtEndpoint() {
        // Test for text2txt endpoint with valid parameters
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/text2txt/two/you/and").then().statusCode(200);
        response.body(equalTo("2 u n"));

        // Check for different status codes
        given().get(baseUrlOfSut + "/api/text2txt/two/you").then().statusCode(404);
    }

    @Test
    public void testTitleEndpoint() {
        // Test for title endpoint with valid parameters
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/title/mr/john").then().statusCode(200);
        response.body(equalTo("Title: mr john"));

        // Check for different status codes
        given().get(baseUrlOfSut + "/api/title/mr").then().statusCode(404);
    }
}
