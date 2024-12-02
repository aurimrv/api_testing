
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

public class v0_gpt4o_run01_BessjTest {

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
    public void testBessjUsingGET() {
        // Test for n < 2
        given().get(baseUrlOfSut + "/api/bessj/1/5.0")
                .then().statusCode(400);

        // Test for ax == 0
        given().get(baseUrlOfSut + "/api/bessj/3/0.0")
                .then().statusCode(400);

        // Test for ax > n
        given().get(baseUrlOfSut + "/api/bessj/3/5.0")
                .then().statusCode(200)
                .body("resultAsDouble", notNullValue());

        // Test for ax <= n
        given().get(baseUrlOfSut + "/api/bessj/5/3.0")
                .then().statusCode(200)
                .body("resultAsDouble", notNullValue());

        // Test for negative x
        given().get(baseUrlOfSut + "/api/bessj/3/-5.0")
                .then().statusCode(200)
                .body("resultAsDouble", notNullValue());
    }

    @Test
    public void testBessjResponses() {
        // Test for 401 Unauthorized
        given().auth().basic("wrongUser", "wrongPassword")
                .get(baseUrlOfSut + "/api/bessj/3/5.0")
                .then().statusCode(401);

        // Test for 403 Forbidden
        given().auth().basic("forbiddenUser", "forbiddenPassword")
                .get(baseUrlOfSut + "/api/bessj/3/5.0")
                .then().statusCode(403);

        // Test for 404 Not Found
        given().get(baseUrlOfSut + "/api/bessj/999/999.0")
                .then().statusCode(404);
    }
}
