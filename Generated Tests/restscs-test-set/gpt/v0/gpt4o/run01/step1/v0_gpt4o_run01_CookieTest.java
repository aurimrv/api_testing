
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

public class v0_gpt4o_run01_CookieTest {

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
    public void testCookieApi() {
        // Test case: name = "userid", val = "user123", site = "example.com"
        given()
            .pathParam("name", "userid")
            .pathParam("val", "user123")
            .pathParam("site", "example.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("1"));

        // Test case: name = "userid", val = "admin", site = "example.com"
        given()
            .pathParam("name", "userid")
            .pathParam("val", "admin")
            .pathParam("site", "example.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));

        // Test case: name = "session", val = "am", site = "abc.com"
        given()
            .pathParam("name", "session")
            .pathParam("val", "am")
            .pathParam("site", "abc.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("1"));

        // Test case: name = "session", val = "am", site = "xyz.com"
        given()
            .pathParam("name", "session")
            .pathParam("val", "am")
            .pathParam("site", "xyz.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("2"));

        // Test case: name = "session", val = "pm", site = "abc.com"
        given()
            .pathParam("name", "session")
            .pathParam("val", "pm")
            .pathParam("site", "abc.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("2"));

        // Test case: name = "other", val = "value", site = "example.com"
        given()
            .pathParam("name", "other")
            .pathParam("val", "value")
            .pathParam("site", "example.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("0"));
    }
}
