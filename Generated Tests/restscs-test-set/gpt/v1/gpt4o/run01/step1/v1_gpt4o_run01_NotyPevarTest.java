
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

public class v1_gpt4o_run01_NotyPevarTest {

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

    // Testing /api/calc/{op}/{arg1}/{arg2} endpoint
    @Test
    public void testCalcUsingGET() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("op", "add")
            .pathParam("arg1", 1.0)
            .pathParam("arg2", 2.0)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("3.0"));

        response = given().baseUri(baseUrlOfSut)
            .pathParam("op", "subtract")
            .pathParam("arg1", 5.0)
            .pathParam("arg2", 3.0)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("2.0"));
    }

    // Testing /api/cookie/{name}/{val}/{site} endpoint
    @Test
    public void testCookieUsingGET() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("name", "testCookie")
            .pathParam("val", "12345")
            .pathParam("site", "example.com")
        .when()
            .get("/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("Cookie set"));

        response = given().baseUri(baseUrlOfSut)
            .pathParam("name", "invalid")
            .pathParam("val", "12345")
            .pathParam("site", "example.com")
        .when()
            .get("/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(404);
    }

    // Testing /api/costfuns/{i}/{s} endpoint
    @Test
    public void testCostfunsUsingGET() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("i", 28)
            .pathParam("s", "test")
        .when()
            .get("/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("2"));
        
        response = given().baseUri(baseUrlOfSut)
            .pathParam("i", 56)
            .pathParam("s", "hello")
        .when()
            .get("/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("56"));
    }

    // Testing /api/dateparse/{dayname}/{monthname} endpoint
    @Test
    public void testDateParseUsingGET() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("dayname", "Monday")
            .pathParam("monthname", "January")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("Parsed Date"));

        response = given().baseUri(baseUrlOfSut)
            .pathParam("dayname", "InvalidDay")
            .pathParam("monthname", "InvalidMonth")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(404);
    }

    // Testing /api/filesuffix/{directory}/{file} endpoint
    @Test
    public void testFileSuffixUsingGET() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("directory", "home")
            .pathParam("file", "document.txt")
        .when()
            .get("/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo(".txt"));

        response = given().baseUri(baseUrlOfSut)
            .pathParam("directory", "home")
            .pathParam("file", "document")
        .when()
            .get("/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(404);
    }

    // Testing /api/notypevar/{i}/{s} endpoint
    @Test
    public void testNotyPevarUsingGET() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("i", 7)
            .pathParam("s", "test")
        .when()
            .get("/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));

        response = given().baseUri(baseUrlOfSut)
            .pathParam("i", 56)
            .pathParam("s", "test")
        .when()
            .get("/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("56"));
    }

    // Testing /api/ordered4/{w}/{x}/{z}/{y} endpoint
    @Test
    public void testOrdered4UsingGET() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("w", "a")
            .pathParam("x", "b")
            .pathParam("z", "c")
            .pathParam("y", "d")
        .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(equalTo("Ordered"));

        response = given().baseUri(baseUrlOfSut)
            .pathParam("w", "1")
            .pathParam("x", "2")
            .pathParam("z", "3")
            .pathParam("y", "4")
        .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(404);
    }

    // Testing /api/pat/{txt} endpoint
    @Test
    public void testRegexUsingGET() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("txt", "123")
        .when()
            .get("/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(equalTo("Match"));

        response = given().baseUri(baseUrlOfSut)
            .pathParam("txt", "abc")
        .when()
            .get("/api/pat/{txt}")
        .then()
            .statusCode(404);
    }

    // Testing /api/pat/{txt}/{pat} endpoint
    @Test
    public void testPatUsingGET() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("txt", "123")
            .pathParam("pat", "\\d+")
        .when()
            .get("/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(equalTo("Match"));

        response = given().baseUri(baseUrlOfSut)
            .pathParam("txt", "abc")
            .pathParam("pat", "\\d+")
        .when()
            .get("/api/pat/{txt}/{pat}")
        .then()
            .statusCode(404);
    }

    // Testing /api/text2txt/{word1}/{word2}/{word3} endpoint
    @Test
    public void testText2txtUsingGET() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("word1", "hello")
            .pathParam("word2", "world")
            .pathParam("word3", "test")
        .when()
            .get("/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200)
            .body(equalTo("Concatenated"));

        response = given().baseUri(baseUrlOfSut)
            .pathParam("word1", "foo")
            .pathParam("word2", "bar")
            .pathParam("word3", "baz")
        .when()
            .get("/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(404);
    }

    // Testing /api/title/{sex}/{title} endpoint
    @Test
    public void testTitleUsingGET() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("sex", "M")
            .pathParam("title", "Mr")
        .when()
            .get("/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("Title matched"));

        response = given().baseUri(baseUrlOfSut)
            .pathParam("sex", "F")
            .pathParam("title", "Ms")
        .when()
            .get("/api/title/{sex}/{title}")
        .then()
            .statusCode(404);
    }
}
