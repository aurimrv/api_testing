
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

public class v2_gpt4o_run01_RegexTest {
    
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
    public void testCalcUsingGET_ValidInput() {
        given()
            .pathParam("op", "add")
            .pathParam("arg1", 1.0)
            .pathParam("arg2", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(is("3.0"));
    }

    @Test
    public void testCalcUsingGET_InvalidOp() {
        given()
            .pathParam("op", "invalidOp")
            .pathParam("arg1", 1.0)
            .pathParam("arg2", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testRegexUsingGET_ValidURL() {
        given()
            .pathParam("txt", "http://example.com")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(is("url"));
    }

    @Test
    public void testRegexUsingGET_ValidDate() {
        given()
            .pathParam("txt", "mon01jan")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(is("date"));
    }

    @Test
    public void testRegexUsingGET_InvalidPattern() {
        given()
            .pathParam("txt", "invalidPattern")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(is("none"));
    }

    @Test
    public void testCookieUsingGET_ValidInput() {
        given()
            .pathParam("name", "sessionId")
            .pathParam("val", "xyz")
            .pathParam("site", "example.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(not(isEmptyOrNullString()));
    }

    @Test
    public void testDateParseUsingGET_ValidInput() {
        given()
            .pathParam("dayname", "mon")
            .pathParam("monthname", "jan")
        .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(not(isEmptyOrNullString()));
    }

    @Test
    public void testFileSuffixUsingGET_ValidInput() {
        given()
            .pathParam("directory", "docs")
            .pathParam("file", "example.txt")
        .when()
            .get(baseUrlOfSut + "/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(is(".txt"));
    }

    @Test
    public void testErrorScenario_500_InternalServerError() {
        given()
            .pathParam("txt", "trigger500")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testCostfunsUsingGET_ValidInput() {
        given()
            .pathParam("i", 1)
            .pathParam("s", "example")
        .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(not(isEmptyOrNullString()));
    }

    @Test
    public void testNotyPevarUsingGET_ValidInput() {
        given()
            .pathParam("i", 10)
            .pathParam("s", "test")
        .when()
            .get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(not(isEmptyOrNullString()));
    }

    @Test
    public void testOrdered4UsingGET_ValidInput() {
        given()
            .pathParam("w", "one")
            .pathParam("x", "two")
            .pathParam("y", "three")
            .pathParam("z", "four")
        .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(not(isEmptyOrNullString()));
    }

    @Test
    public void testText2txtUsingGET_ValidInput() {
        given()
            .pathParam("word1", "hello")
            .pathParam("word2", "world")
            .pathParam("word3", "!")
        .when()
            .get(baseUrlOfSut + "/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200)
            .body(not(isEmptyOrNullString()));
    }

    @Test
    public void testTitleUsingGET_ValidInput() {
        given()
            .pathParam("sex", "male")
            .pathParam("title", "Mr")
        .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(not(isEmptyOrNullString()));
    }
}
