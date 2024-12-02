
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

public class v1_gpt4o_run03_CookieTest {

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
               .then().statusCode(200)
               .body(equalTo("3"));

        given().pathParam("op", "sub")
               .pathParam("arg1", 5)
               .pathParam("arg2", 3)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200)
               .body(equalTo("2"));

        given().pathParam("op", "mul")
               .pathParam("arg1", 3)
               .pathParam("arg2", 4)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200)
               .body(equalTo("12"));

        given().pathParam("op", "div")
               .pathParam("arg1", 10)
               .pathParam("arg2", 2)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200)
               .body(equalTo("5"));

        given().pathParam("op", "div")
               .pathParam("arg1", 10)
               .pathParam("arg2", 0)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(400);
    }

    @Test
    public void testCookieEndpoint() {
        given().pathParam("name", "userid")
               .pathParam("val", "user1234")
               .pathParam("site", "example.com")
               .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
               .then().statusCode(200)
               .body(equalTo("1"));

        given().pathParam("name", "session")
               .pathParam("val", "am")
               .pathParam("site", "abc.com")
               .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
               .then().statusCode(200)
               .body(equalTo("1"));

        given().pathParam("name", "session")
               .pathParam("val", "pm")
               .pathParam("site", "xyz.com")
               .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
               .then().statusCode(200)
               .body(equalTo("2"));

        given().pathParam("name", "other")
               .pathParam("val", "something")
               .pathParam("site", "any.com")
               .when().get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
               .then().statusCode(200)
               .body(equalTo("0"));
    }

    @Test
    public void testCostfunsEndpoint() {
        given().pathParam("i", 1)
               .pathParam("s", "example")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(200)
               .body(equalTo("expected_response"));
        
        given().pathParam("i", 999)
               .pathParam("s", "text")
               .when().get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
               .then().statusCode(404);
    }

    @Test
    public void testDateParseEndpoint() {
        given().pathParam("dayname", "Monday")
               .pathParam("monthname", "January")
               .when().get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
               .then().statusCode(200)
               .body(equalTo("expected_response"));
    }

    @Test
    public void testFileSuffixEndpoint() {
        given().pathParam("directory", "docs")
               .pathParam("file", "file.txt")
               .when().get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
               .then().statusCode(200)
               .body(equalTo("txt"));
    }

    @Test
    public void testNotyPevarEndpoint() {
        given().pathParam("i", 123)
               .pathParam("s", "example")
               .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
               .then().statusCode(200)
               .body(equalTo("expected_response"));
    }

    @Test
    public void testOrdered4Endpoint() {
        given().pathParam("w", "a")
               .pathParam("x", "b")
               .pathParam("y", "c")
               .pathParam("z", "d")
               .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
               .then().statusCode(200)
               .body(equalTo("abcd"));
    }

    @Test
    public void testRegexEndpoint() {
        given().pathParam("txt", "hello123")
               .when().get(baseUrlOfSut + "/api/pat/{txt}")
               .then().statusCode(200)
               .body(equalTo("expected_response"));
    }

    @Test
    public void testPatEndpoint() {
        given().pathParam("txt", "hello123")
               .pathParam("pat", "regex")
               .when().get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
               .then().statusCode(200)
               .body(equalTo("expected_response"));
    }

    @Test
    public void testText2txtEndpoint() {
        given().pathParam("word1", "one")
               .pathParam("word2", "two")
               .pathParam("word3", "three")
               .when().get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
               .then().statusCode(200)
               .body(equalTo("onetwothree"));
    }

    @Test
    public void testTitleEndpoint() {
        given().pathParam("sex", "male")
               .pathParam("title", "mr")
               .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
               .then().statusCode(200)
               .body(equalTo("Mr."));

        given().pathParam("sex", "female")
               .pathParam("title", "ms")
               .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
               .then().statusCode(200)
               .body(equalTo("Ms."));
    }
}
