
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

public class v1_gpt4o_run03_FisherTest {
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

    // Tests for the /api/fisher/{m}/{n}/{x} endpoint
    @Test
    public void testFisherEndpoint_ValidValues() {
        ValidatableResponse response = given()
            .pathParam("m", 10)
            .pathParam("n", 5)
            .pathParam("x", 2.0)
            .when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then().statusCode(200);
        
        response.body("resultAsDouble", greaterThanOrEqualTo(0.0));
        response.body("resultAsDouble", lessThanOrEqualTo(1.0));
    }

    @Test
    public void testFisherEndpoint_BoundaryValues() {
        ValidatableResponse response = given()
            .pathParam("m", 0)
            .pathParam("n", 0)
            .pathParam("x", 0.0)
            .when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then().statusCode(200);
        
        response.body("resultAsDouble", equalTo(0.0));
    }

    @Test
    public void testFisherEndpoint_InvalidPathParams() {
        given()
            .pathParam("m", -1)
            .pathParam("n", -1)
            .pathParam("x", -1.0)
            .when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then().statusCode(404);
    }

    @Test
    public void testFisherEndpoint_Unauthorized() {
        // Assuming the endpoint requires authorization (though not specified in the provided swagger doc)
        given()
            .auth().none()
            .pathParam("m", 10)
            .pathParam("n", 5)
            .pathParam("x", 2.0)
            .when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then().statusCode(401);
    }

    // Tests for other endpoints should follow a similar structure
}
