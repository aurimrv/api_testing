
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

public class v1_gpt4o_run02_CostfunsTest {

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
        given().pathParam("op", "add")
               .pathParam("arg1", 1)
               .pathParam("arg2", 2)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200).body(equalTo("3"));

        given().pathParam("op", "sub")
               .pathParam("arg1", 5)
               .pathParam("arg2", 3)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200).body(equalTo("2"));

        given().pathParam("op", "mul")
               .pathParam("arg1", 3)
               .pathParam("arg2", 4)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200).body(equalTo("12"));

        given().pathParam("op", "div")
               .pathParam("arg1", 8)
               .pathParam("arg2", 4)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200).body(equalTo("2"));
    }

    @Test
    public void testCookieEndpoint() {
        given().pathParam("name", "sessionid")
               .pathParam("val", "12345")
               .pathParam("site", "example.com")
               .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
               .then().statusCode(200).body(equalTo("Cookie set: sessionid=12345; Domain=example.com; Path=/"));
    }

    @Test
    public void testCostfunsEndpoint() {
        given().pathParam("i", 5)
               .pathParam("s", "test")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200).body(equalTo("1"));

        given().pathParam("i", -500)
               .pathParam("s", "test")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200).body(equalTo("2"));

        given().pathParam("i", -333)
               .pathParam("s", "test")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200).body(equalTo("3"));

        given().pathParam("i", 700)
               .pathParam("s", "test")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200).body(equalTo("4"));

        given().pathParam("i", 555)
               .pathParam("s", "test")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200).body(equalTo("5"));

        given().pathParam("i", 0)
               .pathParam("s", "test")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200).body(equalTo("6"));

        given().pathParam("i", 0)
               .pathParam("s", "baab")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200).body(equalTo("7"));

        given().pathParam("i", 0)
               .pathParam("s", "ababba")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200).body(equalTo("8"));

        given().pathParam("i", 0)
               .pathParam("s", "ababba")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200).body(equalTo("9"));

        given().pathParam("i", 0)
               .pathParam("s", "ab")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200).body(equalTo("10"));
    }

    @Test
    public void testDateParseEndpoint() {
        given().pathParam("dayname", "Monday")
               .pathParam("monthname", "January")
               .when().get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
               .then().statusCode(200).body(equalTo("Parsed date: Monday, January"));
    }

    @Test
    public void testFileSuffixEndpoint() {
        given().pathParam("directory", "docs")
               .pathParam("file", "report.pdf")
               .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
               .then().statusCode(200).body(equalTo("File suffix: .pdf"));
    }

    @Test
    public void testNotTypeVarEndpoint() {
        given().pathParam("i", 5)
               .pathParam("s", "test")
               .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
               .then().statusCode(200).body(equalTo("Processed: 5, test"));
    }

    @Test
    public void testOrdered4Endpoint() {
        given().pathParam("w", "word1")
               .pathParam("x", "word2")
               .pathParam("y", "word3")
               .pathParam("z", "word4")
               .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
               .then().statusCode(200).body(equalTo("Ordered: word1, word2, word4, word3"));
    }

    @Test
    public void testRegexEndpoint() {
        given().pathParam("txt", "example")
               .when().get(baseUrlOfSut + "/api/pat/{txt}")
               .then().statusCode(200).body(equalTo("Pattern matched: example"));

        given().pathParam("txt", "example")
               .pathParam("pat", "ex")
               .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
               .then().statusCode(200).body(equalTo("Pattern matched: example with ex"));
    }

    @Test
    public void testText2TxtEndpoint() {
        given().pathParam("word1", "hello")
               .pathParam("word2", "world")
               .pathParam("word3", "java")
               .when().get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
               .then().statusCode(200).body(equalTo("Concatenated: hello, world, java"));
    }

    @Test
    public void testTitleEndpoint() {
        given().pathParam("sex", "Mr")
               .pathParam("title", "Smith")
               .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
               .then().statusCode(200).body(equalTo("Title: Mr Smith"));
    }
}
