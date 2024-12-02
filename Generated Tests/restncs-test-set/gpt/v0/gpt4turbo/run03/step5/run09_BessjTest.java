
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

public class run09_BessjTest {

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

    @Test(expected = IllegalArgumentException.class)
    public void testBessjWithInvalidN() {
        given().get(String.format("%s/api/bessj/%d/%f", baseUrlOfSut, 1, Double.NaN));
    }

    @Test
    public void testBessjAtZeroPoint() {
        double result = given()
            .when()
            .get(String.format("%s/api/bessj/%d/%f", baseUrlOfSut, 2, 0.0))
            .then()
            .statusCode(200)
            .extract()
            .jsonPath().getDouble("result");
        assertEquals(0.0, result, 0.0);
    }

    @Test
    public void testBessjWithNormalValues() {
        double result = given()
            .when()
            .get(String.format("%s/api/bessj/%d/%f", baseUrlOfSut, 5, 3.0))
            .then()
            .statusCode(200)
            .extract()
            .jsonPath().getDouble("result");
        assertNotNull(result);
    }

    @Test
    public void testBessjWithLargeX() {
        double result = given()
            .when()
            .get(String.format("%s/api/bessj/%d/%f", baseUrlOfSut, 5, 20.0))
            .then()
            .statusCode(200)
            .extract()
            .jsonPath().getDouble("result");
        assertNotNull(result);
    }

    @Test
    public void testBessjWithNegativeX() {
        double result = given()
            .when()
            .get(String.format("%s/api/bessj/%d/%f", baseUrlOfSut, 5, -3.0))
            .then()
            .statusCode(200)
            .extract()
            .jsonPath().getDouble("result");
        assertNotNull(result);
    }

    @Test
    public void testBessjWithHighNLowX() {
        double result = given()
            .when()
            .get(String.format("%s/api/bessj/%d/%f", baseUrlOfSut, 50, 5.0))
            .then()
            .statusCode(200)
            .extract()
            .jsonPath().getDouble("result");
        assertNotNull(result);
    }
}
