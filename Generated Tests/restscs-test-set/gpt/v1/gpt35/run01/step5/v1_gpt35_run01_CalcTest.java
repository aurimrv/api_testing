
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

public class v1_gpt35_run01_CalcTest {

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
    public void testCalcEndpoint() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/calc/plus/2.5/3.5")
            .then()
            .statusCode(200)
            .body(equalTo("6.0"));
    }

    @Test
    public void testCookieEndpoint() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/cookie/test/123/example.com")
            .then()
            .statusCode(200)
            .body(equalTo("Cookie set successfully"));
    }

    @Test
    public void testCostFunsEndpoint() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/5/test")
            .then()
            .statusCode(200)
            .body(equalTo("Cost function calculated successfully"));
    }

    @Test
    public void testDateParseEndpoint() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/dateparse/Monday/January")
            .then()
            .statusCode(200)
            .body(equalTo("Parsed date: Monday, January"));
    }

    @Test
    public void testFileSuffixEndpoint() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/filesuffix/images/photo.jpg")
            .then()
            .statusCode(200)
            .body(equalTo("File suffix is jpg"));
    }

    @Test
    public void testNoTypeVarEndpoint() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/notypevar/10/test")
            .then()
            .statusCode(200)
            .body(equalTo("No type variable endpoint successfully called"));
    }

    @Test
    public void testOrdered4Endpoint() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/ordered4/a/b/c/d")
            .then()
            .statusCode(200)
            .body(equalTo("Order: a, b, c, d"));
    }

    @Test
    public void testPatEndpoint() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/pat/test")
            .then()
            .statusCode(200)
            .body(equalTo("Pattern matched successfully"));
    }

    @Test
    public void testText2TxtEndpoint() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/text2txt/word1/word2/word3")
            .then()
            .statusCode(200)
            .body(equalTo("Text transformed successfully"));
    }

    @Test
    public void testTitleEndpoint() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/title/male/Mr")
            .then()
            .statusCode(200)
            .body(equalTo("Title: Mr"));
    }
}
