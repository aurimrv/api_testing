
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

public class v1_gpt4o_run02_TitleTest {

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
            .pathParam("op", "add")
            .pathParam("arg1", 1.0)
            .pathParam("arg2", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("3.0"));

        given()
            .pathParam("op", "subtract")
            .pathParam("arg1", 5.0)
            .pathParam("arg2", 3.0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("2.0"));

        given()
            .pathParam("op", "multiply")
            .pathParam("arg1", 3.0)
            .pathParam("arg2", 4.0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("12.0"));

        given()
            .pathParam("op", "divide")
            .pathParam("arg1", 10.0)
            .pathParam("arg2", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("5.0"));
    }

    @Test
    public void testCookieUsingGET() {
        given()
            .pathParam("name", "session")
            .pathParam("val", "12345")
            .pathParam("site", "localhost")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(is(not(emptyString())));
    }

    @Test
    public void testCostfunsUsingGET() {
        given()
            .pathParam("i", 1)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(is(not(emptyString())));
    }

    @Test
    public void testDateParseUsingGET() {
        given()
            .pathParam("dayname", "Monday")
            .pathParam("monthname", "January")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(is(not(emptyString())));
    }

    @Test
    public void testFileSuffixUsingGET() {
        given()
            .pathParam("directory", "docs")
            .pathParam("file", "file.txt")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(is(not(emptyString())));
    }

    @Test
    public void testNotyPevarUsingGET() {
        given()
            .pathParam("i", 42)
            .pathParam("s", "example")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(is(not(emptyString())));
    }

    @Test
    public void testOrdered4UsingGET() {
        given()
            .pathParam("w", "word1")
            .pathParam("x", "word2")
            .pathParam("y", "word3")
            .pathParam("z", "word4")
        .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(is(not(emptyString())));
    }

    @Test
    public void testRegexUsingGET() {
        given()
            .pathParam("txt", "example")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(is(not(emptyString())));
    }

    @Test
    public void testPatUsingGET() {
        given()
            .pathParam("txt", "example")
            .pathParam("pat", ".*")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(is(not(emptyString())));
    }

    @Test
    public void testText2txtUsingGET() {
        given()
            .pathParam("word1", "hello")
            .pathParam("word2", "world")
            .pathParam("word3", "test")
        .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200)
            .body(is(not(emptyString())));
    }

    @Test
    public void testTitleUsingGET() {
        given()
            .pathParam("sex", "male")
            .pathParam("title", "mr")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("1"));

        given()
            .pathParam("sex", "female")
            .pathParam("title", "mrs")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));

        given()
            .pathParam("sex", "none")
            .pathParam("title", "dr")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("2"));

        given()
            .pathParam("sex", "male")
            .pathParam("title", "ms")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("-1"));
    }
}
