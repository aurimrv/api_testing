
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

public class v2_gpt4turbo_run01_RemainderTest {

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
    public void testRemainderValidInput() {
        given().when().get(baseUrlOfSut + "/api/remainder/{a}/{b}", 10, 3)
            .then()
            .statusCode(200)
            .body("resultAsInt", equalTo(1));
    }

    @Test
    public void testRemainderZeroDivisor() {
        given().when().get(baseUrlOfSut + "/api/remainder/{a}/{b}", 10, 0)
            .then()
            .statusCode(500);
    }

    @Test
    public void testRemainderNegativeNumbers() {
        given().when().get(baseUrlOfSut + "/api/remainder/{a}/{b}", -10, -3)
            .then()
            .statusCode(200)
            .body("resultAsInt", equalTo(-1));
    }

    @Test
    public void testRemainderZeroNumerator() {
        given().when().get(baseUrlOfSut + "/api/remainder/{a}/{b}", 0, 5)
            .then()
            .statusCode(200)
            .body("resultAsInt", equalTo(0));
    }

    @Test
    public void testRemainderResponseSchema() {
        ValidatableResponse response = given().when().get(baseUrlOfSut + "/api/remainder/{a}/{b}", 10, 4).then().statusCode(200);
        String responseString = response.extract().asString();
        JsonPath jsonPath = new JsonPath(responseString);
        double resultAsDouble = jsonPath.getDouble("resultAsDouble");
        int resultAsInt = jsonPath.getInt("resultAsInt");
        assertTrue(resultAsDouble == (double) resultAsInt);
    }

    @Test
    public void testRemainderBusinessRule() {
        // Ensuring that API adheres to the business rule that remainder when b is zero should not be calculated
        given().when().get(baseUrlOfSut + "/api/remainder/{a}/{b}", 10, 0)
            .then()
            .statusCode(500); // Assuming server handles zero division with error 500
    }
}
