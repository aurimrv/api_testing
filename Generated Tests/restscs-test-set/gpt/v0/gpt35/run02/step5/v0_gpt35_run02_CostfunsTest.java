
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

public class v0_gpt35_run02_CostfunsTest {

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

    // Test cases for costfunsUsingGET endpoint
    @Test
    public void testCostfunsUsingGET() {
        int i = 5;
        String s = "baab";
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/" + i + "/" + s)
            .then()
            .statusCode(200)
            .body(equalTo("1")); // Expected result for i = 5

        i = -500;
        s = "xyz";
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/" + i + "/" + s)
            .then()
            .statusCode(200)
            .body(equalTo("2")); // Expected result for i < -444

        i = -333;
        s = "abc";
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/" + i + "/" + s)
            .then()
            .statusCode(200)
            .body(equalTo("3")); // Expected result for i <= -333

        i = 700;
        s = "def";
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/" + i + "/" + s)
            .then()
            .statusCode(200)
            .body(equalTo("4")); // Expected result for i > 666

        i = 555;
        s = "xyz";
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/" + i + "/" + s)
            .then()
            .statusCode(200)
            .body(equalTo("5")); // Expected result for i >= 555

        i = -4;
        s = "ab";
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/" + i + "/" + s)
            .then()
            .statusCode(200)
            .body(equalTo("6")); // Expected result for i != -4

        i = 0;
        s = "baab";
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/" + i + "/" + s)
            .then()
            .statusCode(200)
            .body(equalTo("7")); // Expected result for s.equals("ba" + "ab")

        s = "baba";
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/" + i + "/" + s)
            .then()
            .statusCode(200)
            .body(equalTo("8")); // Expected result for s.compareTo("abab" + "ab") > 0

        s = "abab";
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/" + i + "/" + s)
            .then()
            .statusCode(200)
            .body(equalTo("9")); // Expected result for s.compareTo("abab" + "ab") >= 0

        s = "abcd";
        given()
            .when()
            .get(baseUrlOfSut + "/api/costfuns/" + i + "/" + s)
            .then()
            .statusCode(200)
            .body(equalTo("10")); // Expected result for s != "abab"
    }
}
   