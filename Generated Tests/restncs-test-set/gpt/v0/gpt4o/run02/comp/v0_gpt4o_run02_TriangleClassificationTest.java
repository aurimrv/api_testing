
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

public class v0_gpt4o_run02_TriangleClassificationTest {

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
    public void testClassify() {
        // Test Equilateral Triangle
        given().get(baseUrlOfSut + "/api/triangle/3/3/3")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(3));

        // Test Isosceles Triangle
        given().get(baseUrlOfSut + "/api/triangle/3/3/2")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(2));

        // Test Scalene Triangle
        given().get(baseUrlOfSut + "/api/triangle/3/4/5")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(1));

        // Test Invalid Triangle (one side zero)
        given().get(baseUrlOfSut + "/api/triangle/0/3/3")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(0));

        // Test Invalid Triangle (sides cannot form a triangle)
        given().get(baseUrlOfSut + "/api/triangle/1/2/3")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(0));

        // Test Negative Sides
        given().get(baseUrlOfSut + "/api/triangle/-1/3/3")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(0));
    }

    @Test
    public void testUnauthorized() {
        // Assuming endpoints that can result in 401 Unauthorized
        // Here we assume that unauthorized endpoints are not applicable for the triangle classification
    }

    @Test
    public void testForbidden() {
        // Assuming endpoints that can result in 403 Forbidden
        // Here we assume that forbidden endpoints are not applicable for the triangle classification
    }

    @Test
    public void testNotFound() {
        // Test Not Found Endpoint
        given().get(baseUrlOfSut + "/api/triangle/3/3")
            .then().statusCode(404);

        given().get(baseUrlOfSut + "/api/triangle/3/3/3/3")
            .then().statusCode(404);
    }
}
