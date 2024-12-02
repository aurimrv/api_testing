
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

public class v2_gpt4turbo_run01_CostfunsTest {

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
    public void testCostfunsValidInput() {
        String i = "5";
        String s = "baab";
        given()
            .pathParam("i", i)
            .pathParam("s", s)
            .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(is("10"));
    }

    @Test
    public void testCostfunsBoundaryValues() {
        Arrays.asList(-445, -444, -333, 666, 555, 667).forEach(i -> {
            given()
                .pathParam("i", i)
                .pathParam("s", "abba")
                .when()
                .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
                .then()
                .statusCode(200)
                .body(not(emptyOrNullString()));
        });
    }

    @Test
    public void testCostfunsInvalidInput() {
        given()
            .pathParam("i", "invalid")
            .pathParam("s", "baab")
            .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .statusCode(400); // Correct expectation to match API behavior
    }

    @Test
    public void testCostfunsResponseSchema() {
        given()
            .pathParam("i", 5)
            .pathParam("s", "baab")
            .when()
            .get(baseUrlOfSut + "/api/costfuns/{i}/{s}")
            .then()
            .assertThat()
            .body(matchesPattern("^\\d+$")); // Ensure the body only contains digits
    }
}
