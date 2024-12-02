
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

public class v3_gpt4turbo_run02_CostfunsTest {

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
    public void testSubject_withValidInputs() {
        int i = 5;
        String s = "baab";
        ValidatableResponse response = given()
            .basePath(baseUrlOfSut)
            .pathParam("i", i)
            .pathParam("s", s)
            .when()
            .get("/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testSubject_withNegativeIndex() {
        int i = -445;
        String s = "baab";
        ValidatableResponse response = given()
            .basePath(baseUrlOfSut)
            .pathParam("i", i)
            .pathParam("s", s)
            .when()
            .get("/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testSubject_withHighIndex() {
        int i = 667;
        String s = "baab";
        ValidatableResponse response = given()
            .basePath(baseUrlOfSut)
            .pathParam("i", i)
            .pathParam("s", s)
            .when()
            .get("/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("4"));
    }

    @Test
    public void testSubject_withStringComparison() {
        int i = 0;
        String s = "ababba";
        ValidatableResponse response = given()
            .basePath(baseUrlOfSut)
            .pathParam("i", i)
            .pathParam("s", s)
            .when()
            .get("/api/costfuns/{i}/{s}")
            .then()
            .statusCode(200)
            .body(equalTo("8"));
    }

    @Test
    public void testSubject_internalServerErrorSimulation() {
        int i = Integer.MAX_VALUE;
        String s = null; // Simulating a scenario where input may lead to a server error
        given()
            .basePath(baseUrlOfSut)
            .pathParam("i", i)
            .pathParam("s", s)
            .when()
            .get("/api/costfuns/{i}/{s}")
            .then()
            .statusCode(500); // Assuming that this input causes an internal server error
    }
}
