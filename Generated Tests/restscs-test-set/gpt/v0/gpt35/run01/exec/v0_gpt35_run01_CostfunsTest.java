
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

public class v0_gpt35_run01_CostfunsTest {

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
    public void testCostfuns() {
        ValidatableResponse response = given()
                .when()
                .get(baseUrlOfSut + "/api/costfuns/5/baab")
                .then()
                .statusCode(200);

        response.body(equalTo("1"));

        response = given()
                .when()
                .get(baseUrlOfSut + "/api/costfuns/-500/baab")
                .then()
                .statusCode(200);

        response.body(equalTo("2"));

        response = given()
                .when()
                .get(baseUrlOfSut + "/api/costfuns/-300/baab")
                .then()
                .statusCode(200);

        response.body(equalTo("3"));

        response = given()
                .when()
                .get(baseUrlOfSut + "/api/costfuns/700/baab")
                .then()
                .statusCode(200);

        response.body(equalTo("4"));

        response = given()
                .when()
                .get(baseUrlOfSut + "/api/costfuns/600/baab")
                .then()
                .statusCode(200);

        response.body(equalTo("5"));

        response = given()
                .when()
                .get(baseUrlOfSut + "/api/costfuns/-4/baab")
                .then()
                .statusCode(200);

        response.body(equalTo("6"));

        response = given()
                .when()
                .get(baseUrlOfSut + "/api/costfuns/0/baabab")
                .then()
                .statusCode(200);

        response.body(equalTo("7"));

        response = given()
                .when()
                .get(baseUrlOfSut + "/api/costfuns/0/baabab")
                .then()
                .statusCode(200);

        response.body(equalTo("8"));

        response = given()
                .when()
                .get(baseUrlOfSut + "/api/costfuns/0/baabab")
                .then()
                .statusCode(200);

        response.body(equalTo("9"));

        response = given()
                .when()
                .get(baseUrlOfSut + "/api/costfuns/0/baabab")
                .then()
                .statusCode(200);

        response.body(equalTo("10"));
    }
}
