
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

public class v0_gpt4o_run03_RegexTest {

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
    public void testRegexURL() {
        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "http://example.com")
        .when()
            .get("/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(equalTo("url"));
    }

    @Test
    public void testRegexDate() {
        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "mon01jan")
        .when()
            .get("/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(equalTo("date"));
    }

    @Test
    public void testRegexFloatingPointWithExponent() {
        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "12.34e+56")
        .when()
            .get("/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(equalTo("fpe"));
    }

    @Test
    public void testRegexNone() {
        given().baseUri(baseUrlOfSut)
            .pathParam("txt", "randomString")
        .when()
            .get("/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(equalTo("none"));
    }

    @Test
    public void testCalcEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("op", "add")
            .pathParam("arg1", 1.0)
            .pathParam("arg2", 2.0)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("3.0"));
    }

    @Test
    public void testCookieEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("name", "session")
            .pathParam("val", "abc123")
            .pathParam("site", "example.com")
        .when()
            .get("/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("cookieSet"));
    }

    @Test
    public void testCostfunsEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("i", 1)
            .pathParam("s", "test")
        .when()
            .get("/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("cost:1.0"));
    }

    @Test
    public void testDateParseEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("dayname", "mon")
            .pathParam("monthname", "jan")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("parsed"));
    }

    @Test
    public void testFileSuffixEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("directory", "home")
            .pathParam("file", "document.txt")
        .when()
            .get("/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("txt"));
    }

    @Test
    public void testNotyPevarEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("i", 1)
            .pathParam("s", "test")
        .when()
            .get("/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("var"));
    }

    @Test
    public void testOrdered4Endpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("w", "a")
            .pathParam("x", "b")
            .pathParam("y", "c")
            .pathParam("z", "d")
        .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(equalTo("ordered"));
    }

    @Test
    public void testText2txtEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("word1", "hello")
            .pathParam("word2", "world")
            .pathParam("word3", "!")
        .when()
            .get("/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200)
            .body(equalTo("helloworld!"));
    }

    @Test
    public void testTitleEndpoint() {
        given().baseUri(baseUrlOfSut)
            .pathParam("sex", "Mr")
            .pathParam("title", "Smith")
        .when()
            .get("/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("Mr Smith"));
    }
}
