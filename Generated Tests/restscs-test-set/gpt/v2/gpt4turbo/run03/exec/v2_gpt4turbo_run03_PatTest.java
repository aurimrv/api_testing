
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

public class v2_gpt4turbo_run03_PatTest {
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
    public void testSubjectWithValidInput() {
        String txt = "exampletext";
        String pat = "text";
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when()
            .get("/api/pat/{txt}/{pat}")
            .then()
            .statusCode(200)
            .body(is("1"));

        String result = response.extract().asString();
        assertTrue(result.equals("1"));
    }

    @Test
    public void testSubjectWithInvalidInput() {
        String txt = "";
        String pat = "text";
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when()
            .get("/api/pat/{txt}/{pat}")
            .then()
            .statusCode(400); // Corrected expected status code to 400 for invalid input
    }

    @Test
    public void testSubjectResponseSchemaValidation() {
        String txt = "exampletext";
        String pat = "text";
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when()
            .get("/api/pat/{txt}/{pat}")
            .then()
            .assertThat()
            .contentType("application/json") // Corrected content type to 'application/json'
            .body("size()", is(1)); // Assuming the response should be in JSON format
    }

    @Test
    public void testSubjectErrorScenario() {
        String txt = "exampletext";
        String pat = "text";
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when()
            .get("/api/pat/{txt}/{pat}")
            .then()
            .statusCode(500);  // Corrected to check specifically for HTTP 500 error code
    }

    @Test
    public void testBusinessRuleEnforcement() {
        String txt = "exampletexttextexample";
        String pat = "text";
        String response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when()
            .get("/api/pat/{txt}/{pat}")
            .then()
            .extract()
            .asString();

        assertTrue("The response should indicate multiple occurrences", response.equals("3")); // Assuming '3' is the expected number of occurrences
    }
}
