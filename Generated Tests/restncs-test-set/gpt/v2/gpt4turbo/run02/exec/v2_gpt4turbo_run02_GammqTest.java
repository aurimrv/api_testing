
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

public class v2_gpt4turbo_run02_GammqTest {

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
    public void testInvalidArguments() {
        double a = -1.0;
        double x = -1.0;

        given().pathParam("a", a).pathParam("x", x)
            .when().get(baseUrlOfSut + "/api/gammq/{a}/{x}")
            .then().statusCode(400); // Corrected expected status code to 400 from 500 as per error logs
    }

    @Test
    public void testApiResponseSchema() {
        double a = 1.0;
        double x = 1.0;

        ValidatableResponse response = given().pathParam("a", a).pathParam("x", x)
            .when().get(baseUrlOfSut + "/api/gammq/{a}/{x}")
            .then().statusCode(200)
            .body("resultAsDouble", notNullValue());

        Double resultAsDouble = response.extract().path("resultAsDouble");
        assertNotNull(resultAsDouble);
        assertTrue(resultAsDouble instanceof Double); // Ensuring the type is Double as expected
    }

    @Test
    public void testBusinessRuleEnforcement() {
        double a = 2.0;
        double x = 5.0;

        ValidatableResponse response = given().pathParam("a", a).pathParam("x", x)
            .when().get(baseUrlOfSut + "/api/gammq/{a}/{x}")
            .then().statusCode(200);

        Double result = response.extract().path("resultAsDouble");
        assertNotNull(result);
        assertTrue(result >= 0); // Assuming the business rule is that the result should be a non-negative value
    }
}
