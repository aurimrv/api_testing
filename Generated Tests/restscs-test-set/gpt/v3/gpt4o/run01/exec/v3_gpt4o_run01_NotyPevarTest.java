
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

public class v3_gpt4o_run01_NotyPevarTest {

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
    public void testNotyPevar() {
        // Test valid inputs
        given().baseUri(baseUrlOfSut)
                .when().get("/api/notypevar/56/hello")
                .then().statusCode(200)
                .body(equalTo("3"));

        // Test invalid input causing 404
        given().baseUri(baseUrlOfSut)
                .when().get("/api/notypevar/abc/hello")
                .then().statusCode(404);

        // Test edge case
        given().baseUri(baseUrlOfSut)
                .when().get("/api/notypevar/7/hello")
                .then().statusCode(200)
                .body(equalTo("1"));
        
        // Test business rule enforcement
        given().baseUri(baseUrlOfSut)
                .when().get("/api/notypevar/60/abc")
                .then().statusCode(200)
                .body(equalTo("2"));

        // Test forcing an internal server error
        given().baseUri(baseUrlOfSut)
                .when().get("/api/notypevar/-1/abc")
                .then().statusCode(500);
    }

    @Test
    public void testCalcEndpoint() {
        // Test valid operation
        given().baseUri(baseUrlOfSut)
                .when().get("/api/calc/add/1/2")
                .then().statusCode(200)
                .body(equalTo("0.0"));

        // Test invalid operation causing 404
        given().baseUri(baseUrlOfSut)
                .when().get("/api/calc/unknown/1/2")
                .then().statusCode(404);

        // Test edge case
        given().baseUri(baseUrlOfSut)
                .when().get("/api/calc/sub/0/0")
                .then().statusCode(200)
                .body(equalTo("0"));

        // Test invalid input causing 500
        given().baseUri(baseUrlOfSut)
                .when().get("/api/calc/add/1/abc")
                .then().statusCode(500);
    }

    @Test
    public void testCookieEndpoint() {
        // Test valid input
        given().baseUri(baseUrlOfSut)
                .when().get("/api/cookie/testname/testval/testsite")
                .then().statusCode(200)
                .body(equalTo("0"));

        // Test invalid input causing 404
        given().baseUri(baseUrlOfSut)
                .when().get("/api/cookie/")
                .then().statusCode(404);

        // Test edge case
        given().baseUri(baseUrlOfSut)
                .when().get("/api/cookie/a/b/c")
                .then().statusCode(200)
                .body(equalTo("OK"));

        // Test invalid input causing 500
        given().baseUri(baseUrlOfSut)
                .when().get("/api/cookie/testname/testval/")
                .then().statusCode(500);
    }

    @Test
    public void testDateParseEndpoint() {
        // Test valid input
        given().baseUri(baseUrlOfSut)
                .when().get("/api/dateparse/Monday/January")
                .then().statusCode(200)
                .body(equalTo("0"));

        // Test invalid input causing 404
        given().baseUri(baseUrlOfSut)
                .when().get("/api/dateparse/")
                .then().statusCode(404);

        // Test edge case
        given().baseUri(baseUrlOfSut)
                .when().get("/api/dateparse/Sunday/December")
                .then().statusCode(200)
                .body(equalTo("OK"));

        // Test invalid input causing 500
        given().baseUri(baseUrlOfSut)
                .when().get("/api/dateparse/InvalidDay/InvalidMonth")
                .then().statusCode(500);
    }

    @Test
    public void testFileSuffixEndpoint() {
        // Test valid input
        given().baseUri(baseUrlOfSut)
                .when().get("/api/filesuffix/dir/file.txt")
                .then().statusCode(200)
                .body(equalTo("0"));

        // Test invalid input causing 404
        given().baseUri(baseUrlOfSut)
                .when().get("/api/filesuffix/")
                .then().statusCode(404);

        // Test edge case
        given().baseUri(baseUrlOfSut)
                .when().get("/api/filesuffix/dir/.hiddenfile")
                .then().statusCode(200)
                .body(equalTo(""));

        // Test invalid input causing 500
        given().baseUri(baseUrlOfSut)
                .when().get("/api/filesuffix/dir/")
                .then().statusCode(500);
    }

    @Test
    public void testOrdered4Endpoint() {
        // Test valid input
        given().baseUri(baseUrlOfSut)
                .when().get("/api/ordered4/a/b/c/d")
                .then().statusCode(200)
                .body(equalTo("unordered"));

        // Test invalid input causing 404
        given().baseUri(baseUrlOfSut)
                .when().get("/api/ordered4/")
                .then().statusCode(404);

        // Test edge case
        given().baseUri(baseUrlOfSut)
                .when().get("/api/ordered4/1/2/3/4")
                .then().statusCode(200)
                .body(equalTo("OK"));

        // Test invalid input causing 500
        given().baseUri(baseUrlOfSut)
                .when().get("/api/ordered4/a/b/c/")
                .then().statusCode(500);
    }

    @Test
    public void testRegexEndpoint() {
        // Test valid input
        given().baseUri(baseUrlOfSut)
                .when().get("/api/pat/test")
                .then().statusCode(200)
                .body(equalTo("none"));

        // Test invalid input causing 404
        given().baseUri(baseUrlOfSut)
                .when().get("/api/pat/")
                .then().statusCode(404);

        // Test edge case
        given().baseUri(baseUrlOfSut)
                .when().get("/api/pat/.*")
                .then().statusCode(200)
                .body(equalTo("OK"));

        // Test invalid input causing 500
        given().baseUri(baseUrlOfSut)
                .when().get("/api/pat/test/[")
                .then().statusCode(500);
    }

    @Test
    public void testTitleEndpoint() {
        // Test valid input
        given().baseUri(baseUrlOfSut)
                .when().get("/api/title/mr/doctor")
                .then().statusCode(200)
                .body(equalTo("-1"));

        // Test invalid input causing 404
        given().baseUri(baseUrlOfSut)
                .when().get("/api/title/")
                .then().statusCode(404);

        // Test edge case
        given().baseUri(baseUrlOfSut)
                .when().get("/api/title/mrs/ms")
                .then().statusCode(200)
                .body(equalTo("OK"));

        // Test invalid input causing 500
        given().baseUri(baseUrlOfSut)
                .when().get("/api/title/mr/")
                .then().statusCode(500);
    }
}
