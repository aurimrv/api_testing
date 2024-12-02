
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

public class v3_gpt4o_run03_FisherTest {

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
    public void testFisherNormalCase() {
        given()
            .pathParam("m", 4)
            .pathParam("n", 6)
            .pathParam("x", 1.5)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", allOf(greaterThanOrEqualTo(0.0), lessThanOrEqualTo(1.0)));
    }

    @Test
    public void testFisherInvalidInput() {
        given()
            .pathParam("m", -1)
            .pathParam("n", 6)
            .pathParam("x", 1.5)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testFisherEdgeCaseZeroX() {
        given()
            .pathParam("m", 4)
            .pathParam("n", 6)
            .pathParam("x", 0)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", equalTo(0.0));
    }

    @Test
    public void testFisherEdgeCaseOneX() {
        given()
            .pathParam("m", 4)
            .pathParam("n", 6)
            .pathParam("x", 1.0)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", allOf(greaterThanOrEqualTo(0.0), lessThanOrEqualTo(1.0)));
    }

    @Test
    public void testFisherEdgeCaseZeroM() {
        given()
            .pathParam("m", 0)
            .pathParam("n", 6)
            .pathParam("x", 1.5)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", equalTo(0.0));
    }

    @Test
    public void testFisherEdgeCaseZeroN() {
        given()
            .pathParam("m", 4)
            .pathParam("n", 0)
            .pathParam("x", 1.5)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testFisherSchemaValidation() {
        ValidatableResponse response = given()
            .pathParam("m", 4)
            .pathParam("n", 6)
            .pathParam("x", 1.5)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200);
        
        Map<String, Object> responseBody = response.extract().jsonPath().getMap("");
        assertNotNull(responseBody.get("resultAsDouble"));
        assertTrue(responseBody.get("resultAsDouble") instanceof Double);
    }

    @Test
    public void testFisherUnauthorized() {
        given()
            .pathParam("m", 4)
            .pathParam("n", 6)
            .pathParam("x", 1.5)
            .auth().preemptive().basic("invalidUser", "invalidPassword")
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(401);
    }

    @Test
    public void testFisherNotFound() {
        given()
            .pathParam("m", 4)
            .pathParam("n", 6)
            .pathParam("x", 1.5)
        .when()
            .get(baseUrlOfSut + "/api/nonexistent/{m}/{n}/{x}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testFisherForbidden() {
        given()
            .pathParam("m", 4)
            .pathParam("n", 6)
            .pathParam("x", 1.5)
            .auth().preemptive().basic("forbiddenUser", "forbiddenPassword")
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(403);
    }
}
