
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

public class v0_gpt4o_run03_TriangleClassificationTest {

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
    public void testTriangleClassification() {
        // Test for invalid triangles (one or more sides are non-positive)
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/triangle/0/1/1").then();
        response.statusCode(200);
        response.body("resultAsInt", equalTo(0));

        response = given().get(baseUrlOfSut + "/api/triangle/1/0/1").then();
        response.statusCode(200);
        response.body("resultAsInt", equalTo(0));

        response = given().get(baseUrlOfSut + "/api/triangle/1/1/0").then();
        response.statusCode(200);
        response.body("resultAsInt", equalTo(0));

        // Test for valid equilateral triangle
        response = given().get(baseUrlOfSut + "/api/triangle/3/3/3").then();
        response.statusCode(200);
        response.body("resultAsInt", equalTo(3));

        // Test for valid isosceles triangle
        response = given().get(baseUrlOfSut + "/api/triangle/3/3/4").then();
        response.statusCode(200);
        response.body("resultAsInt", equalTo(2));

        response = given().get(baseUrlOfSut + "/api/triangle/3/4/3").then();
        response.statusCode(200);
        response.body("resultAsInt", equalTo(2));

        response = given().get(baseUrlOfSut + "/api/triangle/4/3/3").then();
        response.statusCode(200);
        response.body("resultAsInt", equalTo(2));

        // Test for valid scalene triangle
        response = given().get(baseUrlOfSut + "/api/triangle/3/4/5").then();
        response.statusCode(200);
        response.body("resultAsInt", equalTo(1));

        // Test for invalid triangle (triangle inequality not satisfied)
        response = given().get(baseUrlOfSut + "/api/triangle/1/2/3").then();
        response.statusCode(200);
        response.body("resultAsInt", equalTo(0));

        response = given().get(baseUrlOfSut + "/api/triangle/2/3/1").then();
        response.statusCode(200);
        response.body("resultAsInt", equalTo(0));

        response = given().get(baseUrlOfSut + "/api/triangle/3/1/2").then();
        response.statusCode(200);
        response.body("resultAsInt", equalTo(0));
    }

    @Test
    public void testTriangleClassificationInvalidInput() {
        // Test with missing parameters (should return 404)
        ValidatableResponse response = given().get(baseUrlOfSut + "/api/triangle/3/4").then();
        response.statusCode(404);

        response = given().get(baseUrlOfSut + "/api/triangle/3").then();
        response.statusCode(404);

        response = given().get(baseUrlOfSut + "/api/triangle").then();
        response.statusCode(404);
    }
}
