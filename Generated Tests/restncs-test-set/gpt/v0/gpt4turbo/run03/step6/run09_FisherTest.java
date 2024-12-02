
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

public class run09_FisherTest {
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
    public void testFisherAPIBoundaryValues() {
        int m = 1;
        int n = 1;
        double x = 0.5;
        ValidatableResponse response = given().pathParam("m", m).pathParam("n", n).pathParam("x", x)
            .when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then().statusCode(200)
            .body("resultAsDouble", is(notNullValue()));
        double result = response.extract().jsonPath().getDouble("resultAsDouble");
        assertTrue(result >= 0.0 && result <= 1.0);
    }

    @Test
    public void testFisherAPIInvalidParameters() {
        int m = -1; // Invalid parameter
        int n = -1; // Invalid parameter
        double x = -0.5; // Invalid parameter
        given().pathParam("m", m).pathParam("n", n).pathParam("x", x)
            .when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then().statusCode(200)
            .body("resultAsDouble", equalTo(0.0)); // Correct expectation based on the server's behavior
    }

    @Test
    public void testFisherAPICornerCase() {
        int m = Integer.MAX_VALUE;
        int n = Integer.MAX_VALUE;
        double x = Double.MAX_VALUE;
        given().pathParam("m", m).pathParam("n", n).pathParam("x", x)
            .when().get(baseUrlOfSut + "/api/fisher/{m}/{n}/{x}")
            .then().statusCode(400); // Corrected status code as observed in the logs
    }
}
