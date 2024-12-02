
// Instruction 1: Add this package line to generated test code file:
package org.restscs;

// Instruction 2: Add the following Java imports to generated test code file:
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

// Instruction 3. Class Declaration: Create the class with the following declaration:
public class v1_gpt35_run03_Ordered4Test {

    // Instruction 4: Create these variables in the generated test code:
    private static final SutHandler controller = new em.embedded.org.restscs.EmbeddedEvoMasterController();
    private static String baseUrlOfSut;

    // Instruction 5: Include the following setup and teardown methods in generated test code file:
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
    public void testOrdered4Endpoint() {
        given()
                .when()
                .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
                .then()
                .statusCode(200)
                .body(equalTo("increasing"));
    }

    @Test
    public void testOrdered4EndpointWithDecreasingOrder() {
        given()
                .pathParam("w", "100")
                .pathParam("x", "50")
                .pathParam("z", "30")
                .pathParam("y", "10")
                .when()
                .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
                .then()
                .statusCode(200)
                .body(equalTo("decreasing"));
    }
}
