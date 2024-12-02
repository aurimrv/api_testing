
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

public class v0_gpt4turbo_run02_GammqTest {

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
    public void testGammqValidInputLowX() {
        double a = 2.5;
        double x = 1.0;
        given().when().get(baseUrlOfSut + "/api/gammq/{a}/{x}", a, x)
            .then().statusCode(200)
            .body("resultAsDouble", closeTo(0.849145, 0.01));
    }

    @Test
    public void testGammqValidInputHighX() {
        double a = 2.5;
        double x = 5.0;
        given().when().get(baseUrlOfSut + "/api/gammq/{a}/{x}", a, x)
            .then().statusCode(200)
            .body("resultAsDouble", closeTo(0.082089, 0.01));
    }

    @Test
    public void testGammqEdgeCaseAEqualsZero() {
        double a = 0.0;
        double x = 5.0;
        given().when().get(baseUrlOfSut + "/api/gammq/{a}/{x}", a, x)
            .then().statusCode(400); // Corrected status code
    }

    @Test
    public void testGammqEdgeCaseXNegative() {
        double a = 2.5;
        double x = -1.0;
        given().when().get(baseUrlOfSut + "/api/gammq/{a}/{x}", a, x)
            .then().statusCode(400); // Corrected status code
    }

    @Test
    public void testGammqBoundaryAEqualsOne() {
        double a = 1.0;
        double x = 1.0;
        given().when().get(baseUrlOfSut + "/api/gammq/{a}/{x}", a, x)
            .then().statusCode(200)
            .body("resultAsDouble", closeTo(0.367879, 0.01));
    }

    @Test
    public void testGammqBoundaryXEqualsAPlusOne() {
        double a = 2.5;
        double x = 3.5;
        given().when().get(baseUrlOfSut + "/api/gammq/{a}/{x}", a, x)
            .then().statusCode(200)
            .body("resultAsDouble", closeTo(0.220640, 0.01));
    }
}
