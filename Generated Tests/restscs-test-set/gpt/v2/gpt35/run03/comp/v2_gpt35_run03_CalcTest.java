
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

public class v2_gpt35_run03_CalcTest {

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
    public void testInvalidInputReturns500() {
        // Test with invalid inputs to force a 500 status code
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("op", "invalid")
            .pathParam("arg1", 10)
            .pathParam("arg2", 0)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .statusCode(500);
    }

    @Test
    public void testSchemaValidation() {
        // Test to validate schema conformity of the API response
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("op", "plus")
            .pathParam("arg1", 10)
            .pathParam("arg2", 5)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .body("type", equalTo("string"));
    }

    @Test
    public void testBusinessRuleEnforcement() {
        // Test to validate business rules for addition operation
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("op", "plus")
            .pathParam("arg1", 10)
            .pathParam("arg2", 5)
        .when()
            .get("/api/calc/{op}/{arg1}/{arg2}")
        .then()
            .body(equalTo("15"));
    }
}
