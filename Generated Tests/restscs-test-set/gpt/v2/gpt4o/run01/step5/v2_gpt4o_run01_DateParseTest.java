
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

public class v2_gpt4o_run01_DateParseTest {

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
        ValidatableResponse response = given()
            .pathParam("dayname", "mon")
            .pathParam("monthname", "jan")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testDateParseInvalidDay() {
        ValidatableResponse response = given()
            .pathParam("dayname", "invalid")
            .pathParam("monthname", "jan")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testDateParseInvalidMonth() {
        ValidatableResponse response = given()
            .pathParam("dayname", "mon")
            .pathParam("monthname", "invalid")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testDateParseInvalidInputs() {
        ValidatableResponse response = given()
            .pathParam("dayname", "invalid")
            .pathParam("monthname", "invalid")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testDateParseEmptyInputs() {
        ValidatableResponse response = given()
            .pathParam("dayname", " ")
            .pathParam("monthname", " ")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testDateParseInternalServerError() {
        ValidatableResponse response = given()
            .pathParam("dayname", "mon")
            .pathParam("monthname", "")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(500);
    }

    @Test
    public void testDateParseSchemaValidation() {
        ValidatableResponse response = given()
            .pathParam("dayname", "mon")
            .pathParam("monthname", "jan")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testDateParseBusinessRuleValid() {
        ValidatableResponse response = given()
            .pathParam("dayname", "wed")
            .pathParam("monthname", "mar")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(equalTo("4"));
    }

    @Test
    public void testDateParseBusinessRuleInvalid() {
        ValidatableResponse response = given()
            .pathParam("dayname", "mon")
            .pathParam("monthname", "null")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(equalTo("1"));
    }
}
