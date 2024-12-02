
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

public class v1_gpt4o_run03_Ordered4Test {

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
    public void testCalcUsingGET() {
        given().pathParam("op", "add")
               .pathParam("arg1", 1.0)
               .pathParam("arg2", 2.0)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200)
               .body(equalTo("3.0"));

        given().pathParam("op", "sub")
               .pathParam("arg1", 5.0)
               .pathParam("arg2", 3.0)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200)
               .body(equalTo("2.0"));

        given().pathParam("op", "div")
               .pathParam("arg1", 10.0)
               .pathParam("arg2", 0.0)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(400);
    }

    @Test
    public void testCookieUsingGET() {
        given().pathParam("name", "testCookie")
               .pathParam("val", "cookieValue")
               .pathParam("site", "localhost")
               .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
               .then().statusCode(200)
               .body(equalTo("testCookie=cookieValue"));
    }

    @Test
    public void testCostfunsUsingGET() {
        given().pathParam("i", 1)
               .pathParam("s", "test")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200)
               .body(equalTo("cost=1,test"));
    }

    @Test
    public void testDateParseUsingGET() {
        given().pathParam("dayname", "Monday")
               .pathParam("monthname", "January")
               .when().get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
               .then().statusCode(200)
               .body(equalTo("Monday, January"));
    }

    @Test
    public void testFileSuffixUsingGET() {
        given().pathParam("directory", "docs")
               .pathParam("file", "file.txt")
               .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
               .then().statusCode(200)
               .body(equalTo("docs/file.txt"));
    }

    @Test
    public void testNotyPevarUsingGET() {
        given().pathParam("i", 42)
               .pathParam("s", "testString")
               .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
               .then().statusCode(200)
               .body(equalTo("42,testString"));
    }

    @Test
    public void testOrdered4UsingGET() {
        given().pathParam("w", "apple")
               .pathParam("x", "banana")
               .pathParam("y", "cherry")
               .pathParam("z", "date")
               .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
               .then().statusCode(200)
               .body(equalTo("unordered"));

        given().pathParam("w", "apple")
               .pathParam("x", "banana")
               .pathParam("y", "cherry")
               .pathParam("z", "date")
               .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
               .then().statusCode(200)
               .body(equalTo("increasing"));

        given().pathParam("w", "date")
               .pathParam("x", "cherry")
               .pathParam("y", "banana")
               .pathParam("z", "apple")
               .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
               .then().statusCode(200)
               .body(equalTo("decreasing"));
    }

    @Test
    public void testRegexUsingGET() {
        given().pathParam("txt", "abcdefg")
               .when().get(baseUrlOfSut + "/api/pat/{txt}")
               .then().statusCode(200)
               .body(equalTo("matched"));
    }

    @Test
    public void testPatUsingGET() {
        given().pathParam("txt", "abcdefg")
               .pathParam("pat", "abc")
               .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
               .then().statusCode(200)
               .body(equalTo("matched"));
    }

    @Test
    public void testText2txtUsingGET() {
        given().pathParam("word1", "hello")
               .pathParam("word2", "world")
               .pathParam("word3", "java")
               .when().get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
               .then().statusCode(200)
               .body(equalTo("hello world java"));
    }

    @Test
    public void testTitleUsingGET() {
        given().pathParam("sex", "male")
               .pathParam("title", "Mr")
               .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
               .then().statusCode(200)
               .body(equalTo("Mr male"));
    }
}
