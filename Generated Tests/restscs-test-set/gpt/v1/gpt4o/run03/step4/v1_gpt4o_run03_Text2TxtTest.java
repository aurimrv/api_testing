
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

public class v1_gpt4o_run03_Text2TxtTest {
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
        given().baseUri(baseUrlOfSut)
            .pathParam("op", "add")
            .pathParam("arg1", 1)
            .pathParam("arg2", 2)
        .when().get("/api/calc/{op}/{arg1}/{arg2}")
        .then().statusCode(200)
            .body(equalTo("3"));
    }

    @Test
    public void testCookieUsingGET() {
        given().baseUri(baseUrlOfSut)
            .pathParam("name", "session")
            .pathParam("val", "12345")
            .pathParam("site", "example.com")
        .when().get("/api/cookie/{name}/{val}/{site}")
        .then().statusCode(200)
            .body(equalTo("session=12345; Domain=example.com; Path=/"));
    }

    @Test
    public void testCostfunsUsingGET() {
        given().baseUri(baseUrlOfSut)
            .pathParam("i", 1)
            .pathParam("s", "test")
        .when().get("/api/costfuns/{i}/{s}")
        .then().statusCode(200)
            .body(equalTo("Cost function result"));
    }

    @Test
    public void testDateParseUsingGET() {
        given().baseUri(baseUrlOfSut)
            .pathParam("dayname", "Monday")
            .pathParam("monthname", "January")
        .when().get("/api/dateparse/{dayname}/{monthname}")
        .then().statusCode(200)
            .body(equalTo("Parsed Date"));
    }

    @Test
    public void testFileSuffixUsingGET() {
        given().baseUri(baseUrlOfSut)
            .pathParam("directory", "home")
            .pathParam("file", "document.txt")
        .when().get("/api/filesuffix/{directory}/{file}")
        .then().statusCode(200)
            .body(equalTo("txt"));
    }

    @Test
    public void testNotyPevarUsingGET() {
        given().baseUri(baseUrlOfSut)
            .pathParam("i", 5)
            .pathParam("s", "example")
        .when().get("/api/notypevar/{i}/{s}")
        .then().statusCode(200)
            .body(equalTo("Processed notypevar"));
    }

    @Test
    public void testOrdered4UsingGET() {
        given().baseUri(baseUrlOfSut)
            .pathParam("w", "one")
            .pathParam("x", "two")
            .pathParam("y", "three")
            .pathParam("z", "four")
        .when().get("/api/ordered4/{w}/{x}/{z}/{y}")
        .then().statusCode(200)
            .body(equalTo("Ordered result"));
    }

    @Test
    public void testRegexUsingGET() {
        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "example text")
        .when().get("/api/pat/{txt}")
        .then().statusCode(200)
            .body(equalTo("Regex match result"));
    }

    @Test
    public void testPatUsingGET() {
        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "example text")
            .pathParam("pat", "^example")
        .when().get("/api/pat/{txt}/{pat}")
        .then().statusCode(200)
            .body(equalTo("Pattern match result"));
    }

    @Test
    public void testText2txtUsingGET() {
        given().baseUri(baseUrlOfSut)
            .pathParam("word1", "by")
            .pathParam("word2", "the")
            .pathParam("word3", "way")
        .when().get("/api/text2txt/{word1}/{word2}/{word3}")
        .then().statusCode(200)
            .body(equalTo("btw"));
    }

    @Test
    public void testTitleUsingGET() {
        given().baseUri(baseUrlOfSut)
            .pathParam("sex", "male")
            .pathParam("title", "Mr")
        .when().get("/api/title/{sex}/{title}")
        .then().statusCode(200)
            .body(equalTo("Title processed"));
    }
}
