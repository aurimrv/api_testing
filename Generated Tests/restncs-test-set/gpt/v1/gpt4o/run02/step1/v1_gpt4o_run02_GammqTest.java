
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

public class v1_gpt4o_run02_GammqTest {
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
    public void testGammqUsingGET() {
        // Happy path
        given().baseUri(baseUrlOfSut)
            .pathParam("a", 1.0)
            .pathParam("x", 1.0)
        .when()
            .get("/api/gammq/{a}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue());

        // Test with invalid a
        given().baseUri(baseUrlOfSut)
            .pathParam("a", -1.0)
            .pathParam("x", 1.0)
        .when()
            .get("/api/gammq/{a}/{x}")
        .then()
            .statusCode(404);

        // Test with invalid x
        given().baseUri(baseUrlOfSut)
            .pathParam("a", 1.0)
            .pathParam("x", -1.0)
        .when()
            .get("/api/gammq/{a}/{x}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testBessjUsingGET() {
        // Happy path
        given().baseUri(baseUrlOfSut)
            .pathParam("n", 2)
            .pathParam("x", 3.0)
        .when()
            .get("/api/bessj/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue());

        // Test with invalid n
        given().baseUri(baseUrlOfSut)
            .pathParam("n", -1)
            .pathParam("x", 3.0)
        .when()
            .get("/api/bessj/{n}/{x}")
        .then()
            .statusCode(404);

        // Test with invalid x
        given().baseUri(baseUrlOfSut)
            .pathParam("n", 2)
            .pathParam("x", -3.0)
        .when()
            .get("/api/bessj/{n}/{x}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testExpintUsingGET() {
        // Happy path
        given().baseUri(baseUrlOfSut)
            .pathParam("n", 1)
            .pathParam("x", 2.0)
        .when()
            .get("/api/expint/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue());

        // Test with invalid n
        given().baseUri(baseUrlOfSut)
            .pathParam("n", -1)
            .pathParam("x", 2.0)
        .when()
            .get("/api/expint/{n}/{x}")
        .then()
            .statusCode(404);

        // Test with invalid x
        given().baseUri(baseUrlOfSut)
            .pathParam("n", 1)
            .pathParam("x", -2.0)
        .when()
            .get("/api/expint/{n}/{x}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testFisherUsingGET() {
        // Happy path
        given().baseUri(baseUrlOfSut)
            .pathParam("m", 3)
            .pathParam("n", 4)
            .pathParam("x", 5.0)
        .when()
            .get("/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue());

        // Test with invalid m
        given().baseUri(baseUrlOfSut)
            .pathParam("m", -1)
            .pathParam("n", 4)
            .pathParam("x", 5.0)
        .when()
            .get("/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(404);

        // Test with invalid n
        given().baseUri(baseUrlOfSut)
            .pathParam("m", 3)
            .pathParam("n", -4)
            .pathParam("x", 5.0)
        .when()
            .get("/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(404);

        // Test with invalid x
        given().baseUri(baseUrlOfSut)
            .pathParam("m", 3)
            .pathParam("n", 4)
            .pathParam("x", -5.0)
        .when()
            .get("/api/fisher/{m}/{n}/{x}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testRemainderUsingGET() {
        // Happy path
        given().baseUri(baseUrlOfSut)
            .pathParam("a", 10)
            .pathParam("b", 3)
        .when()
            .get("/api/remainder/{a}/{b}")
        .then()
            .statusCode(200)
            .body("resultAsInt", notNullValue());

        // Test with invalid a
        given().baseUri(baseUrlOfSut)
            .pathParam("a", -10)
            .pathParam("b", 3)
        .when()
            .get("/api/remainder/{a}/{b}")
        .then()
            .statusCode(404);

        // Test with invalid b
        given().baseUri(baseUrlOfSut)
            .pathParam("a", 10)
            .pathParam("b", -3)
        .when()
            .get("/api/remainder/{a}/{b}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testTriangleUsingGET() {
        // Happy path
        given().baseUri(baseUrlOfSut)
            .pathParam("a", 3)
            .pathParam("b", 4)
            .pathParam("c", 5)
        .when()
            .get("/api/triangle/{a}/{b}/{c}")
        .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue());

        // Test with invalid a
        given().baseUri(baseUrlOfSut)
            .pathParam("a", -3)
            .pathParam("b", 4)
            .pathParam("c", 5)
        .when()
            .get("/api/triangle/{a}/{b}/{c}")
        .then()
            .statusCode(404);

        // Test with invalid b
        given().baseUri(baseUrlOfSut)
            .pathParam("a", 3)
            .pathParam("b", -4)
            .pathParam("c", 5)
        .when()
            .get("/api/triangle/{a}/{b}/{c}")
        .then()
            .statusCode(404);

        // Test with invalid c
        given().baseUri(baseUrlOfSut)
            .pathParam("a", 3)
            .pathParam("b", 4)
            .pathParam("c", -5)
        .when()
            .get("/api/triangle/{a}/{b}/{c}")
        .then()
            .statusCode(404);
    }
}
