
package org.restscs;

import  org.junit.AfterClass;
import  org.junit.BeforeClass;
import  org.junit.Before;
import  org.junit.Test;
import static org.junit.Assert.*;
import  java.util.Map;
import  java.util.List;
import static org.evomaster.client.java.controller.api.EMTestUtils.*;
import  org.evomaster.client.java.controller.SutHandler;
import  io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import  io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.*;
import  io.restassured.config.JsonConfig;
import  io.restassured.path.json.config.JsonPathConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static org.evomaster.client.java.controller.contentMatchers.NumberMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.StringMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.SubStringMatcher.*;
import static org.evomaster.client.java.controller.expect.ExpectationHandler.expectationHandler;
import  org.evomaster.client.java.controller.expect.ExpectationHandler;
import  io.restassured.path.json.JsonPath;
import  java.util.Arrays;

public class v0_gpt4o_run02_DateParseTest {

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
    public void testDateParseValidDaysAndMonths() {
        String[] days = {"mon", "tue", "wed", "thur", "fri", "sat", "sun"};
        String[] months = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};

        for (String day : days) {
            for (int i = 0; i < months.length; i++) {
                String month = months[i];
                int expectedValue = 1 + i + 1;
                given()
                    .baseUri(baseUrlOfSut)
                    .pathParam("dayname", day)
                    .pathParam("monthname", month)
                .when()
                    .get("/api/dateparse/{dayname}/{monthname}")
                .then()
                    .statusCode(200)
                    .body(equalTo(String.valueOf(expectedValue)));
            }
        }
    }

    @Test
    public void testDateParseInvalidDay() {
        String[] invalidDays = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday", "abc"};
        String month = "jan";

        for (String day : invalidDays) {
            given()
                .baseUri(baseUrlOfSut)
                .pathParam("dayname", day)
                .pathParam("monthname", month)
            .when()
                .get("/api/dateparse/{dayname}/{monthname}")
            .then()
                .statusCode(200)
                .body(equalTo("1")); // Only month is valid
        }
    }

    @Test
    public void testDateParseInvalidMonth() {
        String day = "mon";
        String[] invalidMonths = {"january", "february", "march", "april", "mayy", "junne", "july", "august", "september", "october", "november", "december", "xyz"};

        for (String month : invalidMonths) {
            given()
                .baseUri(baseUrlOfSut)
                .pathParam("dayname", day)
                .pathParam("monthname", month)
            .when()
                .get("/api/dateparse/{dayname}/{monthname}")
            .then()
                .statusCode(200)
                .body(equalTo("1")); // Only day is valid
        }
    }

    @Test
    public void testDateParseInvalidDayAndMonth() {
        String[] invalidDays = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday", "abc"};
        String[] invalidMonths = {"january", "february", "march", "april", "mayy", "junne", "july", "august", "september", "october", "november", "december", "xyz"};

        for (String day : invalidDays) {
            for (String month : invalidMonths) {
                given()
                    .baseUri(baseUrlOfSut)
                    .pathParam("dayname", day)
                    .pathParam("monthname", month)
                .when()
                    .get("/api/dateparse/{dayname}/{monthname}")
                .then()
                    .statusCode(200)
                    .body(equalTo("0")); // Neither day nor month is valid
            }
        }
    }

    @Test
    public void testDateParseEmptyDayAndMonth() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("dayname", " ")
            .pathParam("monthname", " ")
        .when()
            .get("/api/dateparse/{dayname}/{monthname}")
        .then()
            .statusCode(200)
            .body(equalTo("0")); // Neither day nor month is valid
    }

    @Test
    public void testDateParseInvalidAndEmptyDayAndMonth() {
        String[] invalidDays = {" ", "abc"};
        String[] invalidMonths = {" ", "xyz"};

        for (String day : invalidDays) {
            for (String month : invalidMonths) {
                given()
                    .baseUri(baseUrlOfSut)
                    .pathParam("dayname", day)
                    .pathParam("monthname", month)
                .when()
                    .get("/api/dateparse/{dayname}/{monthname}")
                .then()
                    .statusCode(200)
                    .body(equalTo("0")); // Neither day nor month is valid
            }
        }
    }
}
