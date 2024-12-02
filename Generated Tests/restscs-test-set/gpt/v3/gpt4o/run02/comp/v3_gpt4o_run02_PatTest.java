
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

public class v3_gpt4o_run02_PatTest {

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
        given().get(baseUrlOfSut + "/api/calc/add/1/2")
            .then().statusCode(200).body(equalTo("3.0"));
        given().get(baseUrlOfSut + "/api/calc/subtract/5/3")
            .then().statusCode(200).body(equalTo("2.0"));
        given().get(baseUrlOfSut + "/api/calc/multiply/2/3")
            .then().statusCode(200).body(equalTo("6.0"));
        given().get(baseUrlOfSut + "/api/calc/divide/6/3")
            .then().statusCode(200).body(equalTo("2.0"));

        // Error cases
        given().get(baseUrlOfSut + "/api/calc/divide/1/0")
            .then().statusCode(500);
        given().get(baseUrlOfSut + "/api/calc/unknown/1/1")
            .then().statusCode(500);
    }

    @Test
    public void testCookieEndpoint() {
        given().get(baseUrlOfSut + "/api/cookie/testname/testval/testsite")
            .then().statusCode(200).body(equalTo("Cookie set"));

        // Error cases
        given().get(baseUrlOfSut + "/api/cookie")
            .then().statusCode(404);
    }

    @Test
    public void testCostfunsEndpoint() {
        given().get(baseUrlOfSut + "/api/costfuns/1/test")
            .then().statusCode(200).body(equalTo("Cost function result"));

        // Error cases
        given().get(baseUrlOfSut + "/api/costfuns/notanumber/test")
            .then().statusCode(500);
    }

    @Test
    public void testDateParseEndpoint() {
        given().get(baseUrlOfSut + "/api/dateparse/Monday/January")
            .then().statusCode(200).body(equalTo("Parsed date"));

        // Error cases
        given().get(baseUrlOfSut + "/api/dateparse/NotADay/NotAMonth")
            .then().statusCode(500);
    }

    @Test
    public void testFileSuffixEndpoint() {
        given().get(baseUrlOfSut + "/api/filesuffix/testdir/testfile")
            .then().statusCode(200).body(equalTo("File suffix result"));

        // Error cases
        given().get(baseUrlOfSut + "/api/filesuffix")
            .then().statusCode(404);
    }

    @Test
    public void testNotyPevarEndpoint() {
        given().get(baseUrlOfSut + "/api/notypevar/1/test")
            .then().statusCode(200).body(equalTo("Notypevar result"));

        // Error cases
        given().get(baseUrlOfSut + "/api/notypevar/notanumber/test")
            .then().statusCode(500);
    }

    @Test
    public void testOrdered4Endpoint() {
        given().get(baseUrlOfSut + "/api/ordered4/w/x/y/z")
            .then().statusCode(200).body(equalTo("Ordered4 result"));

        // Error cases
        given().get(baseUrlOfSut + "/api/ordered4")
            .then().statusCode(404);
    }

    @Test
    public void testRegexEndpoint() {
        given().get(baseUrlOfSut + "/api/pat/test")
            .then().statusCode(200).body(equalTo("Regex result"));

        // Error cases
        given().get(baseUrlOfSut + "/api/pat")
            .then().statusCode(404);
    }

    @Test
    public void testPatEndpoint() {
        ValidatableResponse response;

        // Valid cases
        response = given().get(baseUrlOfSut + "/api/pat/hello/ll")
            .then().statusCode(200);
        assertEquals("1", response.extract().body().asString());

        response = given().get(baseUrlOfSut + "/api/pat/hello/ol")
            .then().statusCode(200);
        assertEquals("2", response.extract().body().asString());

        response = given().get(baseUrlOfSut + "/api/pat/helloolleh/hello")
            .then().statusCode(200);
        assertEquals("4", response.extract().body().asString());

        // Error cases
        given().get(baseUrlOfSut + "/api/pat")
            .then().statusCode(404);
    }

    @Test
    public void testText2txtEndpoint() {
        given().get(baseUrlOfSut + "/api/text2txt/word1/word2/word3")
            .then().statusCode(200).body(equalTo("Text2txt result"));

        // Error cases
        given().get(baseUrlOfSut + "/api/text2txt")
            .then().statusCode(404);
    }

    @Test
    public void testTitleEndpoint() {
        given().get(baseUrlOfSut + "/api/title/mr/john")
            .then().statusCode(200).body(equalTo("Title result"));

        // Error cases
        given().get(baseUrlOfSut + "/api/title")
            .then().statusCode(404);
    }
}
