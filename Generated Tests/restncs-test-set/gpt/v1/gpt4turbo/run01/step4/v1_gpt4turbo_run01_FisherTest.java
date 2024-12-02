
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

public class v1_gpt4turbo_run01_FisherTest {

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
        given().pathParam("m", m)
               .pathParam("n", n)
               .pathParam("x", x)
               .when()
               .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
               .then()
               .statusCode(200)
               .body("resultAsDouble", isOneOf(0.0, 1.0));
    }

    @Test
    public void testFisherEndpointWithEdgeCases() {
        int[] values = {0, 1, Integer.MAX_VALUE};
        for (int m : values) {
            for (int n : values) {
                for (double x : new double[]{0.0, Double.MAX_VALUE, -1.0}) {
                    given().pathParam("m", m)
                           .pathParam("n", n)
                           .pathParam("x", x)
                           .when()
                           .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
                           .then()
                           .statusCode(anyOf(is(200), is(404), is(400)))
                           .body("resultAsDouble", anyOf(is(0.0), is(1.0), nullValue()));
                }
            }
        }
    }
}
