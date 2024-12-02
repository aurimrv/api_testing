
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

public class v0_gpt4o_run01_FisherTest {

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
    public void testFisher200() {
        ValidatableResponse response = given()
            .pathParam("m", 4)
            .pathParam("n", 6)
            .pathParam("x", 1.5)
            .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(200)
            .body("resultAsDouble", is(notNullValue()))
            .body("resultAsDouble", lessThanOrEqualTo(1.0))
            .body("resultAsDouble", greaterThanOrEqualTo(0.0));
    }

    @Test
    public void testFisher404() {
        given()
            .pathParam("m", 999)
            .pathParam("n", 999)
            .pathParam("x", 999.0)
            .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(404);
    }

    @Test
    public void testFisher401() {
        given()
            .auth().basic("invalidUser", "invalidPass")
            .pathParam("m", 4)
            .pathParam("n", 6)
            .pathParam("x", 1.5)
            .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(401);
    }

    @Test
    public void testFisher403() {
        given()
            .auth().oauth2("invalidToken")
            .pathParam("m", 4)
            .pathParam("n", 6)
            .pathParam("x", 1.5)
            .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then()
            .statusCode(403);
    }
}
