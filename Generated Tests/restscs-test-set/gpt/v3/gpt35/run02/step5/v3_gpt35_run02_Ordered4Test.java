
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

public class v3_gpt35_run02_Ordered4Test {

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
    public void test_ordered4_withIncreasingOrder() {
        given()
            .when().get(baseUrlOfSut + "/api/ordered4/abc/def/ghi/jkl")
            .then().statusCode(200)
            .body(equalTo("increasing"));
    }

    @Test
    public void test_ordered4_withDecreasingOrder() {
        given()
            .when().get(baseUrlOfSut + "/api/ordered4/jkl/ghi/def/abc")
            .then().statusCode(200)
            .body(equalTo("decreasing"));
    }

    @Test
    public void test_ordered4_withRandomOrder() {
        given()
            .when().get(baseUrlOfSut + "/api/ordered4/abc/ghi/jkl/def")
            .then().statusCode(200)
            .body(equalTo("unordered"));
    }

    @Test
    public void test_ordered4_invalidInputs() {
        // Force 5xx status code by providing invalid inputs
        given()
            .when().get(baseUrlOfSut + "/api/ordered4/a/b/c/d")
            .then().statusCode(500);
    }

    @Test
    public void test_ordered4_missingInputs() {
        // Force 5xx status code by providing missing inputs
        given()
            .when().get(baseUrlOfSut + "/api/ordered4/abc/def")
            .then().statusCode(500);
    }
}
