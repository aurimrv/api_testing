
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

public class v0_gpt4o_run02_Ordered4Test {

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
    public void testOrdered4Increasing() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("w", "apple")
            .pathParam("x", "berry")
            .pathParam("y", "cherry")
            .pathParam("z", "date")
        .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(equalTo("increasing"));
    }

    @Test
    public void testOrdered4Decreasing() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("w", "date")
            .pathParam("x", "cherry")
            .pathParam("y", "berry")
            .pathParam("z", "apple")
        .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(equalTo("decreasing"));
    }

    @Test
    public void testOrdered4Unordered() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("w", "apple")
            .pathParam("x", "cherry")
            .pathParam("y", "berry")
            .pathParam("z", "date")
        .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(equalTo("unordered"));
    }

    @Test
    public void testOrdered4InvalidLength() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("w", "app")
            .pathParam("x", "berryy")
            .pathParam("y", "cherry")
            .pathParam("z", "date")
        .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(200)
            .body(equalTo("unordered"));
    }

    @Test
    public void testOrdered4NotFound() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("w", "")
            .pathParam("x", "")
            .pathParam("y", "")
            .pathParam("z", "")
        .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
        .then()
            .statusCode(404);
    }

    // Additional tests for other APIs

    @Test
    public void testCalcUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("op", "add")
            .pathParam("arg1", 3.0)
            .pathParam("arg2", 4.0)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("7.0"));
    }

    @Test
    public void testCookieUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("name", "sessionId")
            .pathParam("val", "12345")
            .pathParam("site", "example.com")
        .when()
            .get("/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("OK"));
    }

    @Test
    public void testCostfunsUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("i", 42)
            .pathParam("s", "example")
        .when()
            .get("/api/costfuns/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("OK"));
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
            .body(equalTo("OK"));
    }

    @Test
    public void testFileSuffixUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("directory", "docs")
            .pathParam("file", "file.txt")
        .when()
            .get("/api/filesuffix/{directory}/{file}")
        .then()
            .statusCode(200)
            .body(equalTo("txt"));
    }

    @Test
    public void testNotyPevarUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("i", 5)
            .pathParam("s", "test")
        .when()
            .get("/api/notypevar/{i}/{s}")
        .then()
            .statusCode(200)
            .body(equalTo("OK"));
    }

    @Test
    public void testRegexUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", "example")
        .when()
            .get("/api/pat/{txt}")
        .then()
            .statusCode(200)
            .body(equalTo("OK"));
    }

    @Test
    public void testPatUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", "example")
            .pathParam("pat", "ex.*")
        .when()
            .get("/api/pat/{txt}/{pat}")
        .then()
            .statusCode(200)
            .body(equalTo("true"));
    }

    @Test
    public void testText2txtUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("word1", "hello")
            .pathParam("word2", "world")
            .pathParam("word3", "test")
        .when()
            .get("/api/text2txt/{word1}/{word2}/{word3}")
        .then()
            .statusCode(200)
            .body(equalTo("hello world test"));
    }

    @Test
    public void testTitleUsingGET() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("sex", "male")
            .pathParam("title", "Mr")
        .when()
            .get("/api/title/{sex}/{title}")
        .then()
            .statusCode(200)
            .body(equalTo("Mr"));
    }
}
