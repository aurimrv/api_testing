
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

public class v2_gpt4turbo_run02_CalcTest {

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
    public void testCalcUsingGetWithValidInputs() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/calc")
            .pathParam("op", "plus")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
            .when()
            .get("/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(is("8.0"));
    }

    @Test
    public void testCalcUsingGetWithInvalidOperator() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/calc")
            .pathParam("op", "invalidOp")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
            .when()
            .get("/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(404);
    }

    @Test
    public void testCalcUsingGetDivideByZero() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/calc")
            .pathParam("op", "divide")
            .pathParam("arg1", 5)
            .pathParam("arg2", 0)
            .when()
            .get("/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testCalcUsingGetSchemaValidation() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/calc")
            .pathParam("op", "multiply")
            .pathParam("arg1", 5)
            .pathParam("arg2", 3)
            .when()
            .get("/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200);

        JsonPath jsonPathEvaluator = response.extract().jsonPath();
        double result = jsonPathEvaluator.getDouble("$");
        assertEquals(15.0, result, 0.0);
    }

    @Test
    public void testCalcUsingGetBusinessRuleEnforcement() {
        // Testing subtraction with negative result
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/calc")
            .pathParam("op", "subtract")
            .pathParam("arg1", 5)
            .pathParam("arg2", 10)
            .when()
            .get("/{op}/{arg1}/{arg2}")
            .then()
            .statusCode(200)
            .body(is("-5.0"));
    }
}
