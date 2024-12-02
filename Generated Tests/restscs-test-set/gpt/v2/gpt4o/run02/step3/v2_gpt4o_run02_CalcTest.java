
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
    public void testValidCalcOperations() {
        ValidatableResponse response;

        // Test Pi
        response = given().pathParam("op", "pi").pathParam("arg1", 0).pathParam("arg2", 0)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200).body(equalTo("3.141592653589793"));

        // Test E
        response = given().pathParam("op", "e").pathParam("arg1", 0).pathParam("arg2", 0)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200).body(equalTo("2.718281828459045"));

        // Test Unary Operations
        response = given().pathParam("op", "sqrt").pathParam("arg1", 4).pathParam("arg2", 0)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200).body(equalTo("2.0"));

        response = given().pathParam("op", "log").pathParam("arg1", 1).pathParam("arg2", 0)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200).body(equalTo("0.0"));

        response = given().pathParam("op", "sine").pathParam("arg1", Math.PI / 2).pathParam("arg2", 0)
            .when().get(baseUrlOfSut + "/api/ccalc/{op}/{arg1}/{arg2}")
            .then().statusCode(200).body(equalTo("1.0"));

        response = given().pathParam("op", "cosine").pathParam("arg1", 0).pathParam("arg2", 0)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200).body(equalTo("1.0"));

        response = given().pathParam("op", "tangent").pathParam("arg1", 0).pathParam("arg2", 0)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200).body(equalTo("0.0"));

        // Test Binary Operations
        response = given().pathParam("op", "plus").pathParam("arg1", 1).pathParam("arg2", 1)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200).body(equalTo("2.0"));

        response = given().pathParam("op", "subtract").pathParam("arg1", 1).pathParam("arg2", 1)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200).body(equalTo("0.0"));

        response = given().pathParam("op", "multiply").pathParam("arg1", 2).pathParam("arg2", 2)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200).body(equalTo("4.0"));

        response = given().pathParam("op", "divide").pathParam("arg1", 4).pathParam("arg2", 2)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200).body(equalTo("2.0"));
    }

    @Test
    public void testInvalidCalcOperations() {
        ValidatableResponse response;

        // Invalid operation
        response = given().pathParam("op", "invalidOp").pathParam("arg1", 1).pathParam("arg2", 1)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(404);
    }

    @Test
    public void testErrorScenarios() {
        ValidatableResponse response;

        // Division by zero
        response = given().pathParam("op", "divide").pathParam("arg1", 1).pathParam("arg2", 0)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(500);
    }

    @Test
    public void testSchemaValidation() {
        ValidatableResponse response;

        // Test schema compliance for a valid operation
        response = given().pathParam("op", "plus").pathParam("arg1", 1).pathParam("arg2", 1)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200).body(matchesJsonSchemaInClasspath("schemas/calc_response.json"));
    }

    @Test
    public void testBusinessRuleEnforcement() {
        ValidatableResponse response;

        // Business rule enforcement for valid operations
        response = given().pathParam("op", "plus").pathParam("arg1", 1).pathParam("arg2", 1)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200).body(equalTo("2.0"));

        response = given().pathParam("op", "multiply").pathParam("arg1", 2).pathParam("arg2", 2)
            .when().get(baseUrlOfSut + "/api/calc/{op}/{arg1}/{arg2}")
            .then().statusCode(200).body(equalTo("4.0"));
    }
}
