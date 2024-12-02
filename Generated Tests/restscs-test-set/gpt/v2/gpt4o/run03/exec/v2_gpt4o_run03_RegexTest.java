
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

public class v2_gpt4o_run03_RegexTest {

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

    // Test Error Scenarios
    @Test
    public void testErrorScenario500() {
        given().get(baseUrlOfSut + "/api/calc/div/1/0")
            .then()
            .statusCode(500);
    }

    // Test Schema Validation
    @Test
    public void testSchemaValidationCalc() {
        given().get(baseUrlOfSut + "/api/calc/add/1/2")
            .then()
            .statusCode(200)
            .body("$", hasKey("result"));
    }

    // Test Business Rule Enforcement for POST
    @Test
    public void testBusinessRuleEnforcementPost() {
        given()
            .contentType("application/json")
            .body("{\"txt\":\"http://example.com\"}")
            .when()
            .post(baseUrlOfSut + "/api/pat/")
            .then()
            .statusCode(200)
            .body(equalTo("url"));
    }

    // Test Business Rule Enforcement for PUT
    @Test
    public void testBusinessRuleEnforcementPut() {
        given()
            .contentType("application/json")
            .body("{\"txt\":\"mon12jan\"}")
            .when()
            .put(baseUrlOfSut + "/api/pat/")
            .then()
            .statusCode(200)
            .body(equalTo("date"));
    }

    // Test Business Rule Enforcement for DELETE
    @Test
    public void testBusinessRuleEnforcementDelete() {
        given()
            .when()
            .delete(baseUrlOfSut + "/api/pat/1")
            .then()
            .statusCode(204);
    }

    // Additional Tests for Robustness and Edge Cases
    @Test
    public void testEdgeCaseEmptyString() {
        given().get(baseUrlOfSut + "/api/pat/")
            .then()
            .statusCode(400);
    }

    @Test
    public void testEdgeCaseInvalidPattern() {
        given().get(baseUrlOfSut + "/api/pat/invalid_pattern")
            .then()
            .statusCode(200)
            .body(equalTo("none"));
    }

    @Test
    public void testEdgeCaseSpecialCharacters() {
        given().get(baseUrlOfSut + "/api/pat/!@#$$%^&*()")
            .then()
            .statusCode(200)
            .body(equalTo("none"));
    }

    @Test
    public void testValidURLPattern() {
        given().get(baseUrlOfSut + "/api/pat/http://example.com")
            .then()
            .statusCode(200)
            .body(equalTo("url"));
    }

    @Test
    public void testValidDatePattern() {
        given().get(baseUrlOfSut + "/api/pat/mon12jan")
            .then()
            .statusCode(200)
            .body(equalTo("date"));
    }

    @Test
    public void testValidFloatingPointPattern() {
        given().get(baseUrlOfSut + "/api/pat/12.34e+56")
            .then()
            .statusCode(200)
            .body(equalTo("fpe"));
    }
}
