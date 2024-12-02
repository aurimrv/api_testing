
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

public class v0_gpt4o_run03_ExpintTest {
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

    @Test
    public void testExpintValid() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("n", 1)
            .pathParam("x", 1.0)
            .when().get("/api/expint/{n}/{x}")
            .then().statusCode(200)
            .body("resultAsDouble", closeTo(0.2193839343955203, 1e-10));
    }

    @Test
    public void testExpintZeroX() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("n", 1)
            .pathParam("x", 0.0)
            .when().get("/api/expint/{n}/{x}")
            .then().statusCode(400);
    }

    @Test
    public void testExpintNegativeN() {
        given().baseUri(baseUrlOfSut)
            .pathParam("n", -1)
            .pathParam("x", 1.0)
            .when().get("/api/expint/{n}/{x}")
            .then().statusCode(400);
    }

    @Test
    public void testExpintNegativeX() {
        given().baseUri(baseUrlOfSut)
            .pathParam("n", 1)
            .pathParam("x", -1.0)
            .when().get("/api/expint/{n}/{x}")
            .then().statusCode(400);
    }

    @Test
    public void testExpintLargeX() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("n", 1)
            .pathParam("x", 10.0)
            .when().get("/api/expint/{n}/{x}")
            .then().statusCode(200)
            .body("resultAsDouble", closeTo(4.156968929685325E-6, 1e-10));
    }

    @Test
    public void testExpintMaxIterations() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("n", 100)
            .pathParam("x", 0.001)
            .when().get("/api/expint/{n}/{x}")
            .then().statusCode(200);
    }

    @Test
    public void testExpintUnauthorized() {
        // Assuming unauthorized means no authentication header
        given().baseUri(baseUrlOfSut)
            .pathParam("n", 1)
            .pathParam("x", 1.0)
            .when().get("/api/expint/{n}/{x}")
            .then().statusCode(401);
    }

    @Test
    public void testExpintForbidden() {
        // Assuming forbidden means insufficient permissions, might need auth header with insufficient permissions
        given().baseUri(baseUrlOfSut)
            .pathParam("n", 1)
            .pathParam("x", 1.0)
            .when().get("/api/expint/{n}/{x}")
            .then().statusCode(403);
    }

    @Test
    public void testExpintNotFound() {
        given().baseUri(baseUrlOfSut)
            .pathParam("n", 9999)
            .pathParam("x", 9999.0)
            .when().get("/api/expint/{n}/{x}")
            .then().statusCode(404);
    }
}
