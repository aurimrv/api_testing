
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

public class v3_gpt4o_run01_FisherTest {

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
    public void testFisherValidInputs() {
        given()
            .pathParam("m", 10)
            .pathParam("n", 5)
            .pathParam("x", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", allOf(notNullValue(), greaterThanOrEqualTo(0.0), lessThanOrEqualTo(1.0)));
    }

    @Test
    public void testFisherInvalidInputs() {
        given()
            .pathParam("m", -10)
            .pathParam("n", 5)
            .pathParam("x", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testFisherInternalServerError() {
        given()
            .pathParam("m", Integer.MAX_VALUE)
            .pathParam("n", Integer.MAX_VALUE)
            .pathParam("x", Double.MAX_VALUE)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testSchemaValidation() {
        ValidatableResponse response = given()
            .pathParam("m", 10)
            .pathParam("n", 5)
            .pathParam("x", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200);

        Map<String, Object> schema = response.extract().jsonPath().getMap("$");

        assertTrue(schema.containsKey("resultAsDouble"));
        assertTrue(schema.get("resultAsDouble") instanceof Double);
    }

    @Test
    public void testBusinessRuleEnforcement() {
        given()
            .pathParam("m", 10)
            .pathParam("n", 5)
            .pathParam("x", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", allOf(greaterThanOrEqualTo(0.0), lessThanOrEqualTo(1.0)));

        given()
            .pathParam("m", 10)
            .pathParam("n", 5)
            .pathParam("x", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", allOf(greaterThanOrEqualTo(0.0), lessThanOrEqualTo(1.0)));
    }
}
