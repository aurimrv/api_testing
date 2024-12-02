
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

public class v2_gpt4o_run01_TitleTest {

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
    public void testSchemaValidation() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .get("/api/title/male/mr")
            .then()
            .statusCode(200);

        response.body("size()", equalTo(1));
        response.body("[0]", equalTo("1"));
    }

    @Test
    public void testErrorScenario500() {
        given()
            .baseUri(baseUrlOfSut)
            .get("/api/title/unknown/unknown")
            .then()
            .statusCode(500);
    }

    @Test
    public void testBusinessRulesMaleTitles() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .get("/api/title/male/mr")
            .then()
            .statusCode(200);

        response.body(equalTo("1"));

        response = given()
            .baseUri(baseUrlOfSut)
            .get("/api/title/male/dr")
            .then()
            .statusCode(200);

        response.body(equalTo("1"));
    }

    @Test
    public void testBusinessRulesFemaleTitles() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .get("/api/title/female/mrs")
            .then()
            .statusCode(200);

        response.body(equalTo("0"));

        response = given()
            .baseUri(baseUrlOfSut)
            .get("/api/title/female/ms")
            .then()
            .statusCode(200);

        response.body(equalTo("0"));
    }

    @Test
    public void testBusinessRulesNeutralTitles() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .get("/api/title/none/dr")
            .then()
            .statusCode(200);

        response.body(equalTo("2"));

        response = given()
            .baseUri(baseUrlOfSut)
            .get("/api/title/none/prof")
            .then()
            .statusCode(200);

        response.body(equalTo("2"));
    }

    @Test
    public void testInvalidTitle() {
        given()
            .baseUri(baseUrlOfSut)
            .get("/api/title/male/xyz")
            .then()
            .statusCode(200)
            .body(equalTo("-1"));
    }

    @Test
    public void testInvalidSex() {
        given()
            .baseUri(baseUrlOfSut)
            .get("/api/title/other/mr")
            .then()
            .statusCode(200)
            .body(equalTo("-1"));
    }
}
