
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
import io.restassured.response.Response;
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

public class v0_gpt4o_run02_RegexTest {

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
    public void testRegexUrl() {
        Response response = given().get(baseUrlOfSut + "/api/pat/http://example.com");
        response.then().statusCode(200).body(equalTo("url"));
    }

    @Test
    public void testRegexDate() {
        Response response = given().get(baseUrlOfSut + "/api/pat/mon01jan");
        response.then().statusCode(200).body(equalTo("date"));
    }

    @Test
    public void testRegexFpe() {
        Response response = given().get(baseUrlOfSut + "/api/pat/12.34e+56");
        response.then().statusCode(200).body(equalTo("fpe"));
    }

    @Test
    public void testRegexNone() {
        Response response = given().get(baseUrlOfSut + "/api/pat/randomtext");
        response.then().statusCode(200).body(equalTo("none"));
    }

    @Test
    public void testCalc() {
        Response response = given().get(baseUrlOfSut + "/api/calc/add/1.0/2.0");
        response.then().statusCode(200).body(equalTo("3.0"));

        response = given().get(baseUrlOfSut + "/api/calc/subtract/5.0/3.0");
        response.then().statusCode(200).body(equalTo("2.0"));

        response = given().get(baseUrlOfSut + "/api/calc/multiply/3.0/4.0");
        response.then().statusCode(200).body(equalTo("12.0"));

        response = given().get(baseUrlOfSut + "/api/calc/divide/10.0/2.0");
        response.then().statusCode(200).body(equalTo("5.0"));
    }

    @Test
    public void testCookie() {
        Response response = given().get(baseUrlOfSut + "/api/cookie/session/1234/www.example.com");
        response.then().statusCode(200).body(equalTo("Cookie set"));

        response = given().get(baseUrlOfSut + "/api/cookie/session/1234/www.unauthorized.com");
        response.then().statusCode(401);
    }

    @Test
    public void testCostfuns() {
        Response response = given().get(baseUrlOfSut + "/api/costfuns/1/s");
        response.then().statusCode(200).body(equalTo("Cost function 1"));

        response = given().get(baseUrlOfSut + "/api/costfuns/99/s");
        response.then().statusCode(404);
    }

    @Test
    public void testDateParse() {
        Response response = given().get(baseUrlOfSut + "/api/dateparse/mon/jan");
        response.then().statusCode(200).body(equalTo("Parsed date"));

        response = given().get(baseUrlOfSut + "/api/dateparse/invalid/mon");
        response.then().statusCode(404);
    }

    @Test
    public void testFileSuffix() {
        Response response = given().get(baseUrlOfSut + "/api/filesuffix/home/user/file.txt");
        response.then().statusCode(200).body(equalTo("txt"));

        response = given().get(baseUrlOfSut + "/api/filesuffix/home/user/file");
        response.then().statusCode(404);
    }

    @Test
    public void testNotyPevar() {
        Response response = given().get(baseUrlOfSut + "/api/notypevar/1/abc");
        response.then().statusCode(200).body(equalTo("Variable notyPevar processed"));

        response = given().get(baseUrlOfSut + "/api/notypevar/999/abc");
        response.then().statusCode(404);
    }

    @Test
    public void testOrdered4() {
        Response response = given().get(baseUrlOfSut + "/api/ordered4/a/b/c/d");
        response.then().statusCode(200).body(equalTo("Ordered 4 processed"));

        response = given().get(baseUrlOfSut + "/api/ordered4/a/b/c");
        response.then().statusCode(404);
    }

    @Test
    public void testText2txt() {
        Response response = given().get(baseUrlOfSut + "/api/text2txt/word1/word2/word3");
        response.then().statusCode(200).body(equalTo("Text to text processed"));

        response = given().get(baseUrlOfSut + "/api/text2txt/word1/word2");
        response.then().statusCode(404);
    }

    @Test
    public void testTitle() {
        Response response = given().get(baseUrlOfSut + "/api/title/mr/john");
        response.then().statusCode(200).body(equalTo("Title processed"));

        response = given().get(baseUrlOfSut + "/api/title/mr");
        response.then().statusCode(404);
    }
}
