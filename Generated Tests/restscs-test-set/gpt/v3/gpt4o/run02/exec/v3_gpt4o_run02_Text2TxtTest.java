
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

public class v3_gpt4o_run02_Text2TxtTest {

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
        ValidatableResponse response = given()
            .pathParam("op", "add")
            .pathParam("arg1", 1)
            .pathParam("arg2", 2)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("3"));

        response = given()
            .pathParam("op", "sub")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("2"));

        response = given()
            .pathParam("op", "mul")
            .pathParam("arg1", 2)
            .pathParam("arg2", 3)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("6"));

        response = given()
            .pathParam("op", "div")
            .pathParam("arg1", 6)
            .pathParam("arg2", 3)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("2"));

        // Test invalid operation
        response = given()
            .pathParam("op", "invalid")
            .pathParam("arg1", 1)
            .pathParam("arg2", 2)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(404);
    }

    @Test
    public void testText2TxtUsingGET() {
        ValidatableResponse response = given()
            .pathParam("word1", "two")
            .pathParam("word2", "you")
            .pathParam("word3", "are")
            .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("2"));

        response = given()
            .pathParam("word1", "for")
            .pathParam("word2", "you")
            .pathParam("word3", "are")
            .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("4"));

        response = given()
            .pathParam("word1", "by")
            .pathParam("word2", "the")
            .pathParam("word3", "way")
            .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("btw"));

        response = given()
            .pathParam("word1", "see")
            .pathParam("word2", "you")
            .pathParam("word3", "are")
            .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("cu"));

        response = given()
            .pathParam("word1", "hello")
            .pathParam("word2", "world")
            .pathParam("word3", "test")
            .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo(""));
    }

    @Test
    public void testCookieUsingGET() {
        ValidatableResponse response = given()
            .pathParam("name", "session")
            .pathParam("val", "1234")
            .pathParam("site", "example.com")
            .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("Cookie set"));

        response = given()
            .pathParam("name", "session")
            .pathParam("val", "invalid")
            .pathParam("site", "example.com")
            .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
            .then()
            .statusCode(403);
    }

    @Test
    public void testDateParseUsingGET() {
        ValidatableResponse response = given()
            .pathParam("dayname", "Monday")
            .pathParam("monthname", "January")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("Parsed date"));

        response = given()
            .pathParam("dayname", "Funday")
            .pathParam("monthname", "Jantober")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(404);
    }

    @Test
    public void testFileSuffixUsingGET() {
        ValidatableResponse response = given()
            .pathParam("directory", "docs")
            .pathParam("file", "manual.txt")
            .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo(".txt"));

        response = given()
            .pathParam("directory", "docs")
            .pathParam("file", "manual")
            .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
            .then()
            .statusCode(404);
    }

    @Test
    public void testPatUsingGET() {
        ValidatableResponse response = given()
            .pathParam("txt", "hello")
            .pathParam("pat", "he")
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("Pattern found"));

        response = given()
            .pathParam("txt", "hello")
            .pathParam("pat", "xy")
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
            .then()
            .statusCode(404);
    }

    @Test
    public void testRegexUsingGET() {
        ValidatableResponse response = given()
            .pathParam("txt", "hello123")
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("Pattern matches regex"));

        response = given()
            .pathParam("txt", "hello")
            .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
            .then()
            .statusCode(404);
    }

    @Test
    public void testTitleUsingGET() {
        ValidatableResponse response = given()
            .pathParam("sex", "male")
            .pathParam("title", "Mr")
            .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("Title valid"));

        response = given()
            .pathParam("sex", "female")
            .pathParam("title", "Ms")
            .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then()
            .statusCode(200);
        response.assertThat().body(equalTo("Title valid"));

        response = given()
            .pathParam("sex", "female")
            .pathParam("title", "Mr")
            .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then()
            .statusCode(403);
    }
}
