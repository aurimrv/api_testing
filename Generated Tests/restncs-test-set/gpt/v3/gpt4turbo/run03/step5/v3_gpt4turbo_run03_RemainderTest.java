
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

public class v3_gpt4turbo_run03_RemainderTest {
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
    public void testRemainderPositivePositive() {
        ValidatableResponse response = given().pathParam("a", 10).pathParam("b", 3)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(1));

        int result = org.restncs.imp.Remainder.exe(10, 3);
        assertEquals(1, result);
    }

    @Test
    public void testRemainderPositiveNegative() {
        ValidatableResponse response = given().pathParam("a", 10).pathParam("b", -3)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(1));

        int result = org.restncs.imp.Remainder.exe(10, -3);
        assertEquals(1, result);
    }

    @Test
    public void testRemainderNegativePositive() {
        ValidatableResponse response = given().pathParam("a", -10).pathParam("b", 3)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(-1));

        int result = org.restncs.imp.Remainder.exe(-10, 3);
        assertEquals(-1, result);
    }

    @Test
    public void testRemainderNegativeNegative() {
        ValidatableResponse response = given().pathParam("a", -10).pathParam("b", -3)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(1));

        int result = org.restncs.imp.Remainder.exe(-10, -3);
        assertEquals(1, result);
    }

    @Test
    public void testRemainderWithZeroDivisor() {
        ValidatableResponse response = given().pathParam("a", 10).pathParam("b", 0)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200);

        try {
            int result = org.restncs.imp.Remainder.exe(10, 0);
            fail("Expected an ArithmeticException to be thrown");
        } catch (ArithmeticException e) {
            assertTrue(e.getMessage().contains("by zero"));
        }
    }

    @Test
    public void testRemainderWithZeroNumerator() {
        ValidatableResponse response = given().pathParam("a", 0).pathParam("b", 3)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(-1));

        int result = org.restncs.imp.Remainder.exe(0, 3);
        assertEquals(-1, result);
    }
}
