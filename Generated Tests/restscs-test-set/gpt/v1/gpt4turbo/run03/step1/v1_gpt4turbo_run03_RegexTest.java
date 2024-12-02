
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

public class v1_gpt4turbo_run03_RegexTest {
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
    public void testRegexWithUrl() {
        String input = "http//example.com/resource";
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", input)
            .when()
            .get("/api/pat/{txt}")
            .then()
            .statusCode(200)
            .body(equalTo("url"));

        assertEquals("url", response.extract().asString());
    }

    @Test
    public void testRegexWithDate() {
        String input = "mon01jan";
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", input)
            .when()
            .get("/api/pat/{txt}")
            .then()
            .statusCode(200)
            .body(equalTo("date"));

        assertEquals("date", response.extract().asString());
    }

    @Test
    public void testRegexWithFloatingPointExponential() {
        String input = "12.34e+56";
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", input)
            .when()
            .get("/api/pat/{txt}")
            .then()
            .statusCode(200)
            .body(equalTo("fpe"));

        assertEquals("fpe", response.extract().asString());
    }

    @Test
    public void testRegexWithNone() {
        String input = "NoMatch";
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("txt", input)
            .when()
            .get("/api/pat/{txt}")
            .then()
            .statusCode(200)
            .body(equalTo("none"));

        assertEquals("none", response.extract().asString());
    }
}
