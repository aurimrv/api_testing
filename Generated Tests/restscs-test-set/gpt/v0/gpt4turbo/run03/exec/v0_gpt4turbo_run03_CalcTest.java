
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

public class v0_gpt4turbo_run03_CalcTest {
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
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(Math.PI)));
    }

    @Test
    public void testCalcE() {
        given().when().get(baseUrlOfSut + "/api/calc/e/0/0")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(Math.E)));
    }

    @Test
    public void testCalcSqrtPositive() {
        given().when().get(baseUrlOfSut + "/api/calc/sqrt/4/0")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(2.0)));
    }

    @Test
    public void testCalcSqrtNegative() {
        given().when().get(baseUrlOfSut + "/api/calc/sqrt/-1/0")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(Double.NaN)));
    }

    @Test
    public void testCalcLogPositive() {
        given().when().get(baseUrlOfSut + "/api/calc/log/1/0")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(0.0)));
    }

    @Test
    public void testCalcLogZero() {
        given().when().get(baseUrlOfSut + "/api/calc/log/0/0")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(Double.NEGATIVE_INFINITY)));
    }

    @Test
    public void testCalcPlus() {
        given().when().get(baseUrlOfSut + "/api/calc/plus/5/3")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(8.0)));
    }

    @Test
    public void testCalcSubtract() {
        given().when().get(baseUrlOfSut + "/api/calc/subtract/5/3")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(2.0)));
    }

    @Test
    public void testCalcMultiply() {
        given().when().get(baseUrlOfSut + "/api/calc/multiply/5/3")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(15.0)));
    }

    @Test
    public void testCalcDivide() {
        given().when().get(baseUrlOfSut + "/api/calc/divide/6/3")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(2.0)));
    }

    @Test
    public void testCalcDivideByZero() {
        given().when().get(baseUrlOfSut + "/api/calc/divide/6/0")
            .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(Double.POSITIVE_INFINITY)));
    }
}
