
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
import io.restassured.module.jsv.JsonSchemaValidator;

public class v2_gpt4o_run02_CalcTest {

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
        given().get(baseUrlOfSut + "/api/calc/pi/0/0")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(Math.PI)));
    }

    @Test
    public void testCalcE() {
        given().get(baseUrlOfSut + "/api/calc/e/0/0")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(Math.E)));
    }

    @Test
    public void testCalcSqrt() {
        given().get(baseUrlOfSut + "/api/calc/sqrt/4/0")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(Math.sqrt(4))));
    }

    @Test
    public void testCalcLog() {
        given().get(baseUrlOfSut + "/api/calc/log/10/0")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(Math.log(10))));
    }

    @Test
    public void testCalcSine() {
        given().get(baseUrlOfSut + "/api/calc/sine/0/0")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(Math.sin(0))));
    }

    @Test
    public void testCalcCosine() {
        given().get(baseUrlOfSut + "/api/calc/cosine/0/0")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(Math.cos(0))));
    }

    @Test
    public void testCalcTangent() {
        given().get(baseUrlOfSut + "/api/calc/tangent/0/0")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(Math.tan(0))));
    }

    @Test
    public void testCalcPlus() {
        given().get(baseUrlOfSut + "/api/calc/plus/5/3")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(5 + 3)));
    }

    @Test
    public void testCalcSubtract() {
        given().get(baseUrlOfSut + "/api/calc/subtract/5/3")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(5 - 3)));
    }

    @Test
    public void testCalcMultiply() {
        given().get(baseUrlOfSut + "/api/calc/multiply/5/3")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(5 * 3)));
    }

    @Test
    public void testCalcDivide() {
        given().get(baseUrlOfSut + "/api/calc/divide/6/3")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(6 / 3)));
    }

    @Test
    public void testCalcDivideByZero() {
        given().get(baseUrlOfSut + "/api/calc/divide/6/0")
            .then()
            .statusCode(500);
    }

    @Test
    public void testInvalidOp() {
        given().get(baseUrlOfSut + "/api/calc/invalid/1/2")
            .then()
            .statusCode(500);
    }

    @Test
    public void testSchemaValidation() {
        given().get(baseUrlOfSut + "/api/calc/plus/1/1")
            .then()
            .statusCode(200)
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("calc-schema.json"));
    }

    @Test
    public void testUnauthorizedAccess() {
        given().auth().none().get(baseUrlOfSut + "/api/calc/pi/0/0")
            .then()
            .statusCode(401);
    }

    @Test
    public void testForbiddenAccess() {
        given().auth().basic("user", "password").get(baseUrlOfSut + "/api/calc/pi/0/0")
            .then()
            .statusCode(403);
    }

    @Test
    public void testResourceNotFound() {
        given().get(baseUrlOfSut + "/api/calc/nonexistent/0/0")
            .then()
            .statusCode(404);
    }
}
