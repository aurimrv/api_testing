
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

public class v3_gpt4o_run02_FisherTest {

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
    public void testFisherValid() {
        ValidatableResponse response = given()
            .pathParam("m", 6)
            .pathParam("n", 4)
            .pathParam("x", 3.0)
            .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue());
    }

    @Test
    public void testFisherInvalidInputs() {
        given()
            .pathParam("m", -1)
            .pathParam("n", 4)
            .pathParam("x", 3.0)
            .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(400);  // Updated expected status code

        given()
            .pathParam("m", 6)
            .pathParam("n", -4)
            .pathParam("x", 3.0)
            .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(400);  // Updated expected status code

        given()
            .pathParam("m", 6)
            .pathParam("n", 4)
            .pathParam("x", -3.0)
            .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(400);  // Updated expected status code
    }

    @Test
    public void testFisherSchemaValidation() {
        ValidatableResponse response = given()
            .pathParam("m", 6)
            .pathParam("n", 4)
            .pathParam("x", 3.0)
            .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(200)
            .body("resultAsDouble", instanceOf(Double.class));
    }

    @Test
    public void testFisherBusinessRules() {
        ValidatableResponse response = given()
            .pathParam("m", 6)
            .pathParam("n", 4)
            .pathParam("x", 3.0)
            .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(200)
            .body("resultAsDouble", lessThanOrEqualTo(1.0))
            .body("resultAsDouble", greaterThanOrEqualTo(0.0));
    }
}
