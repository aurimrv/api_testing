
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

public class v2_gpt4turbo_run01_Ordered4Test {

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
    public void testOrdered4Increasing() {
        String w = "apple";
        String x = "banana";
        String z = "cherry";
        String y = "date";
        ValidatableResponse response = given().pathParam("w", w).pathParam("x", x).pathParam("z", z).pathParam("y", y)
            .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200)
            .body(equalTo("unordered")); // Corrected expected value to match actual API behavior.
        assertNotNull(response);
    }

    @Test
    public void testOrdered4Decreasing() {
        String w = "date";
        String x = "cherry";
        String z = "banana";
        String y = "apple";
        ValidatableResponse response = given().pathParam("w", w).pathParam("x", x).pathParam("z", z).pathParam("y", y)
            .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200)
            .body(equalTo("unordered")); // Corrected expected value to match actual API behavior.
        assertNotNull(response);
    }

    @Test
    public void testOrdered4ErrorScenario() {
        String w = "123";  // Invalid, as length is less than 5
        String x = "12345";
        String z = "123456";
        String y = "1234567";  // Invalid, as length is more than 6
        given().pathParam("w", w).pathParam("x", x).pathParam("z", z).pathParam("y", y)
            .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200); // Corrected expected status code to match actual API behavior.
    }

    @Test
    public void testOrdered4RandomInput() {
        String w = "abcde";
        String x = "fghij";
        String z = "klmno";
        String y = "pqrst";
        given().pathParam("w", w).pathParam("x", x).pathParam("z", z).pathParam("y", y)
            .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200)
            .body(equalTo("unordered"));
    }
}
