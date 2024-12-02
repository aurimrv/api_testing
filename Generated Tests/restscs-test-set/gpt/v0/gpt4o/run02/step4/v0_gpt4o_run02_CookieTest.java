
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

public class v0_gpt4o_run02_CookieTest {
    
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
    public void testCookieUserId() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .port(8080)
            .pathParam("name", "userid")
            .pathParam("val", "user12345")
            .pathParam("site", "example.com")
            .when()
            .get("/api/cookie/{name}/{val}/{site}")
            .then()
            .statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testCookieSessionAM() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .port(8080)
            .pathParam("name", "session")
            .pathParam("val", "am")
            .pathParam("site", "abc.com")
            .when()
            .get("/api/cookie/{name}/{val}/{site}")
            .then()
            .statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testCookieSessionOther() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .port(8080)
            .pathParam("name", "session")
            .pathParam("val", "pm")
            .pathParam("site", "example.com")
            .when()
            .get("/api/cookie/{name}/{val}/{site}")
            .then()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testCookieNotFound() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .port(8080)
            .pathParam("name", "unknown")
            .pathParam("val", "value")
            .pathParam("site", "example.com")
            .when()
            .get("/api/cookie/{name}/{val}/{site}")
            .then()
            .statusCode(404);
    }

    @Test
    public void testCookieUnauthorized() {
        // Assuming there are conditions where unauthorized status can be returned
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .port(8080)
            .pathParam("name", "session")
            .pathParam("val", "am")
            .pathParam("site", "restricted.com")
            .when()
            .get("/api/cookie/{name}/{val}/{site}")
            .then()
            .statusCode(401);
    }

    @Test
    public void testCookieForbidden() {
        // Assuming there are conditions where forbidden status can be returned
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .port(8080)
            .pathParam("name", "session")
            .pathParam("val", "am")
            .pathParam("site", "forbidden.com")
            .when()
            .get("/api/cookie/{name}/{val}/{site}")
            .then()
            .statusCode(403);
    }
}
