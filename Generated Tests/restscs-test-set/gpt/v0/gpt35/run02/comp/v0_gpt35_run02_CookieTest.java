
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

public class v0_gpt35_run02_CookieTest {

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
    public void testCookie() {
        String name = "userid";
        String val = "user123";
        String site = "abc.com";

        String expected = "1";

        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/cookie/" + name + "/" + val + "/" + site)
            .get()
            .then()
            .statusCode(200);

        assertEquals(expected, response.extract().asString());
    }

    @Test
    public void testCookieUnauthorized() {
        String name = "session";
        String val = "am";
        String site = "xyz.com";

        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/cookie/" + name + "/" + val + "/" + site)
            .get()
            .then()
            .statusCode(401);
    }

    @Test
    public void testCookieForbidden() {
        String name = "session";
        String val = "pm";
        String site = "abc.com";

        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/cookie/" + name + "/" + val + "/" + site)
            .get()
            .then()
            .statusCode(403);
    }

    @Test
    public void testCookieNotFound() {
        String name = "username";
        String val = "john";
        String site = "example.com";

        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .basePath("/api/cookie/" + name + "/" + val + "/" + site)
            .get()
            .then()
            .statusCode(404);
    }
}
