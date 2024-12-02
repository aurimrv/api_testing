
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

public class run08_RemainderTest {
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
    public void testRemainderBothPositive() {
        int a = 25;
        int b = 3;
        ValidatableResponse response = given().pathParam("a", a).pathParam("b", b)
            .get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", is(25 % 3));

        int result = response.extract().path("resultAsInt");
        assertEquals(1, result);
    }

    @Test
    public void testRemainderANegative() {
        int a = -25;
        int b = 4;
        given().pathParam("a", a).pathParam("b", b)
            .get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", is(-25 % 4));
    }

    @Test
    public void testRemainderBNegative() {
        int a = 25;
        int b = -4;
        given().pathParam("a", a).pathParam("b", b)
            .get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", is(25 % -4));
    }

    @Test
    public void testRemainderAZero() {
        int a = 0;
        int b = 5;
        given().pathParam("a", a).pathParam("b", b)
            .get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", is(0 % 5));
    }

    @Test
    public void testRemainderBZero() {
        int a = 5;
        int b = 0;
        given().pathParam("a", a).pathParam("b", b)
            .get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(404); // Assumes the API would return 404 for division by zero
    }

    @Test
    public void testRemainderBothZero() {
        int a = 0;
        int b = 0;
        given().pathParam("a", a).pathParam("b", b)
            .get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(404); // Assumes the API would return 404 for division by zero
    }
}
