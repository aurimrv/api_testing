
package org.restscs.imp;

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

public class v1_gpt35_run02_CalcTest {

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
    public void testCalcEndpoint_constantPi() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/calc/pi/0/0")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(Math.PI)));
    }

    @Test
    public void testCalcEndpoint_unarySqrt() {
        double number = 25.0;
        given()
            .when()
            .get(baseUrlOfSut + "/api/calc/sqrt/" + number + "/0")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(Math.sqrt(number))));
    }

    @Test
    public void testCalcEndpoint_binaryAddition() {
        double num1 = 10.0;
        double num2 = 5.0;
        given()
            .when()
            .get(baseUrlOfSut + "/api/calc/plus/" + num1 + "/" + num2)
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(num1 + num2)));
    }

    @Test
    public void testCalcEndpoint_binaryDivision() {
        double num1 = 20.0;
        double num2 = 4.0;
        given()
            .when()
            .get(baseUrlOfSut + "/api/calc/divide/" + num1 + "/" + num2)
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(num1 / num2)));
    }
}
