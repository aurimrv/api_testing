
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
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class v2_gpt4turbo_run02_NotyPevarTest {

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

    // Test for error scenarios with invalid inputs that cause the API to return 500 Internal Server Error
    @Test
    public void testInternalServerErrorScenarios() {
        given().baseUri(baseUrlOfSut)
            .pathParam("i", "error") // Invalid parameter to force error
            .pathParam("s", "test")
            .when().get("/api/costfuns/{i}/{s}")
            .then().statusCode(500);
    }

    // Test for schema validation ensuring response conforms to expected API schema
    @Test
    public void testSchemaValidation() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("i", 28)
            .pathParam("s", "hello")
            .when().get("/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body(matchesJsonSchemaInClasspath("costfuns-schema.json"));

        // Example additional assertions based on the expected schema
        response.body("result", equalTo(0));
    }

    // Test for business rule enforcement
    @Test
    public void testBusinessRuleEnforcement() {
        // Testing the proper behavior of the POST operation
        given().baseUri(baseUrlOfSut)
            .pathParam("i", 14)
            .pathParam("s", "hello")
            .when().post("/api/costfuns/{i}/{s}")
            .then().statusCode(200)
            .body("result", equalTo(1)); // Assuming the business rule specifies the result should be '1' for this input
    }
}
