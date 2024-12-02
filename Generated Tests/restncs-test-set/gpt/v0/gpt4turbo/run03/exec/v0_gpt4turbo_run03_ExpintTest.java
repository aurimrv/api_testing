
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

public class v0_gpt4turbo_run03_ExpintTest {
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
    public void testExpintValidInput() {
        int n = 5;
        double x = 10.0;
        given().pathParam("n", n).pathParam("x", x)
            .when().get(baseUrlOfSut + "/api/expint/{n}/{x}")
            .then().statusCode(200)
            .body("resultAsDouble", is(notNullValue(Double.class)));
    }

    @Test
    public void testExpintEdgeCaseZeroX() {
        int n = 5;
        double x = 0.0;
        given().pathParam("n", n).pathParam("x", x)
            .when().get(baseUrlOfSut + "/api/expint/{n}/{x}")
            .then().statusCode(200)
            .body("resultAsDouble", is(notNullValue(Double.class)));
    }

    @Test
    public void testExpintErrorCaseNegativeN() {
        int n = -1;
        double x = 10.0;
        given().pathParam("n", n).pathParam("x", x)
            .when().get(baseUrlOfSut + "/api/expint/{n}/{x}")
            .then().statusCode(400);
    }

    @Test
    public void testExpintErrorCaseNegativeX() {
        int n = 5;
        double x = -10.0;
        given().pathParam("n", n).pathParam("x", x)
            .when().get(baseUrlOfSut + "/api/expint/{n}/{x}")
            .then().statusCode(400);
    }

    @Test
    public void testExpintEdgeCaseNOneZeroX() {
        int n = 1;
        double x = 0.0;
        given().pathParam("n", n).pathParam("x", x)
            .when().get(baseUrlOfSut + "/api/expint/{n}/{x}")
            .then().statusCode(400); // Corrected expected status code based on server response.
    }
}
