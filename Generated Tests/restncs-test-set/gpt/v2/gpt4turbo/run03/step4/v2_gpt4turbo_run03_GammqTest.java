
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
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class v2_gpt4turbo_run03_GammqTest {
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
    public void testGammqWithValidInput() {
        double a = 3.5;
        double x = 2.5;
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("a", a)
            .pathParam("x", x)
            .when()
            .get("/api/gammq/{a}/{x}")
            .then()
            .statusCode(200)
            .body("resultAsDouble", isA(Double.class))
            .body("resultAsDouble", notNullValue());
    }

    @Test
    public void testGammqWithInvalidInput() {
        double a = -1.0;
        double x = 2.5;
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("a", a)
            .pathParam("x", x)
            .when()
            .get("/api/gammq/{a}/{x}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testGammqBoundaryInput() {
        double a = 0.0;
        double x = 1e-30;
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("a", a)
            .pathParam("x", x)
            .when()
            .get("/api/gammq/{a}/{x}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testGammqResponseSchema() {
        double a = 3.5;
        double x = 2.5;
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("a", a)
            .pathParam("x", x)
            .when()
            .get("/api/gammq/{a}/{x}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("path_to_json_schema.json"));
    }
}
