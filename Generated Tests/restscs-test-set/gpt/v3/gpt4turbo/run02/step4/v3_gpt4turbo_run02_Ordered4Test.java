
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

public class v3_gpt4turbo_run02_Ordered4Test {

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
    public void testOrdered4IncreasingOrder() {
        String w = "apple";
        String x = "banana";
        String z = "cherry";
        String y = "date";
        ValidatableResponse response = given().pathParam("w", w)
                                              .pathParam("x", x)
                                              .pathParam("z", z)
                                              .pathParam("y", y)
                                              .when()
                                              .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
                                              .then()
                                              .statusCode(200)
                                              .body(is(equalTo("increasing")));

        assertEquals("increasing", response.extract().asString());
    }

    @Test
    public void testOrdered4DecreasingOrder() {
        String w = "cherry";
        String x = "banana";
        String z = "apple";
        String y = "date";
        ValidatableResponse response = given().pathParam("w", w)
                                              .pathParam("x", x)
                                              .pathParam("z", z)
                                              .pathParam("y", y)
                                              .when()
                                              .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
                                              .then()
                                              .statusCode(200)
                                              .body(is(equalTo("decreasing")));

        assertEquals("decreasing", response.extract().asString());
    }

    @Test
    public void testOrdered4Unordered() {
        String w = "banana";
        String x = "apple";
        String z = "date";
        String y = "cherry";
        ValidatableResponse response = given().pathParam("w", w)
                                              .pathParam("x", x)
                                              .pathParam("z", z)
                                              .pathParam("y", y)
                                              .when()
                                              .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
                                              .then()
                                              .statusCode(200)
                                              .body(is(equalTo("unordered")));

        assertEquals("unordered", response.extract().asString());
    }

    @Test
    public void testOrdered4ErrorScenario() {
        // Simulating an error scenario by providing an invalid type for 'z'
        String w = "apple";
        String x = "banana";
        String z = "12345"; // numeric value in string type parameter
        String y = "date";
        given().pathParam("w", w)
               .pathParam("x", x)
               .pathParam("z", z)
               .pathParam("y", y)
               .when()
               .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
               .then()
               .statusCode(anyOf(is(400), is(500))); // Expecting either 400 Bad Request or 500 Internal Server Error
    }
}
