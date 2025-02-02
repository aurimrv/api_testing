
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

public class v3_gpt4turbo_run02_DateParseTest {

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
    public void testDateParseValidInput() {
        given().pathParam("dayname", "mon").pathParam("monthname", "jan")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(is("2"));
    }

    @Test
    public void testDateParseInvalidDay() {
        given().pathParam("dayname", "noday").pathParam("monthname", "jan")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(404);
    }

    @Test
    public void testDateParseInvalidMonth() {
        given().pathParam("dayname", "mon").pathParam("monthname", "noMonth")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(404);
    }

    @Test
    public void testDateParseEmptyParameters() {
        given().pathParam("dayname", "").pathParam("monthname", "")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(400); // Assuming API handles empty parameters with 400 Bad Request
    }

    @Test
    public void testDateParseInternalErrorSimulation() {
        given().pathParam("dayname", "mon").pathParam("monthname", "jan")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}?error=force500")
            .then()
            .statusCode(500); // Assuming there's a way to force internal server error for testing
    }
}
