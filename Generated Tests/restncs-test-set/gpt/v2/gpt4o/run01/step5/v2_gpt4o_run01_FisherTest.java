
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

public class v2_gpt4o_run01_FisherTest {

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
    public void testFisher() {
        given()
            .pathParam("m", 4)
            .pathParam("n", 5)
            .pathParam("x", 1.0)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", equalTo(0.5143428032407865));
    }

    @Test
    public void testFisherWithInvalidInput() {
        given()
            .pathParam("m", -1)
            .pathParam("n", 5)
            .pathParam("x", 1.0)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", equalTo(0.0));
    }

    @Test
    public void testFisherSchemaValidation() {
        ValidatableResponse response = given()
            .pathParam("m", 4)
            .pathParam("n", 5)
            .pathParam("x", 1.0)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200);

        response
            .body("resultAsDouble", notNullValue())
            .body("resultAsDouble", instanceOf(Double.class));
    }

    @Test
    public void testFisherBusinessRule() {
        ValidatableResponse response = given()
            .pathParam("m", 4)
            .pathParam("n", 5)
            .pathParam("x", 1.0)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200);

        double result = response.extract().path("resultAsDouble");
        assertTrue(result >= 0.0 && result <= 1.0);
    }

    // Additional tests for other endpoints

    @Test
    public void testExpint() {
        given()
            .pathParam("n", 1)
            .pathParam("x", 1.0)
        .when()
            .get(baseUrlOfSut + "/api/expint/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", equalTo(0.21938393455944422));
    }

    @Test
    public void testExpintWithInvalidInput() {
        given()
            .pathParam("n", -1)
            .pathParam("x", 1.0)
        .when()
            .get(baseUrlOfSut + "/api/expint/{n}/{x}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testExpintSchemaValidation() {
        ValidatableResponse response = given()
            .pathParam("n", 1)
            .pathParam("x", 1.0)
        .when()
            .get(baseUrlOfSut + "/api/expint/{n}/{x}")
        .then()
            .statusCode(200);

        response
            .body("resultAsDouble", notNullValue())
            .body("resultAsDouble", instanceOf(Double.class));
    }

    @Test
    public void testExpintBusinessRule() {
        ValidatableResponse response = given()
            .pathParam("n", 1)
            .pathParam("x", 1.0)
        .when()
            .get(baseUrlOfSut + "/api/expint/{n}/{x}")
        .then()
            .statusCode(200);

        double result = response.extract().path("resultAsDouble");
        assertTrue(result >= 0.0);
    }
}
