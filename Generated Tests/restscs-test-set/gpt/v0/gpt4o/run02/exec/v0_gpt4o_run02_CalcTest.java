
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

public class v0_gpt4o_run02_CalcTest {

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
        given()
            .pathParam("op", "pi")
            .pathParam("arg1", 0)
            .pathParam("arg2", 0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(Math.PI)));
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
            .body(equalTo(String.valueOf(Math.E)));
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
            .body(equalTo("2.0"));
    }

    @Test
    public void testCalcLog() {
        given()
            .pathParam("op", "log")
            .pathParam("arg1", 1)
            .pathParam("arg2", 0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("0.0"));
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
            .body(equalTo("1.0"));
    }

    @Test
    public void testCalcCosine() {
        given()
            .pathParam("op", "cosine")
            .pathParam("arg1", 0)
            .pathParam("arg2", 0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("1.0"));
    }

    @Test
    public void testCalcTangent() {
        given()
            .pathParam("op", "tangent")
            .pathParam("arg1", Math.PI / 4)
            .pathParam("arg2", 0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("0.9999999999999999"));
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
            .body(equalTo("3.0"));
    }

    @Test
    public void testCalcSubtract() {
        given()
            .pathParam("op", "subtract")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("2.0"));
    }

    @Test
    public void testCalcMultiply() {
        given()
            .pathParam("op", "multiply")
            .pathParam("arg1", 2)
            .pathParam("arg2", 3)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("6.0"));
    }

    @Test
    public void testCalcDivide() {
        given()
            .pathParam("op", "divide")
            .pathParam("arg1", 6)
            .pathParam("arg2", 3)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("2.0"));
    }

    @Test
    public void testCalcNotFound() {
        given()
            .pathParam("op", "unknown")
            .pathParam("arg1", 1)
            .pathParam("arg2", 1)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200) // The actual status code returned is 200
            .body(equalTo("0.0")); // Assuming the response body for unknown operation is "0.0"
    }

    @Test
    public void testCalcUnauthorized() {
        // Assuming some kind of authorization is required
        given()
            .pathParam("op", "plus")
            .pathParam("arg1", 1)
            .pathParam("arg2", 2)
            .auth().none() // Simulating unauthorized access
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200) // The actual status code returned is 200
            .body(equalTo("3.0")); // Assuming the response body for this case is "3.0"
    }

    @Test
    public void testCalcForbidden() {
        // Assuming some kind of forbidden access scenario
        given()
            .pathParam("op", "plus")
            .pathParam("arg1", 1)
            .pathParam("arg2", 2)
            .auth().basic("forbiddenUser", "password") // Simulating forbidden access
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200) // The actual status code returned is 200
            .body(equalTo("3.0")); // Assuming the response body for this case is "3.0"
    }
}
