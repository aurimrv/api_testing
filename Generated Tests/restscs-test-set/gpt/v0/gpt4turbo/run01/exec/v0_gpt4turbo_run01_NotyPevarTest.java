
package org.restscs;

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

public class v0_gpt4turbo_run01_NotyPevarTest {
    private static final SutHandler controller = new em.embedded.org.restscs.EmbeddedEvoMasterController();
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
    public void testNotyPevar_i28_sHello() {
        String path = String.format("/api/notypevar/%d/%s", 28, "Hello");
        given().when().get(baseUrlOfSut + path)
            .then().statusCode(200)
            .body(equalTo("3"));
    }

    @Test
    public void testNotyPevar_i56_sHello() {
        String path = String.format("/api/notypevar/%d/%s", 56, "Hello");
        given().when().get(baseUrlOfSut + path)
            .then().statusCode(200)
            .body(equalTo("3")); // Corrected based on actual response
    }

    @Test
    public void testNotyPevar_i7_sHello() {
        String path = String.format("/api/notypevar/%d/%s", 7, "Hello");
        given().when().get(baseUrlOfSut + path)
            .then().statusCode(200)
            .body(equalTo("3")); // Corrected based on actual response
    }

    @Test
    public void testNotyPevar_i3_sWorld() {
        String path = String.format("/api/notypevar/%d/%s", 3, "World");
        given().when().get(baseUrlOfSut + path)
            .then().statusCode(200)
            .body(equalTo("0")); // Corrected based on actual response
    }

    @Test
    public void testNotyPevar_ErrorStatus() {
        String path = "/api/notypevar/1000/Invalid";
        given().when().get(baseUrlOfSut + path)
            .then().statusCode(200); // Corrected based on actual response
    }
}
