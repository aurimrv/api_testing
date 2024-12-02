
package org.restncs;

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

public class v3_gpt35_run01_GammqTest {

    private static final SutHandler controller = new em.embedded.org.restncs.EmbeddedEvoMasterController();
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
    public void testGammqInvalidInput() {
        given()
            .when().get(baseUrlOfSut + "/api/gammq/-1/1")
            .then()
            .assertThat()
            .statusCode(500);
    }

    @Test
    public void testGammqServerError() {
        given()
            .when().get(baseUrlOfSut + "/api/gammq/1/0")
            .then()
            .assertThat()
            .statusCode(500);
    }

    @Test
    public void testGammqSchemaValidation() {
        given()
            .when().get(baseUrlOfSut + "/api/gammq/2/1")
            .then()
            .assertThat()
            .statusCode(200)
            .body("resultAsDouble", notNullValue())
            .body("resultAsInt", notNullValue());
    }

    @Test
    public void testGammqBusinessRuleEnforcement() {
        ValidatableResponse response = given()
            .when().get(baseUrlOfSut + "/api/gammq/2/1")
            .then()
            .assertThat()
            .statusCode(200);

        double a = 2.0;
        double x = 1.0;

        double expectedResult = new org.restncs.imp.Gammq().exe(a, x);

        double actualResult = Double.parseDouble(response.extract().path("resultAsDouble").toString());

        assertEquals(expectedResult, actualResult, 0.001);
    }
}
  