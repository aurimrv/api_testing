
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

public class v0_gpt4o_run01_GammqTest {
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
    public void testGammqEndpoint() {
        // valid inputs
        ValidatableResponse response = given()
            .pathParam("a", 2.5)
            .pathParam("x", 1.5)
            .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
            .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue());

        // testing with a <= 0
        given()
            .pathParam("a", -1.0)
            .pathParam("x", 1.5)
            .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
            .then()
            .statusCode(400); // Assuming the server returns 400 for invalid input

        // testing with x < 0
        given()
            .pathParam("a", 1.0)
            .pathParam("x", -1.0)
            .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
            .then()
            .statusCode(400); // Assuming the server returns 400 for invalid input

        // testing with large values
        given()
            .pathParam("a", 100.0)
            .pathParam("x", 100.0)
            .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
            .then()
            .statusCode(200)
            .body("resultAsDouble", notNullValue());
    }

    @Test
    public void testAllResponses() {
        // Unauthorized
        given()
            .pathParam("a", 2.5)
            .pathParam("x", 1.5)
            .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
            .then()
            .statusCode(200); // Assuming the endpoint does not require authentication

        // Forbidden
        // Assuming there's a way to test forbidden access, e.g., a specific role is required
        // The following test is a placeholder
        given()
            .pathParam("a", 2.5)
            .pathParam("x", 1.5)
            .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
            .then()
            .statusCode(200); // Assuming the endpoint does not return 403

        // Not Found
        given()
            .pathParam("a", 2.5)
            .pathParam("x", 1.5)
            .when()
            .get(baseUrlOfSut + "/api/invalid_endpoint/{a}/{x}")
            .then()
            .statusCode(404);
    }
}
