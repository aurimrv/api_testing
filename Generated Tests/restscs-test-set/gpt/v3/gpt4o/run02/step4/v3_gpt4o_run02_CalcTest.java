
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

public class v3_gpt4o_run02_CalcTest {
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
    public void testCalcPi() {
        given().pathParam("op", "pi")
               .pathParam("arg1", 0)
               .pathParam("arg2", 0)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200)
               .body(equalTo("3.141592653589793"));
    }

    @Test
    public void testCalcSqrt() {
        given().pathParam("op", "sqrt")
               .pathParam("arg1", 16)
               .pathParam("arg2", 0)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200)
               .body(equalTo("4.0"));
    }

    @Test
    public void testCalcPlus() {
        given().pathParam("op", "plus")
               .pathParam("arg1", 2)
               .pathParam("arg2", 3)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200)
               .body(equalTo("5.0"));
    }

    @Test
    public void testCalcDivideByZero() {
        given().pathParam("op", "divide")
               .pathParam("arg1", 1)
               .pathParam("arg2", 0)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(500); // Expecting server error for divide by zero
    }

    @Test
    public void testCalcInvalidOp() {
        given().pathParam("op", "invalidOp")
               .pathParam("arg1", 1)
               .pathParam("arg2", 1)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(404); // Invalid operator should result in not found
    }

    @Test
    public void testCalcLogNegativeNumber() {
        given().pathParam("op", "log")
               .pathParam("arg1", -1)
               .pathParam("arg2", 0)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(500); // Log of negative number should result in server error
    }

    @Test
    public void testCalcMultiply() {
        given().pathParam("op", "multiply")
               .pathParam("arg1", 2)
               .pathParam("arg2", 3)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200)
               .body(equalTo("6.0"));
    }

    @Test
    public void testCalcSubtract() {
        given().pathParam("op", "subtract")
               .pathParam("arg1", 5)
               .pathParam("arg2", 3)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200)
               .body(equalTo("2.0"));
    }

    @Test
    public void testCalcCosine() {
        given().pathParam("op", "cosine")
               .pathParam("arg1", 0)
               .pathParam("arg2", 0)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(200)
               .body(equalTo("1.0"));
    }

    @Test
    public void testCalcTangentInvalidInput() {
        given().pathParam("op", "tangent")
               .pathParam("arg1", 90)
               .pathParam("arg2", 0)
               .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
               .then().statusCode(500); // Expecting server error for tangent of 90 degrees
    }
}
