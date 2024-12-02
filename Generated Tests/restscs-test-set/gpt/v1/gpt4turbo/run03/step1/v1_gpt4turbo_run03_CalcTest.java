
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

public class v1_gpt4turbo_run03_CalcTest {

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
    public void testCalcPlusOperation() {
        ValidatableResponse response = given()
            .pathParam("op", "plus")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("8.0"));

        expectationHandler(response, "Expected result of 8.0 for addition operation.");
    }

    @Test
    public void testCalcDivideOperation() {
        ValidatableResponse response = given()
            .pathParam("op", "divide")
            .pathParam("arg1", 10)
            .pathParam("arg2", 2)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("5.0"));

        expectationHandler(response, "Expected result of 5.0 for division operation.");
    }

    @Test
    public void testCalcSqrtOperation() {
        ValidatableResponse response = given()
            .pathParam("op", "sqrt")
            .pathParam("arg1", 16)
            .pathParam("arg2", 0)  // arg2 is unused in this operation
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("4.0"));

        expectationHandler(response, "Expected result of 4.0 for square root operation.");
    }

    @Test
    public void testCalcPiOperation() {
        ValidatableResponse response = given()
            .pathParam("op", "pi")
            .pathParam("arg1", 0)
            .pathParam("arg2", 0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(Math.PI)));

        expectationHandler(response, "Expected result of PI constant value.");
    }

    @Test
    public void testCalcDivideByZero() {
        ValidatableResponse response = given()
            .pathParam("op", "divide")
            .pathParam("arg1", 10)
            .pathParam("arg2", 0)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(200)
            .body(equalTo("Infinity"));

        expectationHandler(response, "Expected result of Infinity for division by zero.");
    }

    @Test
    public void testCalcUnknownOperation() {
        ValidatableResponse response = given()
            .pathParam("op", "unknown")
            .pathParam("arg1", 10)
            .pathParam("arg2", 5)
        .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(404);

        expectationHandler(response, "Expected 404 status code for unknown operation.");
    }
}
