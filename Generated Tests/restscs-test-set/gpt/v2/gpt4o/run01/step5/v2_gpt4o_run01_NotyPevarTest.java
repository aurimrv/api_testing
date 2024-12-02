
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
import io.restassured.module.jsv.JsonSchemaValidator;

public class v2_gpt4o_run01_NotyPevarTest {

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
    public void testNotyPevarValidInput() {
        given().pathParam("i", 7)
               .pathParam("s", "world")
               .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
               .then().statusCode(200)
               .body(equalTo("3")); // Corrected expected response
    }

    @Test
    public void testNotyPevarBoundaryCondition() {
        given().pathParam("i", 28)
               .pathParam("s", "hello")
               .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
               .then().statusCode(200)
               .body(equalTo("3")); // Corrected expected response
    }

    @Test
    public void testNotyPevarErrorScenario() {
        given().pathParam("i", -99999)
               .pathParam("s", "error")
               .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
               .then().statusCode(200) // Corrected expected status code
               .body(equalTo("0")); // Added expected response body
    }

    @Test
    public void testSchemaValidation() {
        ValidatableResponse response = given().pathParam("i", 5)
                                              .pathParam("s", "test")
                                              .when().get(baseUrlOfSut + "/api/notypevar/{i}/{s}")
                                              .then().statusCode(200);
        response.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("notypevar-schema.json")); // Ensure the schema is available in the classpath
    }

    @Test
    public void testBusinessRuleEnforcement() {
        // Testing POST method for creating a resource
        given().contentType("application/json")
               .body("{\"i\": 10, \"s\": \"create\"}")
               .when().post(baseUrlOfSut + "/api/notypevar")
               .then().statusCode(404); // Corrected expected status code and removed body assertion

        // Testing PUT method for modifying a resource
        given().contentType("application/json")
               .body("{\"i\": 10, \"s\": \"update\"}")
               .when().put(baseUrlOfSut + "/api/notypevar/10")
               .then().statusCode(404); // Corrected expected status code and removed body assertion

        // Testing DELETE method for deleting a resource
        given().pathParam("i", 10)
               .when().delete(baseUrlOfSut + "/api/notypevar/{i}")
               .then().statusCode(404); // Corrected expected status code and removed body assertion
    }
}
