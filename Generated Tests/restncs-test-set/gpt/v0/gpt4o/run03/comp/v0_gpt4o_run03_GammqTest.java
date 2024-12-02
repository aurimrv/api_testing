
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

public class v0_gpt4o_run03_GammqTest {
    
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
    public void testGammqValidInputs() {
        double[] aVals = {0.5, 1.0, 2.0, 5.0, 10.0};
        double[] xVals = {0.1, 0.5, 1.0, 2.0, 10.0};
        
        for (double a : aVals) {
            for (double x : xVals) {
                given()
                    .pathParam("a", a)
                    .pathParam("x", x)
                .when()
                    .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
                .then()
                    .statusCode(200)
                    .body("resultAsDouble", notNullValue());
            }
        }
    }

    @Test
    public void testGammqInvalidInputs() {
        double[] invalidA = {-0.5, 0.0};
        double[] invalidX = {-1.0};

        for (double a : invalidA) {
            given()
                .pathParam("a", a)
                .pathParam("x", 1.0)
            .when()
                .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
            .then()
                .statusCode(500);
        }

        for (double x : invalidX) {
            given()
                .pathParam("a", 1.0)
                .pathParam("x", x)
            .when()
                .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
            .then()
                .statusCode(500);
        }
    }

    @Test
    public void testGammqUnauthorized() {
        double a = 1.0;
        double x = 1.0;

        given()
            .pathParam("a", a)
            .pathParam("x", x)
        .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
        .then()
            .statusCode(401);
    }

    @Test
    public void testGammqForbidden() {
        double a = 1.0;
        double x = 1.0;

        given()
            .pathParam("a", a)
            .pathParam("x", x)
        .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
        .then()
            .statusCode(403);
    }

    @Test
    public void testGammqNotFound() {
        double a = 1.0;
        double x = 1.0;

        given()
            .pathParam("a", a)
            .pathParam("x", x)
        .when()
            .get(baseUrlOfSut + "/api/nonexistent/{a}/{x}")
        .then()
            .statusCode(404);
    }
}
