
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

public class v3_gpt4turbo_run02_CalcTest {

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
        ValidatableResponse response = given()
            .pathParam("op", "pi")
            .pathParam("arg1", 0)
            .pathParam("arg2", 0)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(is(equalTo("" + Math.PI)));

        assertNotNull(response);
    }

    @Test
    public void testCalcE() {
        given()
            .pathParam("op", "e")
            .pathParam("arg1", 0)
            .pathParam("arg2", 0)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(is(equalTo("" + Math.E)));
    }

    @Test
    public void testCalcSqrt() {
        given()
            .pathParam("op", "sqrt")
            .pathParam("arg1", 4)
            .pathParam("arg2", 0)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(is(equalTo("" + Math.sqrt(4))));
    }

    @Test
    public void testCalcLog() {
        given()
            .pathParam("op", "log")
            .pathParam("arg1", Math.E)
            .pathParam("arg2", 0)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(is(equalTo("" + Math.log(Math.E))));
    }

    @Test
    public void testCalcSine() {
        given()
            .pathParam("op", "sine")
            .pathParam("arg1", Math.PI / 2)
            .pathParam("arg2", 0)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(is(equalTo("" + Math.sin(Math.PI / 2))));
    }

    @Test
    public void testCalcCosine() {
        given()
            .pathParam("op", "cosine")
            .pathParam("arg1", Math.PI)
            .pathParam("arg2", 0)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(is(equalTo("" + Math.cos(Math.PI))));
    }

    @Test
    public void testCalcTangent() {
        given()
            .pathParam("op", "tangent")
            .pathParam("arg1", 0)
            .pathParam("arg2", 0)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(is(equalTo("" + Math.tan(0))));
    }

    @Test
    public void testCalcPlus() {
        given()
            .pathParam("op", "plus")
            .pathParam("arg1", 1)
            .pathParam("arg2", 2)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(is(equalTo("" + (1 + 2))));
    }

    @Test
    public void testCalcSubtract() {
        given()
            .pathParam("op", "subtract")
            .pathParam("arg1", 5)
            .pathParam("arg2", 2)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(is(equalTo("" + (5 - 2))));
    }

    @Test
    public void testCalcMultiply() {
        given()
            .pathParam("op", "multiply")
            .pathParam("arg1", 3)
            .pathParam("arg2", 2)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(is(equalTo("" + (3 * 2))));
    }

    @Test
    public void testCalcDivide() {
        given()
            .pathParam("op", "divide")
            .pathParam("arg1", 8)
            .pathParam("arg2", 2)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(is(equalTo("" + (8 / 2))));
    }

    @Test
    public void testCalcDivideByZero() {
        given()
            .pathParam("op", "divide")
            .pathParam("arg1", 1)
            .pathParam("arg2", 0)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(500) // Expecting an error due to division by zero
            .body(containsString("error"));
    }
}
