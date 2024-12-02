
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

public class v3_gpt35_run02_CostfunsTest {

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
    public void testCostfuns_invalidInputs() {
        // Test with i = 5 and s = "baab" to force an error
        given()
            .when().get(baseUrlOfSut + "/api/costfuns/5/baab")
            .then()
            .statusCode(500);

        // Additional tests with invalid inputs to cover all branches
        given()
            .when().get(baseUrlOfSut + "/api/costfuns/-445/baab")
            .then()
            .statusCode(500);

        given()
            .when().get(baseUrlOfSut + "/api/costfuns/-333/baab")
            .then()
            .statusCode(500);

        given()
            .when().get(baseUrlOfSut + "/api/costfuns/667/baab")
            .then()
            .statusCode(500);

        given()
            .when().get(baseUrlOfSut + "/api/costfuns/555/baab")
            .then()
            .statusCode(500);

        given()
            .when().get(baseUrlOfSut + "/api/costfuns/-4/baab")
            .then()
            .statusCode(500);

        given()
            .when().get(baseUrlOfSut + "/api/costfuns/0/ab")
            .then()
            .statusCode(500);

        given()
            .when().get(baseUrlOfSut + "/api/costfuns/5/ab")
            .then()
            .statusCode(500);

        given()
            .when().get(baseUrlOfSut + "/api/costfuns/5/baabab")
            .then()
            .statusCode(500);

        given()
            .when().get(baseUrlOfSut + "/api/costfuns/5/ba")
            .then()
            .statusCode(500);
    }

    @Test
    public void testCostfuns_schemaValidation() {
        // Test with valid inputs to check schema validation
        given()
            .when().get(baseUrlOfSut + "/api/costfuns/5/baab")
            .then()
            .statusCode(200)
            .body(matchesJsonSchema(new java.io.File("costfuns-schema.json"))); // Assuming a schema file is provided

        // Additional tests with valid inputs to ensure schema validation for different scenarios
    }

    @Test
    public void testCostfuns_businessRules() {
        // Add tests to validate business rules for POST, PUT, DELETE operations
    }
}
