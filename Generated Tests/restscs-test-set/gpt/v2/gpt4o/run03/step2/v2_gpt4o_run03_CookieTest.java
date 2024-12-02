
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

public class v2_gpt4o_run03_CookieTest {

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
    public void testCookieSubjectUserId() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/api/cookie/userid/user1234/xyz.com")
            .then()
            .statusCode(200);

        response.body(equalTo("1"));
    }

    @Test
    public void testCookieSubjectSession() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/api/cookie/session/am/abc.com")
            .then()
            .statusCode(200);

        response.body(equalTo("1"));
    }

    @Test
    public void testCookieSubjectInvalidSession() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/api/cookie/session/pm/xyz.com")
            .then()
            .statusCode(200);

        response.body(equalTo("2"));
    }

    @Test
    public void testCookieSubjectInvalidUserId() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/api/cookie/userid/short/xyz.com")
            .then()
            .statusCode(200);

        response.body(equalTo("0"));
    }

    @Test
    public void testSchemaValidationForCookie() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/api/cookie/session/am/abc.com")
            .then()
            .statusCode(200);

        // Add explicit schema validation logic here
        response.body("$", hasKey("name"));
        response.body("$", hasKey("val"));
        response.body("$", hasKey("site"));
    }

    @Test
    public void testServerInternalError() {
        // Simulate a condition that forces a 500 Internal Server Error
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("name", "session")
            .pathParam("val", "am")
            .pathParam("site", "abc.com")
            .when()
            .get("/api/cookie/{name}/{val}/{site}")
            .then()
            .statusCode(500);

        assertTrue(response.extract().statusCode() == 500);
    }

    @Test
    public void testBusinessRuleEnforcement() {
        // Validate POST operation
        ValidatableResponse createResponse = given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"name\": \"session\", \"val\": \"am\", \"site\": \"abc.com\"}")
            .when()
            .post("/api/cookie")
            .then()
            .statusCode(201);

        createResponse.body("result", equalTo("1"));

        // Validate PUT operation
        ValidatableResponse updateResponse = given()
            .baseUri(baseUrlOfSut)
            .contentType("application/json")
            .body("{\"name\": \"session\", \"val\": \"pm\", \"site\": \"abc.com\"}")
            .when()
            .put("/api/cookie/session/am/abc.com")
            .then()
            .statusCode(200);

        updateResponse.body("result", equalTo("2"));

        // Validate DELETE operation
        ValidatableResponse deleteResponse = given()
            .baseUri(baseUrlOfSut)
            .when()
            .delete("/api/cookie/session/am/abc.com")
            .then()
            .statusCode(200);

        deleteResponse.body(isEmptyString());
    }
}
