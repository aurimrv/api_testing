
package org.restncs;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.List;
import static io.restassured.RestAssured.given;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.*;
import io.restassured.config.JsonConfig;
import io.restassured.path.json.config.JsonPathConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;
import org.evomaster.client.java.controller.SutHandler;
import io.restassured.path.json.JsonPath;
import java.util.Arrays;

public class v3_gpt35_run03_ExpintTest {

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
    public void testExpintWithInvalidInputs() {
        ValidatableResponse response = given()
            .pathParam("n", -1)
            .pathParam("x", -1.0)
            .when()
            .get(baseUrlOfSut + "/api/expint/{n}/{x}")
            .then();

        response.statusCode(500);
    }

    @Test
    public void testExpintSchemaValidation() {
        ValidatableResponse response = given()
            .pathParam("n", 2)
            .pathParam("x", 2.0)
            .when()
            .get(baseUrlOfSut + "/api/expint/{n}/{x}")
            .then();

        response.statusCode(200)
            .body("resultAsDouble", notNullValue())
            .body("resultAsInt", notNullValue());
    }

    @Test
    public void testExpintBusinessRuleEnforcement() {
        // Add specific business rule enforcement test scenarios here
        // This could include testing POST, PUT, DELETE operations as well
    }
}
