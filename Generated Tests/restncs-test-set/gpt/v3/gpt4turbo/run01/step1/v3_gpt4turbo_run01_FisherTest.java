
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

public class v3_gpt4turbo_run01_FisherTest {

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
        double result = given().baseUri(baseUrlOfSut)
            .pathParam("m", 10)
            .pathParam("n", 20)
            .pathParam("x", 0.5)
            .when()
            .get("/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(200)
            .body("resultAsDouble", isA(Double.class))
            .extract()
            .path("resultAsDouble");

        assertTrue("Result should be between 0 and 1", result >= 0.0 && result <= 1.0);
    }

    @Test
    public void testFisherInvalidInput() {
        given().baseUri(baseUrlOfSut)
            .pathParam("m", -1)
            .pathParam("n", 20)
            .pathParam("x", 0.5)
            .when()
            .get("/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testFisherBoundaryInput() {
        double result = given().baseUri(baseUrlOfSut)
            .pathParam("m", Integer.MAX_VALUE)
            .pathParam("n", Integer.MAX_VALUE)
            .pathParam("x", Double.MAX_VALUE)
            .when()
            .get("/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(200)
            .body("resultAsDouble", isA(Double.class))
            .extract()
            .path("resultAsDouble");

        assertTrue("Result should be between 0 and 1", result >= 0.0 && result <= 1.0);
    }

    @Test
    public void testFisherZeroInput() {
        given().baseUri(baseUrlOfSut)
            .pathParam("m", 0)
            .pathParam("n", 0)
            .pathParam("x", 0)
            .when()
            .get("/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(500);
    }
}
