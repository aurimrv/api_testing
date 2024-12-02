
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

public class v3_gpt4turbo_run03_BessjTest {

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
    public void testBessjValidInput() {
        int n = 3;
        double x = 5.0;

        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .when().get("/api/bessj/{n}/{x}", n, x)
            .then().statusCode(200);

        double result = response.extract().jsonPath().getDouble("resultAsDouble");
        assertTrue(result > -1e10 && result < 1e10); // Expected range of results
    }

    @Test
    public void testBessjNLessThan2ThrowsError() {
        int n = 1;
        double x = 5.0;

        given().baseUri(baseUrlOfSut)
            .when().get("/api/bessj/{n}/{x}", n, x)
            .then().statusCode(500); // Expecting Internal Server Error due to invalid input
    }

    @Test
    public void testBessjZeroXReturnsZero() {
        int n = 3;
        double x = 0.0;

        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .when().get("/api/bessj/{n}/{x}", n, x)
            .then().statusCode(200);

        double result = response.extract().jsonPath().getDouble("resultAsDouble");
        assertEquals(0.0, result, 0.0);
    }

    @Test
    public void testBessjXGreaterThanN() {
        int n = 3;
        double x = 10.0;

        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .when().get("/api/bessj/{n}/{x}", n, x)
            .then().statusCode(200);

        double result = response.extract().jsonPath().getDouble("resultAsDouble");
        assertTrue(result > -1e10 && result < 1e10); // Expected range of results
    }

    @Test
    public void testBessjXLessThanN() {
        int n = 10;
        double x = 5.0;

        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .when().get("/api/bessj/{n}/{x}", n, x)
            .then().statusCode(200);

        double result = response.extract().jsonPath().getDouble("resultAsDouble");
        assertTrue(result > -1e10 && result < 1e10); // Expected range of results
    }
}
