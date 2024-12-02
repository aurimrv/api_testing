
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

public class v3_gpt4o_run03_PatTest {

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
    public void testPatGET() {
        given().log().all()
            .pathParam("txt", "abcde")
            .pathParam("pat", "bcd")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .log().all()
            .statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testPatGETReversed() {
        given().log().all()
            .pathParam("txt", "abcde")
            .pathParam("pat", "dcb")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .log().all()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testPatGETBoth() {
        given().log().all()
            .pathParam("txt", "abcdcb")
            .pathParam("pat", "bcd")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .log().all()
            .statusCode(200)
            .body(equalTo("3"));
    }

    @Test
    public void testPatGETPalindrome() {
        given().log().all()
            .pathParam("txt", "bcddcb")
            .pathParam("pat", "bcd")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .log().all()
            .statusCode(200)
            .body(equalTo("4"));
    }

    @Test
    public void testPatGETReversePalindrome() {
        given().log().all()
            .pathParam("txt", "dcbbcd")
            .pathParam("pat", "bcd")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .log().all()
            .statusCode(200)
            .body(equalTo("5"));
    }

    @Test
    public void testPatGETNotFound() {
        given().log().all()
            .pathParam("txt", "abcdef")
            .pathParam("pat", "xyz")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .log().all()
            .statusCode(200)
            .body(equalTo("0"));
    }

    @Test
    public void testPatGETInvalidInput() {
        given().log().all()
            .pathParam("txt", "")
            .pathParam("pat", "abc")
        .when()
            .get(baseUrlOfSut + "/api/pat/{txt}/{pat}")
        .then()
            .log().all()
            .statusCode(500);
    }
}
