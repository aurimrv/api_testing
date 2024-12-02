
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

public class v2_gpt4turbo_run02_TitleTest {
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
    public void testValidMaleTitle() {
        ValidatableResponse response = given().pathParam("sex", "male").pathParam("title", "mr")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200)
            .body(is("1"));
        assertTrue("Valid male title returns 1", response.extract().body().asString().equals("1"));
    }

    @Test
    public void testInvalidMaleTitle() {
        given().pathParam("sex", "male").pathParam("title", "ms")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200)
            .body(is("-1"));
    }

    @Test
    public void testValidFemaleTitle() {
        given().pathParam("sex", "female").pathParam("title", "ms")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200)
            .body(is("0"));
    }

    @Test
    public void testInvalidFemaleTitle() {
        given().pathParam("sex", "female").pathParam("title", "mr")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200)
            .body(is("-1"));
    }

    @Test
    public void testServerError() {
        given().pathParam("sex", "invalid").pathParam("title", "unknown")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(400);
    }

    @Test
    public void testSchemaCompliance() {
        given().pathParam("sex", "male").pathParam("title", "dr")
            .when().get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then().statusCode(200)
            .body(matchesPattern("^[0-2]$"));  // Expecting a single digit that is either 0, 1, or 2
    }
}
