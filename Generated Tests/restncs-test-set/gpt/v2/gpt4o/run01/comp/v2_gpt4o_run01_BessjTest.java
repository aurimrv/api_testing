
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

public class v2_gpt4o_run01_BessjTest {

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
    public void testBessjValidInputs() {
        given().pathParam("n", 3)
               .pathParam("x", 5.0)
               .when().get(baseUrlOfSut + "/api/bessj/{n}/{x}")
               .then().statusCode(200)
               .body("resultAsDouble", notNullValue());
    }

    @Test
    public void testBessjInvalidInputs() {
        given().pathParam("n", -1)
               .pathParam("x", 5.0)
               .when().get(baseUrlOfSut + "/api/bessj/{n}/{x}")
               .then().statusCode(500);
    }

    @Test
    public void testBessjSchemaValidation() {
        ValidatableResponse response =
            given().pathParam("n", 3)
                   .pathParam("x", 5.0)
                   .when().get(baseUrlOfSut + "/api/bessj/{n}/{x}")
                   .then().statusCode(200);

        response.body("resultAsDouble", instanceOf(Double.class));
    }

    @Test
    public void testBessjBusinessRuleEnforcement() {
        given().pathParam("n", 2)
               .pathParam("x", 0.0)
               .when().get(baseUrlOfSut + "/api/bessj/{n}/{x}")
               .then().statusCode(200)
               .body("resultAsDouble", equalTo(0.0));
    }

    @Test
    public void testBessjNotFound() {
        given().pathParam("n", 10)
               .pathParam("x", -9999.0)
               .when().get(baseUrlOfSut + "/api/bessj/{n}/{x}")
               .then().statusCode(404);
    }

    @Test
    public void testExpintValidInputs() {
        given().pathParam("n", 1)
               .pathParam("x", 5.0)
               .when().get(baseUrlOfSut + "/api/expint/{n}/{x}")
               .then().statusCode(200)
               .body("resultAsDouble", notNullValue());
    }

    @Test
    public void testExpintInvalidInputs() {
        given().pathParam("n", -1)
               .pathParam("x", 5.0)
               .when().get(baseUrlOfSut + "/api/expint/{n}/{x}")
               .then().statusCode(500);
    }

    @Test
    public void testExpintSchemaValidation() {
        ValidatableResponse response =
            given().pathParam("n", 1)
                   .pathParam("x", 5.0)
                   .when().get(baseUrlOfSut + "/api/expint/{n}/{x}")
                   .then().statusCode(200);

        response.body("resultAsDouble", instanceOf(Double.class));
    }

    @Test
    public void testExpintBusinessRuleEnforcement() {
        given().pathParam("n", 0)
               .pathParam("x", 0.0)
               .when().get(baseUrlOfSut + "/api/expint/{n}/{x}")
               .then().statusCode(200)
               .body("resultAsDouble", equalTo(Double.POSITIVE_INFINITY));
    }

    @Test
    public void testExpintNotFound() {
        given().pathParam("n", 10)
               .pathParam("x", -9999.0)
               .when().get(baseUrlOfSut + "/api/expint/{n}/{x}")
               .then().statusCode(404);
    }

    // Additional tests for other endpoints can be added here following the same pattern
}
