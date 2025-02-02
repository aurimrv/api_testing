
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

public class v0_gpt4o_run01_ExpintTest {
    
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
    public void testExpint() {
        int[] ns = {0, 1, -1, 2};
        double[] xs = {0.0, -0.1, 0.5, 1.0, 1.5, 2.0};

        for (int n : ns) {
            for (double x : xs) {
                try {
                    ValidatableResponse response = given()
                        .pathParam("n", n)
                        .pathParam("x", x)
                        .when()
                        .get(baseUrlOfSut + "/api/expint/{n}/{x}")
                        .then();

                    if (n < 0 || x < 0 || (x == 0 && (n == 0 || n == 1))) {
                        response.statusCode(500);
                    } else {
                        response.statusCode(200);
                        response.body("resultAsDouble", notNullValue());
                    }
                } catch (Exception e) {
                    assertTrue(e.getMessage().contains("error: n < 0 or x < 0")
                               || e.getMessage().contains("continued fraction failed in expint")
                               || e.getMessage().contains("series failed in expint"));
                }
            }
        }
    }

    @Test
    public void testExpintEdgeCases() {
        // Testing edge cases specifically
        double x = 0.0;
        int n = 0;

        // n = 0, x = 0.0 should return 500 error
        given()
            .pathParam("n", n)
            .pathParam("x", x)
            .when()
            .get(baseUrlOfSut + "/api/expint/{n}/{x}")
            .then()
            .statusCode(500);

        // n = 1, x = 0.0 should return 500 error
        n = 1;
        given()
            .pathParam("n", n)
            .pathParam("x", x)
            .when()
            .get(baseUrlOfSut + "/api/expint/{n}/{x}")
            .then()
            .statusCode(500);

        // n = 2, x = 0.0 should return 200 with proper result
        n = 2;
        given()
            .pathParam("n", n)
            .pathParam("x", x)
            .when()
            .get(baseUrlOfSut + "/api/expint/{n}/{x}")
            .then()
            .statusCode(200)
            .body("resultAsDouble", equalTo(1.0 / 1));

        // Large n and x
        n = 100;
        x = 100.0;
        given()
            .pathParam("n", n)
            .pathParam("x", x)
            .when()
            .get(baseUrlOfSut + "/api/expint/{n}/{x}")
            .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue());
    }
}
