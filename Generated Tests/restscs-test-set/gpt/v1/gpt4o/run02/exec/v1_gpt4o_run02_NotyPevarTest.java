
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

public class v1_gpt4o_run02_NotyPevarTest {

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

    // Test cases for /api/calc/{op}/{arg1}/{arg2}
    @Test
    public void testCalcAdd() {
        given()
            .pathParam("op", "add")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("8.0"));
    }

    @Test
    public void testCalcSubtract() {
        given()
            .pathParam("op", "subtract")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("2.0"));
    }

    @Test
    public void testCalcDivideByZero() {
        given()
            .pathParam("op", "divide")
            .pathParam("arg1", 5)
            .pathParam("arg2", 0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("Infinity"));
    }

    // Test cases for /api/cookie/{name}/{val}/{site}
    @Test
    public void testCookieValid() {
        given()
            .pathParam("name", "session")
            .pathParam("val", "12345")
            .pathParam("site", "example.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("Cookie set"));
    }

    @Test
    public void testCookieNotFound() {
        given()
            .pathParam("name", "session")
            .pathParam("val", "54321")
            .pathParam("site", "nonexistent.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(404);
    }

    // Test cases for /api/costfuns/{i}/{s}
    @Test
    public void testCostfunsResult0() {
        given()
            .pathParam("i", 2)
            .pathParam("s", "world")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testCostfunsResult10() {
        given()
            .pathParam("i", 7)
            .pathParam("s", "hello")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("10"));
    }

    // Test cases for /api/dateparse/{dayname}/{monthname}
    @Test
    public void testDateParseValid() {
        given()
            .pathParam("dayname", "Monday")
            .pathParam("monthname", "January")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("Parsed date"));
    }

    @Test
    public void testDateParseNotFound() {
        given()
            .pathParam("dayname", "Funday")
            .pathParam("monthname", "Januember")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(404);
    }

    // Test cases for /api/filesuffix/{directory}/{file}
    @Test
    public void testFileSuffixValid() {
        given()
            .pathParam("directory", "docs")
            .pathParam("file", "report.txt")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("txt"));
    }

    @Test
    public void testFileSuffixNotFound() {
        given()
            .pathParam("directory", "docs")
            .pathParam("file", "nonexistentfile")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(404);
    }

    // Test cases for /api/notypevar/{i}/{s}
    @Test
    public void testNotyPevarResult0() {
        given()
            .pathParam("i", 4)
            .pathParam("s", "hello")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testNotyPevarResult3() {
        given()
            .pathParam("i", 7)
            .pathParam("s", "world")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("3"));
    }

    // Test cases for /api/ordered4/{w}/{x}/{z}/{y}
    @Test
    public void testOrdered4Valid() {
        given()
            .pathParam("w", "one")
            .pathParam("x", "two")
            .pathParam("z", "three")
            .pathParam("y", "four")
        .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(equalTo("Ordered"));
    }

    @Test
    public void testOrdered4NotFound() {
        given()
            .pathParam("w", "one")
            .pathParam("x", "two")
            .pathParam("z", "three")
            .pathParam("y", "five")
        .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(404);
    }

    // Test cases for /api/pat/{txt}
    @Test
    public void testRegexValid() {
        given()
            .pathParam("txt", "abc123")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(equalTo("Pattern matched"));
    }

    @Test
    public void testRegexNotFound() {
        given()
            .pathParam("txt", "xyz")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
        .then()
            .statusCode(404);
    }

    // Test cases for /api/pat/{txt}/{pat}
    @Test
    public void testPatValid() {
        given()
            .pathParam("txt", "hello123")
            .pathParam("pat", ".*123$")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(equalTo("Pattern matched"));
    }

    @Test
    public void testPatNotFound() throws Exception {
        given()
            .pathParam("txt", "hello123")
            .pathParam("pat", "%5Ehello%24")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .statusCode(404);
    }

    // Test cases for /api/text2txt/{word1}/{word2}/{word3}
    @Test
    public void testText2TxtValid() {
        given()
            .pathParam("word1", "one")
            .pathParam("word2", "two")
            .pathParam("word3", "three")
        .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200)
            .body(equalTo("Text concatenated"));
    }

    @Test
    public void testText2TxtNotFound() {
        given()
            .pathParam("word1", "four")
            .pathParam("word2", "five")
            .pathParam("word3", "six")
        .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(404);
    }

    // Test cases for /api/title/{sex}/{title}
    @Test
    public void testTitleValid() {
        given()
            .pathParam("sex", "male")
            .pathParam("title", "Mr")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("Title matched"));
    }

    @Test
    public void testTitleNotFound() {
        given()
            .pathParam("sex", "unknown")
            .pathParam("title", "Mx")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(404);
    }
}
