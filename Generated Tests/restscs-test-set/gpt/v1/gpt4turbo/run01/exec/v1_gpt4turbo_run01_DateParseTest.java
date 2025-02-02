
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

public class v1_gpt4turbo_run01_DateParseTest {
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
    public void testDateParseEndpointValidInput() {
        String dayname = "mon";
        String monthname = "jan";
        given().pathParam("dayname", dayname)
               .pathParam("monthname", monthname)
               .when().get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
               .then().statusCode(200)
               .body(is("2"));
    }

    @Test
    public void testDateParseEndpointInvalidDayname() {
        String dayname = "abc";
        String monthname = "jan";
        given().pathParam("dayname", dayname)
               .pathParam("monthname", monthname)
               .when().get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
               .then().statusCode(400)
               .body(equalTo("Invalid day name"));
    }

    @Test
    public void testDateParseEndpointInvalidMonthname() {
        String dayname = "mon";
        String monthname = "xyz";
        given().pathParam("dayname", dayname)
               .pathParam("monthname", monthname)
               .when().get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
               .then().statusCode(400)
               .body(equalTo("Invalid month name"));
    }

    @Test
    public void testDateParseEndpointAllValidDaysMonths() {
        List<String> days = Arrays.asList("mon", "tue", "wed", "thur", "fri", "sat", "sun");
        List<String> months = Arrays.asList("jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec");
        for (String day : days) {
            for (String month : months) {
                int expected = 1 + months.indexOf(month) + 1;
                given().pathParam("dayname", day)
                       .pathParam("monthname", month)
                       .when().get(baseUrlOfSut + "/api/dateparse/{dayname}/{monthname}")
                       .then().statusCode(200)
                       .body(is(String.valueOf(expected)));
            }
        }
    }
}
