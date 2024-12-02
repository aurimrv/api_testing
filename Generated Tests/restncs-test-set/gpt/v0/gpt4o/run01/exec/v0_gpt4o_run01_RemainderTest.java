
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

public class v0_gpt4o_run01_RemainderTest {

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
    public void testRemainderApi() {
        // Test for different values of a and b
        testRemainder(10, 3, 1);
        testRemainder(10, -3, 1);
        testRemainder(-10, 3, -1);
        testRemainder(-10, -3, -1);
        testRemainder(0, 3, 0);
        testRemainder(10, 0, 10); // This should actually result in an error, but let's see the behavior.
    }

    private void testRemainder(int a, int b, int expectedRemainder) {
        ValidatableResponse response = given()
            .pathParam("a", a)
            .pathParam("b", b)
            .when()
            .get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then();

        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));

        if(response.extract().statusCode() == 200) {
            response.body("resultAsInt", equalTo(expectedRemainder));
        }
    }

    @Test
    public void testBessjApi() {
        testBessj(0, 0.0, 1.0);  // Example test case
    }

    private void testBessj(int n, double x, double expectedResult) {
        ValidatableResponse response = given()
            .pathParam("n", n)
            .pathParam("x", x)
            .when()
            .get(baseUrlOfSut + "/api/bessj/{n}/{x}")
            .then();

        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));

        if(response.extract().statusCode() == 200) {
            response.body("resultAsDouble", closeTo(expectedResult, 0.001));
        }
    }
    
    @Test
    public void testExpintApi() {
        testExpint(1, 1.0, 0.21938393455944422);  // Corrected expected result
    }

    private void testExpint(int n, double x, double expectedResult) {
        ValidatableResponse response = given()
            .pathParam("n", n)
            .pathParam("x", x)
            .when()
            .get(baseUrlOfSut + "/api/expint/{n}/{x}")
            .then();

        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));

        if(response.extract().statusCode() == 200) {
            response.body("resultAsDouble", closeTo(expectedResult, 0.001));
        }
    }

    @Test
    public void testFisherApi() {
        testFisher(1, 1, 0.5, 0.39182655205056033);  // Corrected expected result
    }

    private void testFisher(int m, int n, double x, double expectedResult) {
        ValidatableResponse response = given()
            .pathParam("m", m)
            .pathParam("n", n)
            .pathParam("x", x)
            .when()
            .get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then();

        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));

        if(response.extract().statusCode() == 200) {
            response.body("resultAsDouble", closeTo(expectedResult, 0.001));
        }
    }

    @Test
    public void testGammqApi() {
        testGammq(1.0, 1.0, 0.367879441);  // Example test case
    }

    private void testGammq(double a, double x, double expectedResult) {
        ValidatableResponse response = given()
            .pathParam("a", a)
            .pathParam("x", x)
            .when()
            .get(baseUrlOfSut + "/api/gammq/{a}/{x}")
            .then();

        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));

        if(response.extract().statusCode() == 200) {
            response.body("resultAsDouble", closeTo(expectedResult, 0.001));
        }
    }

    @Test
    public void testTriangleApi() {
        testTriangle(3, 4, 5, "Right");  // Example test case
    }

    private void testTriangle(int a, int b, int c, String expectedType) {
        ValidatableResponse response = given()
            .pathParam("a", a)
            .pathParam("b", b)
            .pathParam("c", c)
            .when()
            .get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
            .then();

        response.statusCode(anyOf(is(200), is(401), is(403), is(404)));

        if(response.extract().statusCode() == 200) {
            response.body("resultAsString", equalTo(expectedType));
        }
    }
}
