
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

public class v1_gpt4o_run02_DateParseTest {

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
            .pathParam("op", "add")
            .pathParam("arg1", 1)
            .pathParam("arg2", 2)
            .when().get("/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200)
            .body(equalTo("3"));

        given().baseUri(baseUrlOfSut)
            .pathParam("op", "subtract")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
            .when().get("/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200)
            .body(equalTo("2"));

        given().baseUri(baseUrlOfSut)
            .pathParam("op", "multiply")
            .pathParam("arg1", 2)
            .pathParam("arg2", 3)
            .when().get("/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200)
            .body(equalTo("6"));

        given().baseUri(baseUrlOfSut)
            .pathParam("op", "divide")
            .pathParam("arg1", 6)
            .pathParam("arg2", 2)
            .when().get("/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200)
            .body(equalTo("3"));
    }

    @Test
    public void testCookieEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("name", "sessionId")
            .pathParam("val", "12345")
            .pathParam("site", "example.com")
            .when().get("/api/cookie/{name}/{val}/{site}")
            .then().statusCode(200)
            .body(equalTo("sessionId=12345; Domain=example.com"));
    }

    @Test
    public void testCostfunsEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("i", 5)
            .pathParam("s", "example")
            .when().get("/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body(containsString("example"));
    }

    @Test
    public void testDateParseEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("dayname", "Mon")
            .pathParam("monthname", "Jan")
            .when().get("/api/dateparse/{dayname}/{monthname}")
            .then().statusCode(200)
            .body(equalTo("2"));

        given().baseUri(baseUrlOfSut)
            .pathParam("dayname", "Tue")
            .pathParam("monthname", "Feb")
            .when().get("/api/dateparse/{dayname}/{monthname}")
            .then().statusCode(200)
            .body(equalTo("3"));

        given().baseUri(baseUrlOfSut)
            .pathParam("dayname", "Wed")
            .pathParam("monthname", "Mar")
            .when().get("/api/dateparse/{dayname}/{monthname}")
            .then().statusCode(200)
            .body(equalTo("4"));

        given().baseUri(baseUrlOfSut)
            .pathParam("dayname", "Fri")
            .pathParam("monthname", "Dec")
            .when().get("/api/dateparse/{dayname}/{monthname}")
            .then().statusCode(200)
            .body(equalTo("13"));

        given().baseUri(baseUrlOfSut)
            .pathParam("dayname", "Invalid")
            .pathParam("monthname", "Invalid")
            .when().get("/api/dateparse/{dayname}/{monthname}")
            .then().statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testFileSuffixEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("directory", "home")
            .pathParam("file", "document.txt")
            .when().get("/api/filesuffix/{directory}/{file}")
            .then().statusCode(200)
            .body(equalTo(".txt"));
    }

    @Test
    public void testNotypevarEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("i", 1)
            .pathParam("s", "test")
            .when().get("/api/notypevar/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("test"));
    }

    @Test
    public void testOrdered4Endpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("w", "first")
            .pathParam("x", "second")
            .pathParam("y", "third")
            .pathParam("z", "fourth")
            .when().get("/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200)
            .body(equalTo("first-second-fourth-third"));
    }

    @Test
    public void testRegexEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "example")
            .when().get("/api/pat/{txt}")
            .then().statusCode(200)
            .body(equalTo("example"));

        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "example")
            .pathParam("pat", "ex")
            .when().get("/api/pat/{txt}/{pat}")
            .then().statusCode(200)
            .body(equalTo("true"));
    }

    @Test
    public void testText2txtEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("word1", "hello")
            .pathParam("word2", "world")
            .pathParam("word3", "!")
            .when().get("/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200)
            .body(equalTo("hello world !"));
    }

    @Test
    public void testTitleEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("sex", "male")
            .pathParam("title", "Mr")
            .when().get("/api/title/{sex}/{title}")
            .then().statusCode(200)
            .body(equalTo("Mr"));

        given().baseUri(baseUrlOfSut)
            .pathParam("sex", "female")
            .pathParam("title", "Mrs")
            .when().get("/api/title/{sex}/{title}")
            .then().statusCode(200)
            .body(equalTo("Mrs"));
    }
}
