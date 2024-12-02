
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

public class v2_gpt4turbo_run01_FisherTest {

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
    public void testFisherInvalidInputs() {
        given().when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}", -1, -1, "notANumber")
               .then().statusCode(500);
    }

    @Test
    public void testFisherValidInputs() {
        given().when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}", 10, 10, 0.5)
               .then()
               .statusCode(200)
               .body("resultAsDouble", isA(Double.class));
    }

    @Test
    public void testFisherBoundaryValues() {
        given().when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}", 0, 0, 0)
               .then()
               .statusCode(200)
               .body("resultAsDouble", equalTo(1.0));
        
        given().when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}", Integer.MAX_VALUE, Integer.MAX_VALUE, Double.MAX_VALUE)
               .then()
               .statusCode(200)
               .body("resultAsDouble", equalTo(1.0));
    }

    @Test
    public void testFisherErrorHandling() {
        given().when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}", Integer.MAX_VALUE, Integer.MAX_VALUE, Double.POSITIVE_INFINITY)
               .then()
               .statusCode(500);
    }
}
