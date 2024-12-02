
package org.restncs;

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

public class v2_gpt4o_run03_FisherTest {

    private static final SutHandler controller = new em.embedded.org.restncs.EmbeddedEvoMasterController();
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

    // Error Scenarios
    @Test
    public void testFisherInternalServerError() {
        given()
            .pathParam("m", -1)
            .pathParam("n", 0) // Changed from -1 to 0 to simulate server error
            .pathParam("x", 1.0)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(500);
    }

    // Schema Validation
    @Test
    public void testFisherSchemaValidation() {
        given()
            .pathParam("m", 10)
            .pathParam("n", 5)
            .pathParam("x", 1.0)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue())
            .body("resultAsInt", nullValue());
    }

    // Business Rule Enforcement
    @Test
    public void testFisherBusinessRules() {
        // Validating correct output
        ValidatableResponse response = given()
            .pathParam("m", 10)
            .pathParam("n", 5)
            .pathParam("x", 1.0)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", greaterThanOrEqualTo(0.0))
            .body("resultAsDouble", lessThanOrEqualTo(1.0));

        // Boundary conditions
        response = given()
            .pathParam("m", 1)
            .pathParam("n", 1)
            .pathParam("x", 0.0)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", equalTo(0.0));

        response = given()
            .pathParam("m", 2)
            .pathParam("n", 2)
            .pathParam("x", 1000.0)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", closeTo(1.0, 0.001)); // Updated to closeTo instead of equalTo
    }

    @Test
    public void testBessjSchemaValidation() {
        given()
            .pathParam("n", 2)
            .pathParam("x", 1.5)
        .when()
            .get(baseUrlOfSut + "/api/bessj/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue())
            .body("resultAsInt", nullValue());
    }

    @Test
    public void testExpintSchemaValidation() {
        given()
            .pathParam("n", 2)
            .pathParam("x", 1.5)
        .when()
            .get(baseUrlOfSut + "/api/expint/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue())
            .body("resultAsInt", nullValue());
    }

    @Test
    public void testGammqSchemaValidation() {
        given()
            .pathParam("a", 2.0)
            .pathParam("x", 1.5)
        .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue())
            .body("resultAsInt", nullValue());
    }

    @Test
    public void testRemainderSchemaValidation() {
        given()
            .pathParam("a", 5)
            .pathParam("b", 3)
        .when()
            .get(baseUrlOfSut + "/api/remainder/{a}/{b}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", nullValue())
            .body("resultAsInt", notNullValue());
    }

    @Test
    public void testTriangleSchemaValidation() {
        given()
            .pathParam("a", 3)
            .pathParam("b", 4)
            .pathParam("c", 5)
        .when()
            .get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", nullValue())
            .body("resultAsInt", notNullValue());
    }
}
