
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

public class run04_CalcTest {
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
        given().when().get(baseUrlOfSut + "/api/calc/pi/0/0")
            .then().statusCode(200)
            .body(is("" + Math.PI));
    }

    @Test
    public void testCalcE() {
        given().when().get(baseUrlOfSut + "/api/calc/e/0/0")
            .then().statusCode(200)
            .body(is("" + Math.E));
    }

    @Test
    public void testCalcSqrt() {
        given().when().get(baseUrlOfSut + "/api/calc/sqrt/16/0")
            .then().statusCode(200)
            .body(is("" + Math.sqrt(16)));
    }

    @Test
    public void testCalcLog() {
        given().when().get(baseUrlOfSut + "/api/calc/log/10/0")
            .then().statusCode(200)
            .body(is("" + Math.log(10)));
    }

    @Test
    public void testCalcSine() {
        given().when().get(baseUrlOfSut + "/api/calc/sine/90/0")
            .then().statusCode(200)
            .body(is("" + Math.sin(Math.toRadians(90))));
    }

    @Test
    public void testCalcCosine() {
        given().when().get(baseUrlOfSut + "/api/calc/cosine/180/0")
            .then().statusCode(200)
            .body(is("" + Math.cos(Math.toRadians(180))));
    }

    @Test
    public void testCalcTangent() {
        given().when().get(baseUrlOfSut + "/api/calc/tangent/45/0")
            .then().statusCode(200)
            .body(is("" + Math.tan(Math.toRadians(45))));
    }

    @Test
    public void testCalcPlus() {
        given().when().get(baseUrlOfSut + "/api/calc/plus/5/3")
            .then().statusCode(200)
            .body(is("8.0"));
    }

    @Test
    public void testCalcSubtract() {
        given().when().get(baseUrlOfSut + "/api/calc/subtract/5/3")
            .then().statusCode(200)
            .body(is("2.0"));
    }

    @Test
    public void testCalcMultiply() {
        given().when().get(baseUrlOfSut + "/api/calc/multiply/5/3")
            .then().statusCode(200)
            .body(is("15.0"));
    }

    @Test
    public void testCalcDivide() {
        given().when().get(baseUrlOfSut + "/api/calc/divide/10/2")
            .then().statusCode(200)
            .body(is("5.0"));
    }

    @Test
    public void testCalcDivideByZero() {
        given().when().get(baseUrlOfSut + "/api/calc/divide/10/0")
            .then().statusCode(200)
            .body(is("Infinity"));
    }
}
