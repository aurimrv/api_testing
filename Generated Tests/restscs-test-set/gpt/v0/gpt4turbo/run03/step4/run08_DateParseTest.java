
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

public class run08_DateParseTest {
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
            for (String month : months) {
                int monthIndex = Arrays.asList(months).indexOf(month) + 1;
                int expectedResult = 1 + monthIndex; // 1 from valid day + month index
                given().baseUri(baseUrlOfSut)
                    .basePath("/api/dateparse/{dayname}/{monthname}")
                    .pathParam("dayname", day)
                    .pathParam("monthname", month)
                    .when().get()
                    .then().statusCode(200).body(equalTo(String.valueOf(expectedResult)));
            }
        }
    }

    @Test
    public void testDateParseInvalidDayValidMonth() {
        String invalidDay = "funday";
        String[] months = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
        
        for (String month : months) {
            int monthIndex = Arrays.asList(months).indexOf(month) + 1;
            given().baseUri(baseUrlOfSut)
                .basePath("/api/dateparse/{dayname}/{monthname}")
                .pathParam("dayname", invalidDay)
                .pathParam("monthname", month)
                .when().get()
                .then().statusCode(200).body(equalTo(String.valueOf(monthIndex)));
        }
    }

    @Test
    public void testDateParseValidDayInvalidMonth() {
        String[] days = {"mon", "tue", "wed", "thur", "fri", "sat", "sun"};
        String invalidMonth = "funmonth";
        
        for (String day : days) {
            given().baseUri(baseUrlOfSut)
                .basePath("/api/dateparse/{dayname}/{monthname}")
                .pathParam("dayname", day)
                .pathParam("monthname", invalidMonth)
                .when().get()
                .then().statusCode(200).body(equalTo("1"));
        }
    }

    @Test
    public void testDateParseInvalidDayAndMonth() {
        String invalidDay = "funday";
        String invalidMonth = "funmonth";
        
        given().baseUri(baseUrlOfSut)
            .basePath("/api/dateparse/{dayname}/{monthname}")
            .pathParam("dayname", invalidDay)
            .pathParam("monthname", invalidMonth)
            .when().get()
            .then().statusCode(200).body(equalTo("0"));
    }
}
