
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

public class v2_gpt4turbo_run02_FisherTest {

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
    public void testFisherEndpointValidInput() {
        ValidatableResponse response = given().basePath(baseUrlOfSut)
            .when().get("/api/fisher/10/20/0.5")
            .then().statusCode(200)
            .body("resultAsDouble", isA(Double.class));

        double result = response.extract().path("resultAsDouble");
        assertTrue("Result should be between 0 and 1", result >= 0.0 && result <= 1.0);
    }

    @Test
    public void testFisherEndpointInvalidInput() {
        given().basePath(baseUrlOfSut)
            .when().get("/api/fisher/-1/-1/-0.1")
            .then().statusCode(500);
    }

    @Test
    public void testFisherEndpointBoundaryInput() {
        given().basePath(baseUrlOfSut)
            .when().get("/api/fisher/0/0/0.0")
            .then().statusCode(200)
            .body("resultAsDouble", equalTo(0.0));
    }

    @Test
    public void testFisherEndpointHighInput() {
        given().basePath(baseUrlOfSut)
            .when().get("/api/fisher/1000/1000/10")
            .then().statusCode(200)
            .body("resultAsDouble", equalTo(1.0));
    }
}
