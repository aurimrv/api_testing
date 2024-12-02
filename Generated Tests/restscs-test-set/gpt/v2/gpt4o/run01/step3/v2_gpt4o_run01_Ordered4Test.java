
package org.restscs;

import  org.junit.AfterClass;
import  org.junit.BeforeClass;
import  org.junit.Before;
import  org.junit.Test;
import static org.junit.Assert.*;
import  java.util.Map;
import  java.util.List;
import static org.evomaster.client.java.controller.api.EMTestUtils.*;
import  org.evomaster.client.java.controller.SutHandler;
import  io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import  io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.*;
import  io.restassured.config.JsonConfig;
import  io.restassured.path.json.config.JsonPathConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static org.evomaster.client.java.controller.contentMatchers.NumberMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.StringMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.SubStringMatcher.*;
import static org.evomaster.client.java.controller.expect.ExpectationHandler.expectationHandler;
import  org.evomaster.client.java.controller.expect.ExpectationHandler;
import  io.restassured.path.json.JsonPath;
import  java.util.Arrays;

public class v2_gpt4o_run01_Ordered4Test {

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
            .basePath("/api/ordered4")
            .pathParam("w", "apple")
            .pathParam("x", "banana")
            .pathParam("y", "cherry")
            .pathParam("z", "date")
            .when()
            .get("/{w}/{x}/{y}/{z}")
            .then()
            .statusCode(200)
            .body(equalTo("increasing"));
    }

    @Test
    public void testOrdered4Decreasing() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/ordered4")
            .pathParam("w", "date")
            .pathParam("x", "cherry")
            .pathParam("y", "banana")
            .pathParam("z", "apple")
            .when()
            .get("/{w}/{x}/{y}/{z}")
            .then()
            .statusCode(200)
            .body(equalTo("decreasing"));
    }

    @Test
    public void testOrdered4Unordered() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/ordered4")
            .pathParam("w", "apple")
            .pathParam("x", "cherry")
            .pathParam("y", "banana")
            .pathParam("z", "date")
            .when()
            .get("/{w}/{x}/{y}/{z}")
            .then()
            .statusCode(200)
            .body(equalTo("unordered"));
    }

    @Test
    public void testOrdered4InvalidLength() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/ordered4")
            .pathParam("w", "ap")
            .pathParam("x", "banana")
            .pathParam("y", "cherry")
            .pathParam("z", "date")
            .when()
            .get("/{w}/{x}/{y}/{z}")
            .then()
            .statusCode(200)
            .body(equalTo("unordered"));
    }

    @Test
    public void testCalcAddition() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/calc")
            .pathParam("op", "add")
            .pathParam("arg1", 1)
            .pathParam("arg2", 2)
            .when()
            .get("/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(equalTo("3.0"));
    }

    @Test
    public void testCalcInvalidOp() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/calc")
            .pathParam("op", "invalid_op")
            .pathParam("arg1", 1)
            .pathParam("arg2", 2)
            .when()
            .get("/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testSchemaValidation() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/calc")
            .pathParam("op", "add")
            .pathParam("arg1", 1)
            .pathParam("arg2", 2)
            .when()
            .get("/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/calcResponseSchema.json"));
    }

    @Test
    public void testCookieEndpoint() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/cookie")
            .pathParam("name", "sessionId")
            .pathParam("val", "12345")
            .pathParam("site", "example.com")
            .when()
            .get("/{name}/{val}/{site}")
            .then()
            .statusCode(200)
            .body(equalTo("Cookie set"));
    }

    @Test
    public void testDateParse() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/dateparse")
            .pathParam("dayname", "Monday")
            .pathParam("monthname", "January")
            .when()
            .get("/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(equalTo("Date parsed successfully"));
    }

    @Test
    public void testFileSuffix() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/filesuffix")
            .pathParam("directory", "docs")
            .pathParam("file", "file.txt")
            .when()
            .get("/{directory}/{file}")
            .then()
            .statusCode(200)
            .body(equalTo("txt"));
    }

    @Test
    public void testNotypevar() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/notypevar")
            .pathParam("i", 10)
            .pathParam("s", "test")
            .when()
            .get("/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("Notypevar executed"));
    }

    @Test
    public void testRegex() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/pat")
            .pathParam("txt", "hello")
            .pathParam("pat", "he.*o")
            .when()
            .get("/{txt}/{pat}")
            .then()
            .statusCode(200)
            .body(equalTo("Pattern matched"));
    }

    @Test
    public void testText2txt() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/text2txt")
            .pathParam("word1", "hello")
            .pathParam("word2", "world")
            .pathParam("word3", "java")
            .when()
            .get("/{word1}/{word2}/{word3}")
            .then()
            .statusCode(200)
            .body(equalTo("Text converted"));
    }

    @Test
    public void testTitle() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/title")
            .pathParam("sex", "male")
            .pathParam("title", "Mr")
            .when()
            .get("/{sex}/{title}")
            .then()
            .statusCode(200)
            .body(equalTo("Title validated"));
    }
}
