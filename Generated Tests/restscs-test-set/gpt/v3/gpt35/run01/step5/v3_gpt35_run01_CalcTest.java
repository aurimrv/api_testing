
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

public class v3_gpt35_run01_CalcTest {

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
    public void testCalcAPI_Success() {
        String op = "plus";
        double arg1 = 10.0;
        double arg2 = 5.0;

        ValidatableResponse response = given()
                .when()
                .get(baseUrlOfSut + "/api/calc/" + op + "/" + arg1 + "/" + arg2)
                .then()
                .statusCode(200);

        String result = response.extract().asString();
        assertNotNull(result);
        assertEquals("15.0", result);
    }

    @Test
    public void testCalcAPI_InvalidInput() {
        String op = "unknown";
        double arg1 = 10.0;
        double arg2 = 5.0;

        ValidatableResponse response = given()
                .when()
                .get(baseUrlOfSut + "/api/calc/" + op + "/" + arg1 + "/" + arg2)
                .then()
                .statusCode(500); // Testing internal server error for invalid input

        String errorMessage = response.extract().asString();
        assertNotNull(errorMessage);
        assertTrue(errorMessage.contains("Internal Server Error"));
    }

    @Test
    public void testCalcAPI_ErrorDetection() {
        String op = "divide";
        double arg1 = 10.0;
        double arg2 = 0.0; // Division by zero should trigger a 500 Internal Server Error

        ValidatableResponse response = given()
                .when()
                .get(baseUrlOfSut + "/api/calc/" + op + "/" + arg1 + "/" + arg2)
                .then()
                .statusCode(500);

        String errorMessage = response.extract().asString();
        assertNotNull(errorMessage);
        assertTrue(errorMessage.contains("Internal Server Error"));
    }

    // Add more test cases for different operations and scenarios to maximize coverage

}

