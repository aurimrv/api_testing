
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

public class v1_gpt4turbo_run02_TriangleClassificationTest {

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
    public void testTriangleClassification_InvalidInputs() {
        given().baseUri(baseUrlOfSut)
            .pathParam("a", 0)
            .pathParam("b", 0)
            .pathParam("c", 0)
            .when()
            .get("/api/triangle/{a}/{b}/{c}")
            .then()
            .statusCode(200)
            .body("resultAsInt", equalTo(0));
    }

    @Test
    public void testTriangleClassification_Equilateral() {
        given().baseUri(baseUrlOfSut)
            .pathParam("a", 1)
            .pathParam("b", 1)
            .pathParam("c", 1)
            .when()
            .get("/api/triangle/{a}/{b}/{c}")
            .then()
            .statusCode(200)
            .body("resultAsInt", equalTo(3));
    }

    @Test
    public void testTriangleClassification_Isosceles() {
        given().baseUri(baseUrlOfSut)
            .pathParam("a", 2)
            .pathParam("b", 2)
            .pathParam("c", 1)
            .when()
            .get("/api/triangle/{a}/{b}/{c}")
            .then()
            .statusCode(200)
            .body("resultAsInt", equalTo(2));
    }

    @Test
    public void testTriangleClassification_Scalene() {
        given().baseUri(baseUrlOfSut)
            .pathParam("a", 2)
            .pathParam("b", 3)
            .pathParam("c", 4)
            .when()
            .get("/api/triangle/{a}/{b}/{c}")
            .then()
            .statusCode(200)
            .body("resultAsInt", equalTo(1));
    }
}
