
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

public class v3_gpt4o_run01_CostfunsTest {

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
    public void testCostfunsValid() {
        ValidatableResponse response = given()
            .pathParam("i", 5)
            .pathParam("s", "baab")
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("1"));

        response = given()
            .pathParam("i", -445)
            .pathParam("s", "baab")
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("2"));

        response = given()
            .pathParam("i", -333)
            .pathParam("s", "baab")
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("3"));

        response = given()
            .pathParam("i", 667)
            .pathParam("s", "baab")
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("4"));

        response = given()
            .pathParam("i", 555)
            .pathParam("s", "baab")
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("5"));

        response = given()
            .pathParam("i", 0)
            .pathParam("s", "baab")
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("6"));

        response = given()
            .pathParam("i", 0)
            .pathParam("s", "baab")
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("7"));

        response = given()
            .pathParam("i", 0)
            .pathParam("s", "baaba")
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("8"));

        response = given()
            .pathParam("i", 0)
            .pathParam("s", "baabab")
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("9"));

        response = given()
            .pathParam("i", 0)
            .pathParam("s", "ababab")
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("10"));
    }

    @Test
    public void testCostfunsInvalid() {
        given()
            .pathParam("i", "invalid")
            .pathParam("s", "baab")
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(500);

        given()
            .pathParam("i", 0)
            .pathParam("s", null)
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testCalcValid() {
        given()
            .pathParam("op", "add")
            .pathParam("arg1", 1)
            .pathParam("arg2", 2)
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(equalTo("3.0"));
    }

    @Test
    public void testCalcInvalid() {
        given()
            .pathParam("op", "invalid")
            .pathParam("arg1", 1)
            .pathParam("arg2", 2)
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testCookieValid() {
        given()
            .pathParam("name", "session")
            .pathParam("val", "12345")
            .pathParam("site", "example.com")
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then()
            .statusCode(200)
            .body(equalTo("Cookie set: session=12345 for site=example.com"));
    }

    @Test
    public void testCookieInvalid() {
        given()
            .pathParam("name", null)
            .pathParam("val", "12345")
            .pathParam("site", "example.com")
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testDateParseValid() {
        given()
            .pathParam("dayname", "Monday")
            .pathParam("monthname", "January")
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(equalTo("Date parsed: Monday, January"));
    }

    @Test
    public void testDateParseInvalid() {
        given()
            .pathParam("dayname", "Funday")
            .pathParam("monthname", "Januember")
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testFileSuffixValid() {
        given()
            .pathParam("directory", "/home/user")
            .pathParam("file", "document.txt")
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then()
            .statusCode(200)
            .body(equalTo("File suffix: txt"));
    }

    @Test
    public void testFileSuffixInvalid() {
        given()
            .pathParam("directory", "/home/user")
            .pathParam("file", null)
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testNotyPevarValid() {
        given()
            .pathParam("i", 1)
            .pathParam("s", "test")
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("Notypevar: 1, test"));
    }

    @Test
    public void testNotyPevarInvalid() {
        given()
            .pathParam("i", "invalid")
            .pathParam("s", "test")
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testOrdered4Valid() {
        given()
            .pathParam("w", "apple")
            .pathParam("x", "banana")
            .pathParam("y", "cherry")
            .pathParam("z", "date")
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then()
            .statusCode(200)
            .body(equalTo("Ordered4: apple, banana, date, cherry"));
    }

    @Test
    public void testOrdered4Invalid() {
        given()
            .pathParam("w", "apple")
            .pathParam("x", null)
            .pathParam("y", "cherry")
            .pathParam("z", "date")
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testRegexValid() {
        given()
            .pathParam("txt", "hello world")
            .get(baseUrlOfSut + "/api/pat/{txt}")
            .then()
            .statusCode(200)
            .body(equalTo("Regex: hello world"));
    }

    @Test
    public void testRegexInvalid() {
        given()
            .pathParam("txt", null)
            .get(baseUrlOfSut + "/api/pat/{txt}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testPatValid() {
        given()
            .pathParam("txt", "hello world")
            .pathParam("pat", "hello")
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then()
            .statusCode(200)
            .body(equalTo("Pat: hello world, hello"));
    }

    @Test
    public void testPatInvalid() {
        given()
            .pathParam("txt", "hello world")
            .pathParam("pat", null)
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testText2TxtValid() {
        given()
            .pathParam("word1", "one")
            .pathParam("word2", "two")
            .pathParam("word3", "three")
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then()
            .statusCode(200)
            .body(equalTo("Text2txt: one, two, three"));
    }

    @Test
    public void testText2TxtInvalid() {
        given()
            .pathParam("word1", "one")
            .pathParam("word2", null)
            .pathParam("word3", "three")
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testTitleValid() {
        given()
            .pathParam("sex", "male")
            .pathParam("title", "Mr")
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then()
            .statusCode(200)
            .body(equalTo("Title: male, Mr"));
    }

    @Test
    public void testTitleInvalid() {
        given()
            .pathParam("sex", null)
            .pathParam("title", "Mr")
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then()
            .statusCode(500);
    }
}
