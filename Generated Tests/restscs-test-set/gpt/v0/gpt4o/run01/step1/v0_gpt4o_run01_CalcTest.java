
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

public class v0_gpt4o_run01_CalcTest {

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
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/calc/pi/0/0").then();
        response.statusCode(200);
        response.body(equalTo("3.141592653589793"));
    }

    @Test
    public void testCalcE() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/calc/e/0/0").then();
        response.statusCode(200);
        response.body(equalTo("2.718281828459045"));
    }

    @Test
    public void testCalcSqrt() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/calc/sqrt/16/0").then();
        response.statusCode(200);
        response.body(equalTo("4.0"));
    }

    @Test
    public void testCalcLog() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/calc/log/1/0").then();
        response.statusCode(200);
        response.body(equalTo("0.0"));
    }

    @Test
    public void testCalcSine() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/calc/sine/1/0").then();
        response.statusCode(200);
        response.body(equalTo("0.8414709848078965"));
    }

    @Test
    public void testCalcCosine() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/calc/cosine/1/0").then();
        response.statusCode(200);
        response.body(equalTo("0.5403023058681398"));
    }

    @Test
    public void testCalcTangent() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/calc/tangent/1/0").then();
        response.statusCode(200);
        response.body(equalTo("1.5574077246549023"));
    }

    @Test
    public void testCalcPlus() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/calc/plus/1/2").then();
        response.statusCode(200);
        response.body(equalTo("3.0"));
    }

    @Test
    public void testCalcSubtract() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/calc/subtract/5/3").then();
        response.statusCode(200);
        response.body(equalTo("2.0"));
    }

    @Test
    public void testCalcMultiply() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/calc/multiply/2/3").then();
        response.statusCode(200);
        response.body(equalTo("6.0"));
    }

    @Test
    public void testCalcDivide() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/calc/divide/6/3").then();
        response.statusCode(200);
        response.body(equalTo("2.0"));
    }

    @Test
    public void testCalcDivideByZero() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/calc/divide/6/0").then();
        response.statusCode(200);
        response.body(equalTo("Infinity"));
    }

    @Test
    public void testCalcInvalidOperation() {
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/calc/invalid/1/2").then();
        response.statusCode(200);
        response.body(equalTo("0.0"));
    }

    @Test
    public void testCalcNotFound() {
        given().get(baseUrlOfSut + "/api/calc/").then().statusCode(404);
        given().get(baseUrlOfSut + "/api/calc/unknown/1/1").then().statusCode(404);
    }

    @Test
    public void testUnauthorized() {
        given().get(baseUrlOfSut + "/api/calc/pi/0/0").then().statusCode(401);
    }

    @Test
    public void testForbidden() {
        given().get(baseUrlOfSut + "/api/calc/pi/0/0").then().statusCode(403);
    }
}
