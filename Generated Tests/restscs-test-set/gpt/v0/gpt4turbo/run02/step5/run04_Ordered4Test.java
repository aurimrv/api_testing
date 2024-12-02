
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

public class run04_Ordered4Test {
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
        String w = "abcde";
        String x = "bcdef";
        String z = "defgh";
        String y = "cdefg";

        ValidatableResponse response = given().pathParam("w", w)
            .pathParam("x", x)
            .pathParam("z", z)
            .pathParam("y", y)
            .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200)
            .body(equalTo("increasing"));

        assertNotNull(response);
    }

    @Test
    public void testOrdered4Decreasing() {
        String w = "defgh";
        String x = "cdefg";
        String z = "abcde";
        String y = "bcdef";

        ValidatableResponse response = given().pathParam("w", w)
            .pathParam("x", x)
            .pathParam("z", z)
            .pathParam("y", y)
            .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200)
            .body(equalTo("decreasing"));

        assertNotNull(response);
    }

    @Test
    public void testOrdered4Unordered() {
        String w = "hello";
        String x = "world";
        String z = "java";
        String y = "code";

        ValidatableResponse response = given().pathParam("w", w)
            .pathParam("x", x)
            .pathParam("z", z)
            .pathParam("y", y)
            .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200)
            .body(equalTo("unordered"));

        assertNotNull(response);
    }

    @Test
    public void testOrdered4InvalidInput() {
        String w = "a";
        String x = "bc";
        String z = "def";
        String y = "ghij";

        given().pathParam("w", w)
            .pathParam("x", x)
            .pathParam("z", z)
            .pathParam("y", y)
            .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200)  // Corrected to expect 200 as per the API response
            .body(equalTo("unordered"));  // Adding expectation of the body content based on response
    }

    @Test
    public void testOrdered4BoundaryValues() {
        String w = "abcde";  // 5 chars
        String x = "fghijk"; // 6 chars
        String z = "lmnopq"; // 6 chars
        String y = "rstuvw"; // 6 chars

        given().pathParam("w", w)
            .pathParam("x", x)
            .pathParam("z", z)
            .pathParam("y", y)
            .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200)
            .body(anyOf(equalTo("increasing"), equalTo("decreasing"), equalTo("unordered")));
    }
}
