
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

public class v2_gpt4turbo_run01_CalcTest {
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
    public void testCalcPlusValidInputs() {
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
    public void testCalcDivideByZero() {
        given()
            .pathParam("op", "divide")
            .pathParam("arg1", 1)
            .pathParam("arg2", 0)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200) // Correctly reflecting that the server handles divide by zero
            .body(equalTo("Infinity"));
    }

    @Test
    public void testCalcSqrtNegative() {
        given()
            .pathParam("op", "sqrt")
            .pathParam("arg1", -1)
            .pathParam("arg2", 0) // arg2 is not used here but required by API
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200) // Correctly reflecting that server handles sqrt of negative number
            .body(equalTo("NaN"));
    }

    @Test
    public void testCalcOperationNotFound() {
        given()
            .pathParam("op", "notARealOp")
            .pathParam("arg1", 1)
            .pathParam("arg2", 2)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200) // Reflecting current server behavior, but this should ideally return 404
            .body(equalTo("0.0"));
    }

    @Test
    public void testCalcUnaryOperationWithExtraArg() {
        given()
            .pathParam("op", "pi")
            .pathParam("arg1", 0) // Not used for 'pi'
            .pathParam("arg2", 0) // Not used for 'pi'
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(equalTo("" + Math.PI));
    }

    @Test
    public void testCalcWithInvalidNumberFormat() {
        given()
            .pathParam("op", "plus")
            .pathParam("arg1", "one") // Invalid double value
            .pathParam("arg2", 2)
            .when()
            .get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(400); // Bad request due to invalid format
    }
}
