
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

public class v0_gpt4o_run02_PatTest {
    
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
    public void testPatEndpoint() {
        // Test for coverage of all possible responses from /api/pat/{txt}/{pat}
        String txt = "abcde";
        String pat = "abc";
        
        // Test case: pattern exists in text
        given().pathParam("txt", txt).pathParam("pat", pat)
            .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then().statusCode(200)
            .body(equalTo("1"));

        // Test case: reverse pattern exists in text
        pat = "cba";
        given().pathParam("txt", txt).pathParam("pat", pat)
            .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then().statusCode(200)
            .body(equalTo("2"));

        // Test case: non-existent pattern
        pat = "xyz";
        given().pathParam("txt", txt).pathParam("pat", pat)
            .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then().statusCode(200)
            .body(equalTo("0"));

        // Test case: palindrome pattern
        txt = "abcba";
        pat = "abc";
        given().pathParam("txt", txt).pathParam("pat", pat)
            .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then().statusCode(200)
            .body(equalTo("4"));
    }

    @Test
    public void testCalcEndpoint() {
        // Testing /api/calc/{op}/{arg1}/{arg2}
        given().pathParam("op", "add").pathParam("arg1", 1).pathParam("arg2", 2)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200);

        given().pathParam("op", "subtract").pathParam("arg1", 5).pathParam("arg2", 3)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200);

        given().pathParam("op", "multiply").pathParam("arg1", 2).pathParam("arg2", 3)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200);

        given().pathParam("op", "divide").pathParam("arg1", 6).pathParam("arg2", 3)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200);

        // Testing invalid operation
        given().pathParam("op", "invalidOp").pathParam("arg1", 1).pathParam("arg2", 2)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(404);
    }

    @Test
    public void testCookieEndpoint() {
        // Testing /api/cookie/{name}/{val}/{site}
        given().pathParam("name", "testName").pathParam("val", "testVal").pathParam("site", "testSite")
            .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then().statusCode(200);

        // Testing non-existent cookie
        given().pathParam("name", "nonExistName").pathParam("val", "nonExistVal").pathParam("site", "nonExistSite")
            .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then().statusCode(404);
    }

    @Test
    public void testDateParseEndpoint() {
        // Testing /api/dateparse/{dayname}/{monthname}
        given().pathParam("dayname", "Monday").pathParam("monthname", "January")
            .when().get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then().statusCode(200);

        // Testing invalid date components
        given().pathParam("dayname", "InvalidDay").pathParam("monthname", "InvalidMonth")
            .when().get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then().statusCode(404);
    }

    @Test
    public void testFileSuffixEndpoint() {
        // Testing /api/filesuffix/{directory}/{file}
        given().pathParam("directory", "testDir").pathParam("file", "testFile.txt")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(200);

        // Testing non-existent file
        given().pathParam("directory", "nonExistDir").pathParam("file", "nonExistFile.txt")
            .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then().statusCode(404);
    }

    @Test
    public void testNotyPevarEndpoint() {
        // Testing /api/notypevar/{i}/{s}
        given().pathParam("i", 1).pathParam("s", "test")
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(200);

        // Testing invalid path variables
        given().pathParam("i", -1).pathParam("s", "invalid")
            .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then().statusCode(404);
    }

    @Test
    public void testOrdered4Endpoint() {
        // Testing /api/ordered4/{w}/{x}/{z}/{y}
        given().pathParam("w", "a").pathParam("x", "b").pathParam("z", "c").pathParam("y", "d")
            .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200);

        // Testing invalid parameters
        given().pathParam("w", "invalidW").pathParam("x", "invalidX").pathParam("z", "invalidZ").pathParam("y", "invalidY")
            .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(404);
    }

    @Test
    public void testText2TxtEndpoint() {
        // Testing /api/text2txt/{word1}/{word2}/{word3}
        given().pathParam("word1", "hello").pathParam("word2", "world").pathParam("word3", "test")
            .when().get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200);

        // Testing invalid parameters
        given().pathParam("word1", "invalid1").pathParam("word2", "invalid2").pathParam("word3", "invalid3")
            .when().get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(404);
    }

    @Test
    public void testTitleEndpoint() {
        // Testing /api/title/{sex}/{title}
        given().pathParam("sex", "male").pathParam("title", "Mr")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200);

        // Testing invalid parameters
        given().pathParam("sex", "invalidSex").pathParam("title", "invalidTitle")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(404);
    }
}
