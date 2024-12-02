
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

public class v2_gpt4turbo_run03_CalcTest {
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
    public void testOperationPlus() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/calc/plus/1/2")
            .when()
            .get()
            .then()
            .statusCode(200)
            .body(is("3.0"));

        String result = response.extract().asString();
        assertEquals("3.0", result);
    }

    @Test
    public void testOperationDivideByZero() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/calc/divide/1/0")
            .when()
            .get()
            .then()
            .statusCode(500); // Assuming server returns HTTP 500 when trying to divide by zero
    }

    @Test
    public void testOperationSqrtNegative() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/calc/sqrt/-1")
            .when()
            .get()
            .then()
            .statusCode(500); // Assuming server returns HTTP 500 for sqrt of negative number
    }

    @Test
    public void testMissingOperator() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/calc//1/2")
            .when()
            .get()
            .then()
            .statusCode(404); // Endpoint not found due to missing operator
    }

    @Test
    public void testInvalidOperator() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/calc/invalidOp/1/2")
            .when()
            .get()
            .then()
            .statusCode(404); // Assuming invalid operations are not found
    }

    @Test
    public void testSchemaValidationPlus() {
        given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/calc/plus/1/2")
            .when()
            .get()
            .then()
            .assertThat()
            .body(matchesRegex("\\d+\\.\\d+")); // Checking that the response is a decimal number
    }
}
