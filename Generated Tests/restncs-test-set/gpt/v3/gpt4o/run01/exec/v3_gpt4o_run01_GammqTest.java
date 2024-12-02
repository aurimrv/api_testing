
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

public class v3_gpt4o_run01_GammqTest {

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
    public void testGammqValidInputs() {
        given()
            .pathParam("a", 2.0)
            .pathParam("x", 3.0)
        .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue());
    }

    @Test
    public void testGammqInvalidA() {
        given()
            .pathParam("a", -1.0)
            .pathParam("x", 3.0)
        .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testGammqInvalidX() {
        given()
            .pathParam("a", 2.0)
            .pathParam("x", -1.0)
        .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testGammqLargeA() {
        given()
            .pathParam("a", 1e10)
            .pathParam("x", 3.0)
        .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue());
    }

    @Test
    public void testGammqNotFound() {
        given()
            .pathParam("a", 2.0)
            .pathParam("x", 3.0)
        .when()
            .get(baseUrlOfSut + "/api/nonexistent/{a}/{x}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testGammqUnauthorized() {
        // Assuming the endpoint requires authentication, add appropriate auth mechanism here
        given()
            .auth().none()
            .pathParam("a", 2.0)
            .pathParam("x", 3.0)
        .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
        .then()
            .statusCode(401);
    }

    @Test
    public void testGammqForbidden() {
        // Assuming the endpoint requires specific permissions, simulate forbidden access
        given()
            .auth().none()  // This should be replaced with actual auth mechanism to simulate forbidden access
            .pathParam("a", 2.0)
            .pathParam("x", 3.0)
        .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
        .then()
            .statusCode(403);
    }

    @Test
    public void testGammqSchemaValidation() {
        ValidatableResponse response = given()
            .pathParam("a", 2.0)
            .pathParam("x", 3.0)
        .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
        .then()
            .statusCode(200);
        
        response.body("resultAsDouble", instanceOf(Double.class));
    }
}
