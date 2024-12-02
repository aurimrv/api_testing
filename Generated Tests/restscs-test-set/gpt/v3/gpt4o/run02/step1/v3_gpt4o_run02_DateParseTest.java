
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

public class v3_gpt4o_run02_DateParseTest {

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
    public void testDateParseValidInputs() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("dayname", "mon")
            .pathParam("monthname", "jan")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("2")); // 1 (for day) + 1 (for month)
    }

    @Test
    public void testDateParseInvalidDayname() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("dayname", "invalidDay")
            .pathParam("monthname", "jan")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("1")); // only valid month
    }

    @Test
    public void testDateParseInvalidMonthname() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("dayname", "mon")
            .pathParam("monthname", "invalidMonth")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("1")); // only valid day
    }

    @Test
    public void testDateParseInvalidInputs() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("dayname", "invalidDay")
            .pathParam("monthname", "invalidMonth")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("0")); // neither day nor month is valid
    }

    @Test
    public void testDateParseMissingDayname() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("monthname", "jan")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(404); // missing path parameter
    }

    @Test
    public void testDateParseMissingMonthname() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("dayname", "mon")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(404); // missing path parameter
    }

    @Test
    public void testDateParseInternalError() {
        // Simulate internal error by manipulating the controller (if possible)
        controller.injectFault("internalError");
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("dayname", "mon")
            .pathParam("monthname", "jan")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(500); // internal server error
        controller.clearFault("internalError");
    }

    @Test
    public void testDateParseSchemaValidation() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("dayname", "mon")
            .pathParam("monthname", "jan")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200);

        response.body(matchesJsonSchemaInClasspath("dateParseResponseSchema.json"));
    }
}
