
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

public class v1_gpt4turbo_run03_GammqTest {
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
    public void testGammqUsingGet200() {
        double a = 2.5; // Sample valid input
        double x = 5.0; // Sample valid input
        given().baseUri(baseUrlOfSut)
            .basePath("/api/gammq/{a}/{x}")
            .pathParam("a", a)
            .pathParam("x", x)
            .when().get()
            .then().statusCode(200)
            .body("resultAsDouble", is(notNullValue()), "resultAsDouble", closeTo(0.075, 0.001)); // Adjusted expected value and tolerance
    }

    @Test
    public void testGammqUsingGetInvalidInput() {
        double a = -1.0; // Invalid input
        double x = -1.0; // Invalid input
        given().baseUri(baseUrlOfSut)
            .basePath("/api/gammq/{a}/{x}")
            .pathParam("a", a)
            .pathParam("x", x)
            .when().get()
            .then().statusCode(400); // Corrected expected status code
    }

    @Test
    public void testGammqUsingGetBoundaryInput() {
        double a = 0.0; // Boundary input
        double x = 0.0; // Boundary input
        given().baseUri(baseUrlOfSut)
            .basePath("/api/gammq/{a}/{x}")
            .pathParam("a", a)
            .pathParam("x", x)
            .when().get()
            .then().statusCode(400) // Corrected expected status code
            .body("resultAsDouble", is(nullValue())); // Handling the response for boundary input
    }
}
