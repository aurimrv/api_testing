
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

public class v3_gpt4o_run03_NotyPevarTest {

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
    public void testCalc() {
        given().get(baseUrlOfSut + "/api/calc/add/1/1").then().statusCode(200).body(equalTo("2.0"));
        given().get(baseUrlOfSut + "/api/calc/subtract/1/1").then().statusCode(200).body(equalTo("0.0"));
        given().get(baseUrlOfSut + "/api/calc/multiply/2/3").then().statusCode(200).body(equalTo("6.0"));
        given().get(baseUrlOfSut + "/api/calc/divide/6/3").then().statusCode(200).body(equalTo("2.0"));
        given().get(baseUrlOfSut + "/api/calc/divide/1/0").then().statusCode(500);
    }

    @Test
    public void testCookie() {
        given().get(baseUrlOfSut + "/api/cookie/testName/testVal/testSite").then().statusCode(200).body(equalTo("Cookie set"));
        given().get(baseUrlOfSut + "/api/cookie//testVal/testSite").then().statusCode(404);
    }

    @Test
    public void testCostfuns() {
        given().get(baseUrlOfSut + "/api/costfuns/28/hello").then().statusCode(200).body(equalTo("2"));
        given().get(baseUrlOfSut + "/api/costfuns/28/hi").then().statusCode(200).body(equalTo("0"));
        given().get(baseUrlOfSut + "/api/costfuns/56/hello").then().statusCode(200).body(equalTo("56"));
        given().get(baseUrlOfSut + "/api/costfuns/7/hello").then().statusCode(200).body(equalTo("1"));
        given().get(baseUrlOfSut + "/api/costfuns/7/h").then().statusCode(500);
    }

    @Test
    public void testDateParse() {
        given().get(baseUrlOfSut + "/api/dateparse/Monday/January").then().statusCode(200).body(equalTo("2023-01-02"));
        given().get(baseUrlOfSut + "/api/dateparse//January").then().statusCode(404);
    }

    @Test
    public void testFileSuffix() {
        given().get(baseUrlOfSut + "/api/filesuffix/testDir/testFile.txt").then().statusCode(200).body(equalTo(".txt"));
        given().get(baseUrlOfSut + "/api/filesuffix/testDir/").then().statusCode(404);
    }

    @Test
    public void testNotyPevar() {
        given().get(baseUrlOfSut + "/api/notypevar/28/hello").then().statusCode(200).body(equalTo("2"));
        given().get(baseUrlOfSut + "/api/notypevar/28/hi").then().statusCode(200).body(equalTo("0"));
        given().get(baseUrlOfSut + "/api/notypevar/56/hello").then().statusCode(200).body(equalTo("56"));
        given().get(baseUrlOfSut + "/api/notypevar/7/hello").then().statusCode(200).body(equalTo("1"));
        given().get(baseUrlOfSut + "/api/notypevar/7/h").then().statusCode(500);
    }

    @Test
    public void testOrdered4() {
        given().get(baseUrlOfSut + "/api/ordered4/a/b/c/d").then().statusCode(200).body(equalTo("abcd"));
        given().get(baseUrlOfSut + "/api/ordered4///").then().statusCode(404);
    }

    @Test
    public void testRegex() {
        given().get(baseUrlOfSut + "/api/pat/testTxt").then().statusCode(200).body(equalTo("Pattern matched"));
        given().get(baseUrlOfSut + "/api/pat/").then().statusCode(404);
    }

    @Test
    public void testPat() {
        given().get(baseUrlOfSut + "/api/pat/testTxt/testPat").then().statusCode(200).body(equalTo("Pattern matched"));
        given().get(baseUrlOfSut + "/api/pat//").then().statusCode(404);
    }

    @Test
    public void testText2txt() {
        given().get(baseUrlOfSut + "/api/text2txt/word1/word2/word3").then().statusCode(200).body(equalTo("word1 word2 word3"));
        given().get(baseUrlOfSut + "/api/text2txt//word2/word3").then().statusCode(404);
    }

    @Test
    public void testTitle() {
        given().get(baseUrlOfSut + "/api/title/male/Mr").then().statusCode(200).body(equalTo("Mr"));
        given().get(baseUrlOfSut + "/api/title/female/Mrs").then().statusCode(200).body(equalTo("Mrs"));
        given().get(baseUrlOfSut + "/api/title//Mrs").then().statusCode(404);
    }

}
