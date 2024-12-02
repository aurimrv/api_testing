
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

public class v2_gpt4turbo_run03_CostfunsTest {
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
        int i = 5;
        String s = "baab";
        given()
            .pathParam("i", i)
            .pathParam("s", s)
            .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(is(equalTo("10")));
    }

    @Test
    public void testSubjectWithBoundaryInput() {
        int i = 666;
        String s = "aabaabba";
        given()
            .pathParam("i", i)
            .pathParam("s", s)
            .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(is(equalTo("10")));
    }

    @Test
    public void testSubjectWithInvalidInput() {
        int i = -1000;
        String s = "nonexistent";
        given()
            .pathParam("i", i)
            .pathParam("s", s)
            .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200); // API handles invalid inputs gracefully, returning 200 OK instead of 500
    }

    @Test
    public void testSubjectWithExtremeInput() {
        int i = 10000;
        String s = "baabbaab";
        given()
            .pathParam("i", i)
            .pathParam("s", s)
            .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(is(equalTo("10")));
    }

    @Test
    public void testSchemaConformance() {
        int i = 5;
        String s = "baab";
        ValidatableResponse response = given()
            .pathParam("i", i)
            .pathParam("s", s)
            .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200);

        JsonPath responseJson = response.extract().jsonPath();
        String result = responseJson.getString("$");
        assertNotNull(result);
        assertTrue(result.matches("\\d+")); // Check if result is a numeric string
    }

    @Test
    public void testBusinessRuleCompliance() {
        int i = 555;
        String s = "ababba";
        given()
            .pathParam("i", i)
            .pathParam("s", s)
            .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(is(equalTo("10"))); // Business rule as per API spec
    }
}
