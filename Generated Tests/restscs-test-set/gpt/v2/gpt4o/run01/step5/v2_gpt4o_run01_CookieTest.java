
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

public class v2_gpt4o_run01_CookieTest {

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
    public void testCookieEndpoint_ValidInput_Userid() {
        given()
            .pathParam("name", "userid")
            .pathParam("val", "user1234")
            .pathParam("site", "example.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testCookieEndpoint_ValidInput_Session() {
        given()
            .pathParam("name", "session")
            .pathParam("val", "am")
            .pathParam("site", "abc.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("1"));
    }

    @Test
    public void testCookieEndpoint_InvalidInput_Session() {
        given()
            .pathParam("name", "session")
            .pathParam("val", "am")
            .pathParam("site", "xyz.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    public void testCookieEndpoint_SchemaValidation() {
        ValidatableResponse response = given()
            .pathParam("name", "userid")
            .pathParam("val", "user1234")
            .pathParam("site", "example.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(200);
        
        // Validate response schema manually, since matchesJsonSchemaInClasspath is unavailable
        Map<String, Object> responseBody = response.extract().jsonPath().getMap("$");
        assertNotNull(responseBody);
        assertNotNull(responseBody.get("id"));
        assertTrue(responseBody.get("id") instanceof Integer);
        assertNotNull(responseBody.get("name"));
        assertTrue(responseBody.get("name") instanceof String);
    }

    @Test
    public void testCookieEndpoint_MissingRequiredFields() {
        given()
            .pathParam("name", "session")
            .pathParam("val", "")
            .pathParam("site", "xyz.com")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(404);
    }

    @Test
    public void testCookieEndpoint_InternalServerError() {
        given()
            .pathParam("name", "session")
            .pathParam("val", "am")
            .pathParam("site", "abc.com")
            .queryParam("forceError", "true")
        .when()
            .get(baseUrlOfSut + "/api/cookie/{name}/{val}/{site}")
        .then()
            .statusCode(500);
    }
}
