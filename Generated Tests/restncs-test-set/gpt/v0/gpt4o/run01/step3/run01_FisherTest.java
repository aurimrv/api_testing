
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

public class v0_gpt4o_run01_FisherTest {

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
    public void testFisherEndpoint200Response() {
        // Valid input parameters
        int m = 4;
        int n = 6;
        double x = 1.5;

        given()
            .baseUri(baseUrlOfSut)
            .pathParam("m", m)
            .pathParam("n", n)
            .pathParam("x", x)
        .when()
            .get("/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue());
    }

    @Test
    public void testFisherEndpoint401Response() {
        // Unauthorized access simulation
        int m = 4;
        int n = 6;
        double x = 1.5;

        given()
            .baseUri(baseUrlOfSut)
            .auth().none()
            .pathParam("m", m)
            .pathParam("n", n)
            .pathParam("x", x)
        .when()
            .get("/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(401);
    }

    @Test
    public void testFisherEndpoint403Response() {
        // Forbidden access simulation
        int m = 4;
        int n = 6;
        double x = 1.5;

        given()
            .baseUri(baseUrlOfSut)
            .auth().basic("invalidUser", "invalidPassword")
            .pathParam("m", m)
            .pathParam("n", n)
            .pathParam("x", x)
        .when()
            .get("/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(403);
    }

    @Test
    public void testFisherEndpoint404Response() {
        // Non-existent endpoint simulation
        int m = 4;
        int n = 6;
        double x = 1.5;

        given()
            .baseUri(baseUrlOfSut)
            .pathParam("m", m)
            .pathParam("n", n)
            .pathParam("x", x)
        .when()
            .get("/api/fisher/invalid/{m}/{n}/{x}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testFisherCalculationEdgeCases() {
        // Edge case: m and n are zero
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("m", 0)
            .pathParam("n", 0)
            .pathParam("x", 1.5)
        .when()
            .get("/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", equalTo(0.0));

        // Edge case: very large values
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("m", Integer.MAX_VALUE)
            .pathParam("n", Integer.MAX_VALUE)
            .pathParam("x", Double.MAX_VALUE)
        .when()
            .get("/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue());

        // Edge case: negative values
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("m", -4)
            .pathParam("n", -6)
            .pathParam("x", -1.5)
        .when()
            .get("/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue());
    }

    @Test
    public void testFisherCalculationTypicalCase() {
        // Typical case: valid m, n, and x
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("m", 10)
            .pathParam("n", 20)
            .pathParam("x", 0.5)
        .when()
            .get("/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", equalTo(0.5)); // Adjust expected result as needed
    }
}
