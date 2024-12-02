
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

public class v1_gpt4turbo_run03_FisherTest {

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
    public void testFisherEndpoint() {
        int m = 10;
        int n = 20;
        double x = 0.5;
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("m", m)
            .pathParam("n", n)
            .pathParam("x", x)
        .when()
            .get("/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", is(closeTo(0.0, 1.0)));
    }

    @Test
    public void testFisherEndpointEdgeCases() {
        int[] edgeCasesM = {0, Integer.MAX_VALUE, Integer.MIN_VALUE};
        int[] edgeCasesN = {0, Integer.MAX_VALUE, Integer.MIN_VALUE};
        double[] edgeCasesX = {0.0, Double.MAX_VALUE, -Double.MAX_VALUE, Double.MIN_VALUE, -Double.MIN_VALUE};

        for (int m : edgeCasesM) {
            for (int n : edgeCasesN) {
                for (double x : edgeCasesX) {
                    given()
                        .baseUri(baseUrlOfSut)
                        .pathParam("m", m)
                        .pathParam("n", n)
                        .pathParam("x", x)
                    .when()
                        .get("/api/fisher/{m}/{n}/{x}")
                    .then()
                        .statusCode(anyOf(is(200), is(400), is(404)));  // Assuming the API can return 400 for invalid inputs and 404 for not found scenarios
                }
            }
        }
    }
}
