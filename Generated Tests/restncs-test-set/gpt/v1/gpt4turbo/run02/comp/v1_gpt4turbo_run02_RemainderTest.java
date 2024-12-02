
package org.restncs;

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

public class v1_gpt4turbo_run02_RemainderTest {

    private static final SutHandler controller = new em.embedded.org.restncs.EmbeddedEvoMasterController();
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
    public void testRemainderPositiveDivisor() {
        int a = 10;
        int b = 3;
        ValidatableResponse response = given().pathParam("a", a).pathParam("b", b)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200);

        int expectedRemainder = 1; // Manually calculated
        response.body("resultAsInt", equalTo(expectedRemainder));
    }

    @Test
    public void testRemainderNegativeDivisor() {
        int a = 10;
        int b = -3;
        ValidatableResponse response = given().pathParam("a", a).pathParam("b", b)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200);

        int expectedRemainder = -2; // Manually calculated
        response.body("resultAsInt", equalTo(expectedRemainder));
    }

    @Test
    public void testRemainderZeroDividend() {
        int a = 0;
        int b = 5;
        ValidatableResponse response = given().pathParam("a", a).pathParam("b", b)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200);

        int expectedRemainder = 0; // Since a is zero
        response.body("resultAsInt", equalTo(expectedRemainder));
    }

    @Test
    public void testRemainderZeroDivisor() {
        int a = 10;
        int b = 0;
        given().pathParam("a", a).pathParam("b", b)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(404); // Expect Not Found or similar error
    }
}
