
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

public class v1_gpt4o_run02_BessjTest {

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
        // Test valid inputs
        given().pathParam("n", 2).pathParam("x", 1.5)
            .when().get(baseUrlOfSut + "/api/bessj/{n}/{x}")
            .then().statusCode(anyOf(is(200), is(400))) // Accepting either 200 or 400
            .body("resultAsDouble", notNullValue());
        
        // Test invalid n
        given().pathParam("n", 1).pathParam("x", 1.5)
            .when().get(baseUrlOfSut + "/api/bessj/{n}/{x}")
            .then().statusCode(400);
        
        // Test invalid x
        given().pathParam("n", 2).pathParam("x", "invalid")
            .when().get(baseUrlOfSut + "/api/bessj/{n}/{x}")
            .then().statusCode(400);
    }

    @Test
    public void testExpintEndpoint() {
        // Test valid inputs
        given().pathParam("n", 2).pathParam("x", 1.5)
            .when().get(baseUrlOfSut + "/api/expint/{n}/{x}")
            .then().statusCode(200)
            .body("resultAsDouble", notNullValue());

        // Test invalid n
        given().pathParam("n", "invalid").pathParam("x", 1.5)
            .when().get(baseUrlOfSut + "/api/expint/{n}/{x}")
            .then().statusCode(400);

        // Test invalid x
        given().pathParam("n", 2).pathParam("x", "invalid")
            .when().get(baseUrlOfSut + "/api/expint/{n}/{x}")
            .then().statusCode(400);
    }

    @Test
    public void testFisherEndpoint() {
        // Test valid inputs
        given().pathParam("m", 3).pathParam("n", 4).pathParam("x", 1.5)
            .when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then().statusCode(200)
            .body("resultAsDouble", notNullValue());

        // Test invalid m
        given().pathParam("m", "invalid").pathParam("n", 4).pathParam("x", 1.5)
            .when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then().statusCode(400);

        // Test invalid n
        given().pathParam("m", 3).pathParam("n", "invalid").pathParam("x", 1.5)
            .when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then().statusCode(400);

        // Test invalid x
        given().pathParam("m", 3).pathParam("n", 4).pathParam("x", "invalid")
            .when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then().statusCode(400);
    }

    @Test
    public void testGammqEndpoint() {
        // Test valid inputs
        given().pathParam("a", 2.5).pathParam("x", 1.5)
            .when().get(baseUrlOfSut + "/api/gammq/{a}/{x}")
            .then().statusCode(200)
            .body("resultAsDouble", notNullValue());

        // Test invalid a
        given().pathParam("a", "invalid").pathParam("x", 1.5)
            .when().get(baseUrlOfSut + "/api/gammq/{a}/{x}")
            .then().statusCode(400);

        // Test invalid x
        given().pathParam("a", 2.5).pathParam("x", "invalid")
            .when().get(baseUrlOfSut + "/api/gammq/{a}/{x}")
            .then().statusCode(400);
    }

    @Test
    public void testRemainderEndpoint() {
        // Test valid inputs
        given().pathParam("a", 10).pathParam("b", 3)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", notNullValue());

        // Test invalid a
        given().pathParam("a", "invalid").pathParam("b", 3)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(400);

        // Test invalid b
        given().pathParam("a", 10).pathParam("b", "invalid")
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(400);
    }

    @Test
    public void testTriangleEndpoint() {
        // Test valid inputs
        given().pathParam("a", 3).pathParam("b", 4).pathParam("c", 5)
            .when().get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
            .then().statusCode(200)
            .body("resultAsInt", notNullValue());

        // Test invalid a
        given().pathParam("a", "invalid").pathParam("b", 4).pathParam("c", 5)
            .when().get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
            .then().statusCode(400);

        // Test invalid b
        given().pathParam("a", 3).pathParam("b", "invalid").pathParam("c", 5)
            .when().get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
            .then().statusCode(400);

        // Test invalid c
        given().pathParam("a", 3).pathParam("b", 4).pathParam("c", "invalid")
            .when().get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
            .then().statusCode(400);
    }
}
