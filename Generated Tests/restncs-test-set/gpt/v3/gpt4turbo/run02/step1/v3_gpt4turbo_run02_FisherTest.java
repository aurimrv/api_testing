
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

public class v3_gpt4turbo_run02_FisherTest {
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
    public void testFisherValidInput() {
        int m = 4;
        int n = 10;
        double x = 0.5;

        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("m", m)
            .pathParam("n", n)
            .pathParam("x", x)
            .when()
            .get("/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(200)
            .body("resultAsDouble", isA(Number.class));

        double result = JsonPath.from(response.extract().asString()).getDouble("resultAsDouble");
        assertTrue("Result should be between 0 and 1, was: " + result, result >= 0.0 && result <= 1.0);
    }

    @Test
    public void testFisherErrorHandling() {
        int m = 0;
        int n = 0;
        double x = -1; // Invalid values to potentially trigger error

        given().baseUri(baseUrlOfSut)
            .pathParam("m", m)
            .pathParam("n", n)
            .pathParam("x", x)
            .when()
            .get("/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(anyOf(is(500), is(400), is(404))); // Expecting either Server Error, Bad Request or Not Found
    }
}
