
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

public class v1_gpt4o_run02_RegexTest {

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
            .pathParam("op", "sub")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
            .when().get("/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200)
            .body(equalTo("2"));

        given().baseUri(baseUrlOfSut)
            .pathParam("op", "mul")
            .pathParam("arg1", 2)
            .pathParam("arg2", 3)
            .when().get("/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200)
            .body(equalTo("6"));
    }

    @Test
    public void testCookieEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("name", "sessionId")
            .pathParam("val", "abc123")
            .pathParam("site", "example.com")
            .when().get("/api/cookie/{name}/{val}/{site}")
            .then().statusCode(200)
            .body(equalTo("Cookie set"));

        given().baseUri(baseUrlOfSut)
            .pathParam("name", "userId")
            .pathParam("val", "xyz789")
            .pathParam("site", "example.org")
            .when().get("/api/cookie/{name}/{val}/{site}")
            .then().statusCode(200)
            .body(equalTo("Cookie set"));
    }

    @Test
    public void testCostfunsEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("i", 1)
            .pathParam("s", "testString")
            .when().get("/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("Cost function result"));

        given().baseUri(baseUrlOfSut)
            .pathParam("i", 2)
            .pathParam("s", "anotherTest")
            .when().get("/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("Cost function result"));
    }

    @Test
    public void testDateParseEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("dayname", "mon")
            .pathParam("monthname", "jan")
            .when().get("/api/dateparse/{dayname}/{monthname}")
            .then().statusCode(200)
            .body(equalTo("Date parsed"));

        given().baseUri(baseUrlOfSut)
            .pathParam("dayname", "tue")
            .pathParam("monthname", "feb")
            .when().get("/api/dateparse/{dayname}/{monthname}")
            .then().statusCode(200)
            .body(equalTo("Date parsed"));
    }

    @Test
    public void testFileSuffixEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("directory", "dir1")
            .pathParam("file", "file.txt")
            .when().get("/api/filesuffix/{directory}/{file}")
            .then().statusCode(200)
            .body(equalTo("txt"));

        given().baseUri(baseUrlOfSut)
            .pathParam("directory", "dir2")
            .pathParam("file", "file.pdf")
            .when().get("/api/filesuffix/{directory}/{file}")
            .then().statusCode(200)
            .body(equalTo("pdf"));
    }

    @Test
    public void testNotypevarEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("i", 1)
            .pathParam("s", "test")
            .when().get("/api/notypevar/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("Notypevar result"));

        given().baseUri(baseUrlOfSut)
            .pathParam("i", 2)
            .pathParam("s", "example")
            .when().get("/api/notypevar/{i}/{s}")
            .then().statusCode(200)
            .body(equalTo("Notypevar result"));
    }

    @Test
    public void testOrdered4Endpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("w", "word1")
            .pathParam("x", "word2")
            .pathParam("y", "word3")
            .pathParam("z", "word4")
            .when().get("/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200)
            .body(equalTo("Ordered result"));

        given().baseUri(baseUrlOfSut)
            .pathParam("w", "w1")
            .pathParam("x", "w2")
            .pathParam("y", "w3")
            .pathParam("z", "w4")
            .when().get("/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200)
            .body(equalTo("Ordered result"));
    }

    @Test
    public void testRegexEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "http://example.com")
            .when().get("/api/pat/{txt}")
            .then().statusCode(200)
            .body(equalTo("url"));

        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "mon01jan")
            .when().get("/api/pat/{txt}")
            .then().statusCode(200)
            .body(equalTo("date"));

        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "123.456e+78")
            .when().get("/api/pat/{txt}")
            .then().statusCode(200)
            .body(equalTo("fpe"));

        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "randomText")
            .when().get("/api/pat/{txt}")
            .then().statusCode(200)
            .body(equalTo("none"));
    }

    @Test
    public void testPatEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "example text")
            .pathParam("pat", "example")
            .when().get("/api/pat/{txt}/{pat}")
            .then().statusCode(200)
            .body(equalTo("Pattern matched"));

        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "another text")
            .pathParam("pat", "another")
            .when().get("/api/pat/{txt}/{pat}")
            .then().statusCode(200)
            .body(equalTo("Pattern matched"));
    }

    @Test
    public void testText2txtEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("word1", "one")
            .pathParam("word2", "two")
            .pathParam("word3", "three")
            .when().get("/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200)
            .body(equalTo("Text to text result"));

        given().baseUri(baseUrlOfSut)
            .pathParam("word1", "alpha")
            .pathParam("word2", "beta")
            .pathParam("word3", "gamma")
            .when().get("/api/text2txt/{word1}/{word2}/{word3}")
            .then().statusCode(200)
            .body(equalTo("Text to text result"));
    }

    @Test
    public void testTitleEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("sex", "male")
            .pathParam("title", "mr")
            .when().get("/api/title/{sex}/{title}")
            .then().statusCode(200)
            .body(equalTo("Title result"));

        given().baseUri(baseUrlOfSut)
            .pathParam("sex", "female")
            .pathParam("title", "mrs")
            .when().get("/api/title/{sex}/{title}")
            .then().statusCode(200)
            .body(equalTo("Title result"));
    }
}
