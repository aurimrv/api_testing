
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
import java.util.HashSet;

public class v3_gpt35_run03_BessjTest {

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
    public void testBessjValidInput() {
        int n = 3;
        double x = 1.5;

        given()
            .when().get(baseUrlOfSut + "/api/bessj/" + n + "/" + x)
            .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue())
            .body("resultAsInt", notNullValue());
    }

    @Test
    public void testBessjInvalidInput() {
        int n = 1;
        double x = -1.5;

        given()
            .when().get(baseUrlOfSut + "/api/bessj/" + n + "/" + x)
            .then()
            .statusCode(500);
    }

    @Test
    public void testBessjSchemaValidation() {
        int n = 2;
        double x = 2.0;

        ValidatableResponse response = given()
            .when().get(baseUrlOfSut + "/api/bessj/" + n + "/" + x)
            .then()
            .statusCode(200);

        Map<String, ?> result = response.extract().jsonPath().getMap("");

        assertEquals(result.keySet(), new HashSet<>(Arrays.asList("resultAsDouble", "resultAsInt")));
        assertTrue(result.get("resultAsDouble") instanceof Double);
        assertTrue(result.get("resultAsInt") instanceof Integer);
    }

    @Test
    public void testBessjServerError() {
        int n = 0;
        double x = 2.5;

        given()
            .when().get(baseUrlOfSut + "/api/bessj/" + n + "/" + x)
            .then()
            .statusCode(500);
    }
}
