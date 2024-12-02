
// Add the package line
package org.restscs;

// Add the necessary Java imports
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

// Class Declaration
public class v0_gpt35_run02_PatTest {

    // Variables
    private static final SutHandler controller = new em.embedded.org.restscs.EmbeddedEvoMasterController();
    private static String baseUrlOfSut;

    // Setup and Teardown methods
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

    // Test Methods
    @Test
    public void testPatEndpoint() {
        String txt = "exampletext";
        String pat = "example";
        
        ValidatableResponse response = given()
            .when()
            .get(baseUrlOfSut + "/api/pat/" + txt + "/" + pat)
            .then()
            .statusCode(200);
        
        response.assertThat()
            .body(is("1")); // Assuming the expected response is "1"
    }

    @Test
    public void testReversePatEndpoint() {
        String txt = "exampletext";
        String pat = "elpmaxe"; // Reverse of "example"
        
        ValidatableResponse response = given()
            .when()
            .get(baseUrlOfSut + "/api/pat/" + txt + "/" + pat)
            .then()
            .statusCode(200);
        
        response.assertThat()
            .body(is("2")); // Assuming the expected response is "2"
    }

    // Add more test methods to cover additional scenarios based on the documentation

}
