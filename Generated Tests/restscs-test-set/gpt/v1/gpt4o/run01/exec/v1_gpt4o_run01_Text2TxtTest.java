
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

        res = given().baseUri(baseUrlOfSut)
            .when().get("/api/calc/add/0.0/0.0")
            .then().statusCode(200);
        res.body(equalTo("0.0"));

        res = given().baseUri(baseUrlOfSut)
            .when().get("/api/calc/subtract/0.0/0.0")
            .then().statusCode(200);
        res.body(equalTo("0.0"));
    }

    @Test
    public void testCookieEndpoint() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/cookie/session/1234/google.com")
            .then().statusCode(200);
        res.body(equalTo("2"));

        res = given().baseUri(baseUrlOfSut)
            .when().get("/api/cookie/session/5678/google.com")
            .then().statusCode(200);
        res.body(equalTo("2"));
    }

    @Test
    public void testCostfunsEndpoint() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/costfuns/1/test")
            .then().statusCode(200);
        res.body(equalTo("10"));

        res = given().baseUri(baseUrlOfSut)
            .when().get("/api/costfuns/2/test")
            .then().statusCode(200);
        res.body(equalTo("20"));
    }

    @Test
    public void testDateParseEndpoint() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/dateparse/Monday/January")
            .then().statusCode(200);
        res.body(equalTo("0"));

        res = given().baseUri(baseUrlOfSut)
            .when().get("/api/dateparse/Tuesday/February")
            .then().statusCode(200);
        res.body(equalTo("0"));
    }

    @Test
    public void testFileSuffixEndpoint() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/filesuffix/home/test.txt")
            .then().statusCode(200);
        res.body(equalTo("0"));

        res = given().baseUri(baseUrlOfSut)
            .when().get("/api/filesuffix/home/test.jpg")
            .then().statusCode(200);
        res.body(equalTo("0"));
    }

    @Test
    public void testNotyPevarEndpoint() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/notypevar/5/sample")
            .then().statusCode(200);
        res.body(equalTo("2"));

        res = given().baseUri(baseUrlOfSut)
            .when().get("/api/notypevar/10/sample")
            .then().statusCode(200);
        res.body(equalTo("4"));
    }

    @Test
    public void testOrdered4Endpoint() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/ordered4/a/b/c/d")
            .then().statusCode(200);
        res.body(equalTo("unordered"));

        res = given().baseUri(baseUrlOfSut)
            .when().get("/api/ordered4/e/f/g/h")
            .then().statusCode(200);
        res.body(equalTo("unordered"));
    }

    @Test
    public void testRegexEndpoint() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/pat/sample")
            .then().statusCode(200);
        res.body(equalTo("none"));

        res = given().baseUri(baseUrlOfSut)
            .when().get("/api/pat/example")
            .then().statusCode(200);
        res.body(equalTo("none"));
    }

    @Test
    public void testPatEndpoint() throws Exception {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/pat/sample/%5Cd%2B")
            .then().statusCode(200);
        res.body(equalTo("Pattern match"));

        res = given().baseUri(baseUrlOfSut)
            .when().get("/api/pat/example/%5Cd%2B")
            .then().statusCode(200);
        res.body(equalTo("Pattern match"));
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
    }

    @Test
    public void testTitleEndpoint() {
        ValidatableResponse res = given().baseUri(baseUrlOfSut)
            .when().get("/api/title/mr/mrs")
            .then().statusCode(200);
        res.body(equalTo("-1"));

        res = given().baseUri(baseUrlOfSut)
            .when().get("/api/title/ms/mrs")
            .then().statusCode(200);
        res.body(equalTo("-1"));
    }
}
