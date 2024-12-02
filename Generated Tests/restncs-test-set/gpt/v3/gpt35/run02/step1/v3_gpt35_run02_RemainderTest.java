
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

public class v3_gpt35_run02_RemainderTest {

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
    public void testRemainderValidInputs() {
        int a = 10;
        int b = 3;
        int expectedRemainder = 1;

        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .when()
                .get("/api/remainder/" + a + "/" + b)
                .then()
                .statusCode(200);

        int actualRemainder = response.extract().path("resultAsInt");

        assertEquals(expectedRemainder, actualRemainder);
    }

    @Test
    public void testRemainderInvalidInputs() {
        int a = -5;
        int b = 0;

        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .when()
                .get("/api/remainder/" + a + "/" + b)
                .then()
                .statusCode(500); // Expecting Internal Server Error

        // Add more assertions as needed
    }

    @Test
    public void testRemainderSchemaValidation() {
        int a = 15;
        int b = 4;

        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .when()
                .get("/api/remainder/" + a + "/" + b)
                .then()
                .statusCode(200);

        response.body("resultAsInt", notNullValue());
        response.body("resultAsDouble", nullValue());
    }

    // Add more test cases for different scenarios following the above patterns

}
