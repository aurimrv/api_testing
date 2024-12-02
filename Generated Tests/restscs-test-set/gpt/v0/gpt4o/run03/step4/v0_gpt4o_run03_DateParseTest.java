
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

public class v0_gpt4o_run03_DateParseTest {
    
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
    public void testDateParseEndpoint() {
        // Test for all valid day and month combinations
        String[] days = {"mon", "tue", "wed", "thur", "fri", "sat", "sun"};
        String[] months = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
        
        for (String day : days) {
            for (String month : months) {
                ValidatableResponse response = given()
                    .pathParam("dayname", day)
                    .pathParam("monthname", month)
                    .when()
                    .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
                    .then()
                    .statusCode(200)
                    .body(equalTo(getExpectedResult(day, month)));
            }
        }

        // Test for invalid day and month combinations
        ValidatableResponse invalidResponse = given()
            .pathParam("dayname", "invalidDay")
            .pathParam("monthname", "invalidMonth")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(equalTo("0"));

        // Test for valid day but invalid month
        ValidatableResponse validDayInvalidMonth = given()
            .pathParam("dayname", "mon")
            .pathParam("monthname", "invalidMonth")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(equalTo("1"));

        // Test for invalid day but valid month
        ValidatableResponse invalidDayValidMonth = given()
            .pathParam("dayname", "invalidDay")
            .pathParam("monthname", "jan")
            .when()
            .get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
            .then()
            .statusCode(200)
            .body(equalTo("1"));
    }

    private String getExpectedResult(String day, String month) {
        int result = 0;

        if (Arrays.asList("mon", "tue", "wed", "thur", "fri", "sat", "sun").contains(day)) {
            result = 1;
        }

        switch (month) {
            case "jan":
                result += 1;
                break;
            case "feb":
                result += 2;
                break;
            case "mar":
                result += 3;
                break;
            case "apr":
                result += 4;
                break;
            case "may":
                result += 5;
                break;
            case "jun":
                result += 6;
                break;
            case "jul":
                result += 7;
                break;
            case "aug":
                result += 8;
                break;
            case "sep":
                result += 9;
                break;
            case "oct":
                result += 10;
                break;
            case "nov":
                result += 11;
                break;
            case "dec":
                result += 12;
                break;
        }

        return "" + result;
    }
}
