
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

public class run08_ExpintTest {
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
    public void testExpintValidInput() {
        given().when().get(baseUrlOfSut + "/api/expint/{n}/{x}", 2, 5.0)
               .then().statusCode(200)
               .body("resultAsDouble", isA(Double.class));
    }

    @Test
    public void testExpintNZeroXZero() {
        given().when().get(baseUrlOfSut + "/api/expint/{n}/{x}", 0, 0.0)
               .then().statusCode(404); // Assuming 404 for invalid input as per similar patterns
    }

    @Test
    public void testExpintNOneXZero() {
        given().when().get(baseUrlOfSut + "/api/expint/{n}/{x}", 1, 0.0)
               .then().statusCode(404); // Assuming 404 for invalid input as per similar patterns
    }

    @Test
    public void testExpintXNegative() {
        given().when().get(baseUrlOfSut + "/api/expint/{n}/{x}", 1, -1.0)
               .then().statusCode(404); // Assuming 404 for negative x
    }

    @Test
    public void testExpintNNegative() {
        given().when().get(baseUrlOfSut + "/api/expint/{n}/{x}", -1, 5.0)
               .then().statusCode(404); // Assuming 404 for negative n
    }

    @Test
    public void testExpintLargeX() {
        given().when().get(baseUrlOfSut + "/api/expint/{n}/{x}", 2, 100.0)
               .then().statusCode(200)
               .body("resultAsDouble", isA(Double.class));
    }

    @Test
    public void testExpintBoundaryX() {
        given().when().get(baseUrlOfSut + "/api/expint/{n}/{x}", 2, 1.0e-30)
               .then().statusCode(200)
               .body("resultAsDouble", isA(Double.class));
    }
}
