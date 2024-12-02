
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

public class run03_FisherTest {

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
    public void testFisherEndpoint() {
        // Test with valid parameters
        given().pathParam("m", 4).pathParam("n", 6).pathParam("x", 1.5)
            .when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then().statusCode(200)
            .body("resultAsDouble", greaterThanOrEqualTo(0.0))
            .body("resultAsDouble", lessThanOrEqualTo(1.0));

        // Test with m and n as 0 (edge case)
        given().pathParam("m", 0).pathParam("n", 0).pathParam("x", 1.5)
            .when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then().statusCode(200)
            .body("resultAsDouble", equalTo(0.0));

        // Test with m and n as negative values (invalid case)
        given().pathParam("m", -1).pathParam("n", -1).pathParam("x", 1.5)
            .when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then().statusCode(400); // Assuming the API returns 400 for invalid parameters

        // Test with x as 0 (edge case)
        given().pathParam("m", 4).pathParam("n", 6).pathParam("x", 0.0)
            .when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then().statusCode(200)
            .body("resultAsDouble", equalTo(0.0));

        // Test with large values
        given().pathParam("m", 10000).pathParam("n", 20000).pathParam("x", 1000.0)
            .when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then().statusCode(200)
            .body("resultAsDouble", greaterThanOrEqualTo(0.0))
            .body("resultAsDouble", lessThanOrEqualTo(1.0));

        // Test unauthorized access (assuming some endpoints require authentication)
        given().auth().none()
            .pathParam("m", 4).pathParam("n", 6).pathParam("x", 1.5)
            .when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then().statusCode(anyOf(is(401), is(403)));
    }
}
