
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

public class v0_gpt4o_run03_NotyPevarTest {

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
        // Test for i0
        given().baseUri(baseUrlOfSut)
            .when().get("/api/notypevar/28/anyString")
            .then().statusCode(200)
            .body(equalTo("28"));
        
        // Test for i1
        given().baseUri(baseUrlOfSut)
            .when().get("/api/notypevar/7/anyString")
            .then().statusCode(200)
            .body(equalTo("1"));

        // Test for i2
        given().baseUri(baseUrlOfSut)
            .when().get("/api/notypevar/1/zzz")
            .then().statusCode(200)
            .body(equalTo("2"));

        // Test for i3
        given().baseUri(baseUrlOfSut)
            .when().get("/api/notypevar/6/hello")
            .then().statusCode(200)
            .body(equalTo("3"));
        
        // Test default case
        given().baseUri(baseUrlOfSut)
            .when().get("/api/notypevar/1/a")
            .then().statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testNotyPevarErrorResponses() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/notypevar")
            .then().statusCode(404);
        
        given().baseUri(baseUrlOfSut)
            .when().get("/api/notypevar/1")
            .then().statusCode(404);
    }

    @Test
    public void testCalc() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/calc/add/1/2")
            .then().statusCode(200)
            .body(equalTo("3"));
        
        given().baseUri(baseUrlOfSut)
            .when().get("/api/calc/subtract/5/3")
            .then().statusCode(200)
            .body(equalTo("2"));
        
        given().baseUri(baseUrlOfSut)
            .when().get("/api/calc/multiply/2/3")
            .then().statusCode(200)
            .body(equalTo("6"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/calc/divide/6/2")
            .then().statusCode(200)
            .body(equalTo("3"));
    }

    @Test
    public void testCalcErrorResponses() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/calc/add/1")
            .then().statusCode(404);
        
        given().baseUri(baseUrlOfSut)
            .when().get("/api/calc")
            .then().statusCode(404);
    }
    
    @Test
    public void testCookie() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/cookie/testName/testVal/testSite")
            .then().statusCode(200)
            .body(equalTo("OK"));
    }

    @Test
    public void testCookieErrorResponses() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/cookie/testName/testVal")
            .then().statusCode(404);

        given().baseUri(baseUrlOfSut)
            .when().get("/api/cookie")
            .then().statusCode(404);
    }

    @Test
    public void testDateParse() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/dateparse/Monday/January")
            .then().statusCode(200)
            .body(equalTo("OK"));
    }

    @Test
    public void testDateParseErrorResponses() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/dateparse/Monday")
            .then().statusCode(404);

        given().baseUri(baseUrlOfSut)
            .when().get("/api/dateparse")
            .then().statusCode(404);
    }

    @Test
    public void testFileSuffix() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/filesuffix/myDir/myFile.txt")
            .then().statusCode(200)
            .body(equalTo("txt"));
    }

    @Test
    public void testFileSuffixErrorResponses() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/filesuffix/myDir")
            .then().statusCode(404);

        given().baseUri(baseUrlOfSut)
            .when().get("/api/filesuffix")
            .then().statusCode(404);
    }

    @Test
    public void testOrdered4() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/ordered4/a/b/c/d")
            .then().statusCode(200)
            .body(equalTo("abcd"));
    }

    @Test
    public void testOrdered4ErrorResponses() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/ordered4/a/b/c")
            .then().statusCode(404);

        given().baseUri(baseUrlOfSut)
            .when().get("/api/ordered4")
            .then().statusCode(404);
    }

    @Test
    public void testPat() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/pat/testTxt/testPat")
            .then().statusCode(200)
            .body(equalTo("OK"));
    }

    @Test
    public void testPatErrorResponses() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/pat/testTxt")
            .then().statusCode(404);

        given().baseUri(baseUrlOfSut)
            .when().get("/api/pat")
            .then().statusCode(404);
    }

    @Test
    public void testText2Txt() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/text2txt/word1/word2/word3")
            .then().statusCode(200)
            .body(equalTo("OK"));
    }

    @Test
    public void testText2TxtErrorResponses() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/text2txt/word1/word2")
            .then().statusCode(404);

        given().baseUri(baseUrlOfSut)
            .when().get("/api/text2txt")
            .then().statusCode(404);
    }

    @Test
    public void testTitle() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/title/male/Mr")
            .then().statusCode(200)
            .body(equalTo("Mr"));
    }

    @Test
    public void testTitleErrorResponses() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/title/male")
            .then().statusCode(404);

        given().baseUri(baseUrlOfSut)
            .when().get("/api/title")
            .then().statusCode(404);
    }
}
