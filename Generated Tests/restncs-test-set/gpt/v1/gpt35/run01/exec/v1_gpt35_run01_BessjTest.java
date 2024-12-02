
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

public class v1_gpt35_run01_BessjTest {

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
    public void testBessjEndpoint() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/bessj/3/2.5")
            .then()
            .statusCode(200)
            .body("resultAsDouble", equalTo(0.5983833205316426));
    }

    @Test
    public void testExpintEndpoint() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/expint/2/1.5")
            .then()
            .statusCode(200)
            .body("resultAsDouble", equalTo(2.193281148229865));
    }

    @Test
    public void testFisherEndpoint() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/fisher/4/5/2.0")
            .then()
            .statusCode(200)
            .body("resultAsInt", equalTo(3));
    }

    @Test
    public void testGammqEndpoint() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/gammq/1.5/2.0")
            .then()
            .statusCode(200)
            .body("resultAsDouble", equalTo(0.10907039064601496));
    }

    @Test
    public void testRemainderEndpoint() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/remainder/10/3")
            .then()
            .statusCode(200)
            .body("resultAsInt", equalTo(1));
    }

    @Test
    public void testTriangleEndpoint() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/triangle/3/4/5")
            .then()
            .statusCode(200)
            .body("resultAsInt", equalTo(1));
    }
}
