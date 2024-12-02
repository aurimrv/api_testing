
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

public class v3_gpt4o_run01_Text2TxtTest {

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
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("op", "add")
            .pathParam("arg1", 5.0)
            .pathParam("arg2", 3.0)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(is("8.0"));
    }

    @Test
    public void testCalcUsingGET_InvalidOperation() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("op", "invalid")
            .pathParam("arg1", 5.0)
            .pathParam("arg2", 3.0)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testText2TxtUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("word1", "two")
            .pathParam("word2", "you")
            .pathParam("word3", "and")
        .when()
            .get("/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200)
            .body(is("2"));
    }

    @Test
    public void testText2TxtUsingGET_InvalidInput() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("word1", "unknown")
            .pathParam("word2", "word")
            .pathParam("word3", "test")
        .when()
            .get("/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200)
            .body(is(""));
    }

    @Test
    public void testCookieUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("name", "session")
            .pathParam("val", "abc123")
            .pathParam("site", "example.com")
        .when()
            .get("/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(is("Set cookie: session=abc123 for example.com"));
    }

    @Test
    public void testCookieUsingGET_MissingParameter() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("name", "session")
            .pathParam("val", "")
            .pathParam("site", "example.com")
        .when()
            .get("/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testDateParseUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("dayname", "Monday")
            .pathParam("monthname", "January")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(is("Parsed date: Monday, January"));
    }

    @Test
    public void testDateParseUsingGET_InvalidDate() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("dayname", "Funday")
            .pathParam("monthname", "Jany")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testPatUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", "hello")
            .pathParam("pat", "he*o")
        .when()
            .get("/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(is("Pattern matched: hello"));
    }

    @Test
    public void testPatUsingGET_NoMatch() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", "hello")
            .pathParam("pat", "he*llo")
        .when()
            .get("/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(is("No match"));
    }

    @Test
    public void testTitleUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("sex", "Mr")
            .pathParam("title", "Smith")
        .when()
            .get("/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(is("Title: Mr Smith"));
    }

    @Test
    public void testTitleUsingGET_InvalidTitle() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("sex", "Mx")
            .pathParam("title", "Unknown")
        .when()
            .get("/api/title/{sex}/{title}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testFileSuffixUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("directory", "docs")
            .pathParam("file", "report.pdf")
        .when()
            .get("/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(is("File suffix: pdf"));
    }

    @Test
    public void testFileSuffixUsingGET_NoSuffix() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("directory", "docs")
            .pathParam("file", "report")
        .when()
            .get("/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(is("No suffix found"));
    }

    @Test
    public void testCostfunsUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("i", 123)
            .pathParam("s", "test")
        .when()
            .get("/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(is("Cost calculated for 123 and test"));
    }

    @Test
    public void testCostfunsUsingGET_InvalidInput() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("i", -1)
            .pathParam("s", "test")
        .when()
            .get("/api/costfuns/{i}/{s}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testNotyPevarUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("i", 456)
            .pathParam("s", "value")
        .when()
            .get("/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(is("Variable type not specified for 456 and value"));
    }

    @Test
    public void testNotyPevarUsingGET_InvalidInput() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("i", 456)
            .pathParam("s", "")
        .when()
            .get("/api/notypevar/{i}/{s}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testOrdered4UsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("w", "one")
            .pathParam("x", "two")
            .pathParam("z", "three")
            .pathParam("y", "four")
        .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(is("Ordered: one, two, three, four"));
    }

    @Test
    public void testOrdered4UsingGET_MissingParameter() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("w", "one")
            .pathParam("x", "")
            .pathParam("z", "three")
            .pathParam("y", "four")
        .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(404);
    }
}
