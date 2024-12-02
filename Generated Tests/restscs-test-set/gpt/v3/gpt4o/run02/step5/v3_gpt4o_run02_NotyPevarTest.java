
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

public class v3_gpt4o_run02_NotyPevarTest {

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
    public void testCalcOperation() {
        given().pathParam("op", "add")
               .pathParam("arg1", 1.0)
               .pathParam("arg2", 2.0)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200)
               .body(equalTo("3.0"));
    }

    @Test
    public void testCalcOperationInvalidOp() {
        given().pathParam("op", "invalid")
               .pathParam("arg1", 1.0)
               .pathParam("arg2", 2.0)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(400);
    }

    @Test
    public void testNotyPevar() {
        given().pathParam("i", 7)
               .pathParam("s", "test")
               .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
               .then().statusCode(200)
               .body(equalTo("3"));
    }

    @Test
    public void testNotyPevarInvalidInput() {
        given().pathParam("i", Integer.MAX_VALUE)
               .pathParam("s", "test")
               .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
               .then().statusCode(500);
    }

    @Test
    public void testCostfuns() {
        given().pathParam("i", 56)
               .pathParam("s", "test")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200)
               .body(equalTo("56"));
    }

    @Test
    public void testCostfunsInvalidInput() {
        given().pathParam("i", "invalid")
               .pathParam("s", "test")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(400);
    }

    @Test
    public void testCookie() {
        given().pathParam("name", "session")
               .pathParam("val", "12345")
               .pathParam("site", "example.com")
               .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
               .then().statusCode(200)
               .body(equalTo("session=12345; Domain=example.com"));
    }

    @Test
    public void testCookieInvalidInput() {
        given().pathParam("name", "")
               .pathParam("val", "")
               .pathParam("site", "")
               .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
               .then().statusCode(400);
    }

    @Test
    public void testDateParse() {
        given().pathParam("dayname", "Monday")
               .pathParam("monthname", "January")
               .when().get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
               .then().statusCode(200)
               .body(equalTo("Parsed: Monday, January"));
    }

    @Test
    public void testDateParseInvalidInput() {
        given().pathParam("dayname", "InvalidDay")
               .pathParam("monthname", "InvalidMonth")
               .when().get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
               .then().statusCode(400);
    }

    @Test
    public void testFileSuffix() {
        given().pathParam("directory", "usr")
               .pathParam("file", "file.txt")
               .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
               .then().statusCode(200)
               .body(equalTo(".txt"));
    }

    @Test
    public void testFileSuffixInvalidInput() {
        given().pathParam("directory", "")
               .pathParam("file", "")
               .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
               .then().statusCode(400);
    }

    @Test
    public void testOrdered4() {
        given().pathParam("w", "a")
               .pathParam("x", "b")
               .pathParam("y", "c")
               .pathParam("z", "d")
               .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
               .then().statusCode(200)
               .body(equalTo("abcd"));
    }

    @Test
    public void testOrdered4InvalidInput() {
        given().pathParam("w", "")
               .pathParam("x", "")
               .pathParam("y", "")
               .pathParam("z", "")
               .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
               .then().statusCode(400);
    }

    @Test
    public void testRegex() {
        given().pathParam("txt", "abc123")
               .when().get(baseUrlOfSut + "/api/pat/{txt}")
               .then().statusCode(200)
               .body(equalTo("Matched: abc123"));
    }

    @Test
    public void testRegexInvalidInput() {
        given().pathParam("txt", "")
               .when().get(baseUrlOfSut + "/api/pat/{txt}")
               .then().statusCode(400);
    }

    @Test
    public void testPat() {
        given().pathParam("txt", "abc123")
               .pathParam("pat", "\\\\d+")
               .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
               .then().statusCode(200)
               .body(equalTo("Matched pattern: \\d+ in abc123"));
    }

    @Test
    public void testPatInvalidInput() {
        given().pathParam("txt", "abc123")
               .pathParam("pat", "")
               .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
               .then().statusCode(400);
    }

    @Test
    public void testText2txt() {
        given().pathParam("word1", "hello")
               .pathParam("word2", "world")
               .pathParam("word3", "test")
               .when().get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
               .then().statusCode(200)
               .body(equalTo("hello world test"));
    }

    @Test
    public void testText2txtInvalidInput() {
        given().pathParam("word1", "")
               .pathParam("word2", "")
               .pathParam("word3", "")
               .when().get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
               .then().statusCode(400);
    }

    @Test
    public void testTitle() {
        given().pathParam("sex", "Mr")
               .pathParam("title", "Smith")
               .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
               .then().statusCode(200)
               .body(equalTo("Mr Smith"));
    }

    @Test
    public void testTitleInvalidInput() {
        given().pathParam("sex", "")
               .pathParam("title", "")
               .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
               .then().statusCode(400);
    }
}
