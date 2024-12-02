
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

public class v2_gpt4o_run03_BessjTest {

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
        ValidatableResponse response = given()
            .pathParam("n", 3)
            .pathParam("x", 5.0)
            .when()
            .get(baseUrlOfSut + "/api/bessj/{n}/{x}")
            .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue())
            .body("resultAsDouble", instanceOf(Double.class));
    }

    @Test
    public void testBessjInvalidN() {
        given()
            .pathParam("n", 1)
            .pathParam("x", 5.0)
            .when()
            .get(baseUrlOfSut + "/api/bessj/{n}/{x}")
            .then()
            .statusCode(500)
            .body("message", containsString("Index n less than 2 in bessj"));
    }

    @Test
    public void testBessjInvalidX() {
        given()
            .pathParam("n", 3)
            .pathParam("x", Double.NaN)
            .when()
            .get(baseUrlOfSut + "/api/bessj/{n}/{x}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testBessjSchemaValidation() {
        ValidatableResponse response = given()
            .pathParam("n", 3)
            .pathParam("x", 5.0)
            .when()
            .get(baseUrlOfSut + "/api/bessj/{n}/{x}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schema.json"));
    }

    @Test
    public void testExpintValidInput() {
        ValidatableResponse response = given()
            .pathParam("n", 1)
            .pathParam("x", 5.0)
            .when()
            .get(baseUrlOfSut + "/api/expint/{n}/{x}")
            .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue())
            .body("resultAsDouble", instanceOf(Double.class));
    }

    @Test
    public void testExpintInvalidN() {
        given()
            .pathParam("n", -1)
            .pathParam("x", 5.0)
            .when()
            .get(baseUrlOfSut + "/api/expint/{n}/{x}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testExpintInvalidX() {
        given()
            .pathParam("n", 1)
            .pathParam("x", Double.NaN)
            .when()
            .get(baseUrlOfSut + "/api/expint/{n}/{x}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testExpintSchemaValidation() {
        ValidatableResponse response = given()
            .pathParam("n", 1)
            .pathParam("x", 5.0)
            .when()
            .get(baseUrlOfSut + "/api/expint/{n}/{x}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schema.json"));
    }

    @Test
    public void testFisherValidInput() {
        ValidatableResponse response = given()
            .pathParam("m", 5)
            .pathParam("n", 3)
            .pathParam("x", 1.5)
            .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue())
            .body("resultAsDouble", instanceOf(Double.class));
    }

    @Test
    public void testFisherInvalidM() {
        given()
            .pathParam("m", -1)
            .pathParam("n", 3)
            .pathParam("x", 1.5)
            .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testFisherInvalidX() {
        given()
            .pathParam("m", 5)
            .pathParam("n", 3)
            .pathParam("x", Double.NaN)
            .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testFisherSchemaValidation() {
        ValidatableResponse response = given()
            .pathParam("m", 5)
            .pathParam("n", 3)
            .pathParam("x", 1.5)
            .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schema.json"));
    }

    @Test
    public void testGammqValidInput() {
        ValidatableResponse response = given()
            .pathParam("a", 1.0)
            .pathParam("x", 5.0)
            .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
            .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue())
            .body("resultAsDouble", instanceOf(Double.class));
    }

    @Test
    public void testGammqInvalidA() {
        given()
            .pathParam("a", -1.0)
            .pathParam("x", 5.0)
            .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testGammqInvalidX() {
        given()
            .pathParam("a", 1.0)
            .pathParam("x", Double.NaN)
            .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testGammqSchemaValidation() {
        ValidatableResponse response = given()
            .pathParam("a", 1.0)
            .pathParam("x", 5.0)
            .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schema.json"));
    }

    @Test
    public void testRemainderValidInput() {
        ValidatableResponse response = given()
            .pathParam("a", 10)
            .pathParam("b", 3)
            .when()
            .get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then()
            .statusCode(200)
            .body("resultAsInt", notNullValue())
            .body("resultAsInt", instanceOf(Integer.class));
    }

    @Test
    public void testRemainderInvalidA() {
        given()
            .pathParam("a", -10)
            .pathParam("b", 3)
            .when()
            .get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testRemainderInvalidB() {
        given()
            .pathParam("a", 10)
            .pathParam("b", 0)
            .when()
            .get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testRemainderSchemaValidation() {
        ValidatableResponse response = given()
            .pathParam("a", 10)
            .pathParam("b", 3)
            .when()
            .get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schema.json"));
    }

    @Test
    public void testTriangleValidInput() {
        ValidatableResponse response = given()
            .pathParam("a", 3)
            .pathParam("b", 4)
            .pathParam("c", 5)
            .when()
            .get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
            .then()
            .statusCode(200)
            .body("resultAsInt", notNullValue())
            .body("resultAsInt", instanceOf(Integer.class));
    }

    @Test
    public void testTriangleInvalidA() {
        given()
            .pathParam("a", -1)
            .pathParam("b", 4)
            .pathParam("c", 5)
            .when()
            .get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testTriangleInvalidB() {
        given()
            .pathParam("a", 3)
            .pathParam("b", -1)
            .pathParam("c", 5)
            .when()
            .get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testTriangleInvalidC() {
        given()
            .pathParam("a", 3)
            .pathParam("b", 4)
            .pathParam("c", -1)
            .when()
            .get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testTriangleSchemaValidation() {
        ValidatableResponse response = given()
            .pathParam("a", 3)
            .pathParam("b", 4)
            .pathParam("c", 5)
            .when()
            .get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schema.json"));
    }
}
