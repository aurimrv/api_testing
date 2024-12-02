
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

public class v1_gpt4o_run01_GammqTest {

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
    public void testGammqValid() {
        given()
            .pathParam("a", 1.0)
            .pathParam("x", 2.0)
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
            .pathParam("x", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testGammqInvalidX() {
        given()
            .pathParam("a", 1.0)
            .pathParam("x", -2.0)
        .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testBessjValid() {
        given()
            .pathParam("n", 1)
            .pathParam("x", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/bessj/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue());
    }

    @Test
    public void testBessjInvalidN() {
        given()
            .pathParam("n", -1)
            .pathParam("x", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/bessj/{n}/{x}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testExpintValid() {
        given()
            .pathParam("n", 1)
            .pathParam("x", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/expint/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue());
    }

    @Test
    public void testExpintInvalidN() {
        given()
            .pathParam("n", -1)
            .pathParam("x", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/expint/{n}/{x}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testExpintInvalidX() {
        given()
            .pathParam("n", 1)
            .pathParam("x", -2.0)
        .when()
            .get(baseUrlOfSut + "/api/expint/{n}/{x}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testFisherValid() {
        given()
            .pathParam("m", 1)
            .pathParam("n", 2)
            .pathParam("x", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue());
    }

    @Test
    public void testFisherInvalidM() {
        given()
            .pathParam("m", -1)
            .pathParam("n", 2)
            .pathParam("x", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testFisherInvalidN() {
        given()
            .pathParam("m", 1)
            .pathParam("n", -2)
            .pathParam("x", 2.0)
        .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testRemainderValid() {
        given()
            .pathParam("a", 5)
            .pathParam("b", 3)
        .when()
            .get(baseUrlOfSut + "/api/remainder/{a}/{b}")
        .then()
            .statusCode(200)
            .body("resultAsInt", notNullValue());
    }

    @Test
    public void testRemainderInvalidA() {
        given()
            .pathParam("a", -5)
            .pathParam("b", 3)
        .when()
            .get(baseUrlOfSut + "/api/remainder/{a}/{b}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testRemainderInvalidB() {
        given()
            .pathParam("a", 5)
            .pathParam("b", 0)
        .when()
            .get(baseUrlOfSut + "/api/remainder/{a}/{b}")
        .then()
            .statusCode(400);
    }

    @Test
    public void testTriangleValid() {
        given()
            .pathParam("a", 3)
            .pathParam("b", 4)
            .pathParam("c", 5)
        .when()
            .get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
        .then()
            .statusCode(200)
            .body("resultAsInt", notNullValue());
    }

    @Test
    public void testTriangleInvalid() {
        given()
            .pathParam("a", 3)
            .pathParam("b", 4)
            .pathParam("c", 10)
        .when()
            .get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
        .then()
            .statusCode(400);
    }
}
