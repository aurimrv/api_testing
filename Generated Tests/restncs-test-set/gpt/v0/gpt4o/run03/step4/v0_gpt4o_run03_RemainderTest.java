
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

public class v0_gpt4o_run03_RemainderTest {

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
    public void testRemainder() {
        // Test case for a = 0
        given().pathParam("a", 0).pathParam("b", 5)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(-1));

        // Test case for b = 0
        given().pathParam("a", 5).pathParam("b", 0)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(-1));

        // Test case for a > 0 and b > 0
        given().pathParam("a", 10).pathParam("b", 3)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(1));

        // Test case for a > 0 and b < 0
        given().pathParam("a", 10).pathParam("b", -3)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(1));

        // Test case for a < 0 and b > 0
        given().pathParam("a", -10).pathParam("b", 3)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(2));

        // Test case for a < 0 and b < 0
        given().pathParam("a", -10).pathParam("b", -3)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(2));

        // Test case for a = b
        given().pathParam("a", 5).pathParam("b", 5)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(0));

        // Test case for a = -b
        given().pathParam("a", 5).pathParam("b", -5)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(0));

        // Test case for a < b
        given().pathParam("a", 3).pathParam("b", 5)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(3));

        // Test case for a > b
        given().pathParam("a", 5).pathParam("b", 3)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(2));

        // Test case for a < 0 and b = 1
        given().pathParam("a", -10).pathParam("b", 1)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(0));

        // Test case for a > 0 and b = -1
        given().pathParam("a", 10).pathParam("b", -1)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(200)
            .body("resultAsInt", equalTo(0));
    }

    @Test
    public void testRemainderInvalidInputs() {
        // Test case for invalid input (non-integer)
        given().pathParam("a", "invalid").pathParam("b", 5)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(400);

        given().pathParam("a", 5).pathParam("b", "invalid")
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(400);
    }

    @Test
    public void testRemainderNotFound() {
        // Test case for non-existing endpoint
        given().pathParam("a", 5).pathParam("b", 5)
            .when().get(baseUrlOfSut + "/api/nonexistent/{a}/{b}")
            .then().statusCode(404);
    }

    @Test
    public void testUnauthorizedAccess() {
        // Test case for unauthorized access
        given().auth().preemptive().basic("invalidUser", "invalidPassword")
            .pathParam("a", 5).pathParam("b", 5)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(401);
    }

    @Test
    public void testForbiddenAccess() {
        // Test case for forbidden access
        given().auth().preemptive().basic("user", "userPassword")
            .pathParam("a", 5).pathParam("b", 5)
            .when().get(baseUrlOfSut + "/api/remainder/{a}/{b}")
            .then().statusCode(403);
    }
}
