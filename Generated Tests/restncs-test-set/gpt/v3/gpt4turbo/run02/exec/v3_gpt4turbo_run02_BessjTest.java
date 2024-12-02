
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

public class v3_gpt4turbo_run02_BessjTest {
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
    public void test_bessjValidInput() {
        given().baseUri(baseUrlOfSut)
               .basePath("/api/bessj/{n}/{x}")
               .pathParam("n", 3)
               .pathParam("x", 5.0)
               .when().get()
               .then().statusCode(200)
               .body("resultAsDouble", notNullValue());
    }

    @Test
    public void test_bessjInvalidInputN() {
        given().baseUri(baseUrlOfSut)
               .basePath("/api/bessj/{n}/{x}")
               .pathParam("n", 1)
               .pathParam("x", 5.0)
               .when().get()
               .then().statusCode(400); // Updated to correct status code based on error message
    }

    @Test
    public void test_bessjZeroX() {
        given().baseUri(baseUrlOfSut)
               .basePath("/api/bessj/{n}/{x}")
               .pathParam("n", 3)
               .pathParam("x", 0.0)
               .when().get()
               .then().statusCode(200)
               .body("resultAsDouble", equalTo(0.0));
    }

    @Test
    public void test_bessjNegativeX() {
        given().baseUri(baseUrlOfSut)
               .basePath("/api/bessj/{n}/{x}")
               .pathParam("n", 3)
               .pathParam("x", -5.0)
               .when().get()
               .then().statusCode(200)
               .body("resultAsDouble", notNullValue());
    }

    @Test
    public void test_bessjHighX() {
        given().baseUri(baseUrlOfSut)
               .basePath("/api/bessj/{n}/{x}")
               .pathParam("n", 3)
               .pathParam("x", 200.0)
               .when().get()
               .then().statusCode(200)
               .body("resultAsDouble", notNullValue());
    }
}
