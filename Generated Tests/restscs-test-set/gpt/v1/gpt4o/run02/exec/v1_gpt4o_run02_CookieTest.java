
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

public class v1_gpt4o_run02_CookieTest {

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
            .when().get("/api/calc/divide/10/2")
            .then().statusCode(200)
            .body(equalTo("5"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/calc/add/1.5/2.5")
            .then().statusCode(200)
            .body(equalTo("4.0"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/calc/unknown/1/2")
            .then().statusCode(404);
    }

    @Test
    public void testCookieEndpoint() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/cookie/userid/user123/abc.com")
            .then().statusCode(200)
            .body(equalTo("1"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/cookie/session/am/abc.com")
            .then().statusCode(200)
            .body(equalTo("1"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/cookie/session/pm/xyz.com")
            .then().statusCode(200)
            .body(equalTo("2"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/cookie/unknown/value/site.com")
            .then().statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testCostfunsEndpoint() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/costfuns/1/test")
            .then().statusCode(200)
            .body(equalTo("10"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/costfuns/0/anotherTest")
            .then().statusCode(200)
            .body(equalTo("10"));
    }

    @Test
    public void testDateParseEndpoint() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/dateparse/monday/january")
            .then().statusCode(200)
            .body(equalTo("0"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/dateparse/friday/december")
            .then().statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testFileSuffixEndpoint() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/filesuffix/dir/file.txt")
            .then().statusCode(200)
            .body(equalTo("0"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/filesuffix/folder/image.png")
            .then().statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testNotyPevarEndpoint() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/notypevar/1/variable")
            .then().statusCode(200)
            .body(equalTo("2"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/notypevar/2/anotherVariable")
            .then().statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testOrdered4Endpoint() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/ordered4/a/b/c/d")
            .then().statusCode(200)
            .body(equalTo("unordered"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/ordered4/x/y/z/w")
            .then().statusCode(200)
            .body(equalTo("unordered"));
    }

    @Test
    public void testPatEndpoint() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/pat/sample")
            .then().statusCode(200)
            .body(equalTo("none"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/pat/anotherSample")
            .then().statusCode(200)
            .body(equalTo("none"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/pat/text/pattern")
            .then().statusCode(200)
            .body(equalTo("matched"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/pat/word/regex")
            .then().statusCode(200)
            .body(equalTo("matched"));
    }

    @Test
    public void testText2txtEndpoint() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/text2txt/one/two/three")
            .then().statusCode(200)
            .body(equalTo(""));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/text2txt/four/five/six")
            .then().statusCode(200)
            .body(equalTo(""));
    }

    @Test
    public void testTitleEndpoint() {
        given().baseUri(baseUrlOfSut)
            .when().get("/api/title/mr/smith")
            .then().statusCode(200)
            .body(equalTo("-1"));

        given().baseUri(baseUrlOfSut)
            .when().get("/api/title/ms/jones")
            .then().statusCode(200)
            .body(equalTo("-1"));
    }
}
