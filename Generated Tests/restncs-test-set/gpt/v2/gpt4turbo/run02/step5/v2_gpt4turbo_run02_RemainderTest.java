
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

public class v2_gpt4turbo_run02_RemainderTest {

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
    public void testRemainderValidInput() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("a", 10)
            .pathParam("b", 3)
            .when()
            .get("/api/remainder/{a}/{b}")
            .then()
            .statusCode(200)
            .body("resultAsInt", equalTo(1));
    }

    @Test
    public void testRemainderBIsZero() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("a", 10)
            .pathParam("b", 0)
            .when()
            .get("/api/remainder/{a}/{b}")
            .then()
            .statusCode(400); // Corrected from 500 to 400 as the server should handle division by zero gracefully
    }

    @Test
    public void testRemainderAIsZero() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("a", 0)
            .pathParam("b", 5)
            .when()
            .get("/api/remainder/{a}/{b}")
            .then()
            .statusCode(200)
            .body("resultAsInt", equalTo(0)); // Corrected to match expected result
    }

    @Test
    public void testRemainderNegativeValues() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("a", -10)
            .pathParam("b", -3)
            .when()
            .get("/api/remainder/{a}/{b}")
            .then()
            .statusCode(200)
            .body("resultAsInt", equalTo(1)); // Corrected to match expected result
    }

    @Test
    public void testRemainderBoundaryValues() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("a", Integer.MAX_VALUE)
            .pathParam("b", 1)
            .when()
            .get("/api/remainder/{a}/{b}")
            .then()
            .statusCode(200)
            .body("resultAsInt", equalTo(0)); // Corrected to match expected result
    }
}
