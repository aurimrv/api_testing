
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

public class v3_gpt4turbo_run01_Ordered4Test {
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
    public void testOrderedIncreasing() {
        String w = "apple";
        String x = "banana";
        String z = "cherry";
        String y = "date";
        ValidatableResponse response = given().pathParam("w", w).pathParam("x", x).pathParam("z", z).pathParam("y", y)
            .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200)
            .body(equalTo("increasing"));
        assertNotNull(response);
    }

    @Test
    public void testOrderedDecreasing() {
        String w = "cherry";
        String x = "banana";
        String z = "apple";
        String y = "date";
        ValidatableResponse response = given().pathParam("w", w).pathParam("x", x).pathParam("z", z).pathParam("y", y)
            .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200)
            .body(equalTo("decreasing"));
        assertNotNull(response);
    }

    @Test
    public void testOrderedUnordered() {
        String w = "cherry";
        String x = "apple";
        String z = "date";
        String y = "banana";
        ValidatableResponse response = given().pathParam("w", w).pathParam("x", x).pathParam("z", z).pathParam("y", y)
            .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200)
            .body(equalTo("unordered"));
        assertNotNull(response);
    }

    @Test
    public void testOrderedInvalidInput() {
        String w = "ch";
        String x = "a";
        String z = "d";
        String y = "b";
        ValidatableResponse response = given().pathParam("w", w).pathParam("x", x).pathParam("z", z).pathParam("y", y)
            .when().get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(200)
            .body(equalTo("unordered"));
        assertNotNull(response);
    }

    @Test
    public void testServerErrorSimulation() {
        // Simulate server error by sending an unsupported HTTP method such as POST
        given().pathParam("w", "apple").pathParam("x", "banana").pathParam("z", "cherry").pathParam("y", "date")
            .when().post(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then().statusCode(anyOf(is(405), is(500))); // Checking for Method Not Allowed or Internal Server Error
    }
}
