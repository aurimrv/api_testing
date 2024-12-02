
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

public class v3_gpt4o_run03_TitleTest {

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
    public void testValidMaleTitles() {
        String[] validTitles = {"mr", "dr", "sir", "rev", "rthon", "prof"};
        for (String title : validTitles) {
            given().get(baseUrlOfSut + "/api/title/male/" + title)
                .then().statusCode(200)
                .body(equalTo("1"));
        }
    }

    @Test
    public void testValidFemaleTitles() {
        String[] validTitles = {"mrs", "miss", "ms", "dr", "lady", "rev", "rthon", "prof"};
        for (String title : validTitles) {
            given().get(baseUrlOfSut + "/api/title/female/" + title)
                .then().statusCode(200)
                .body(equalTo("0"));
        }
    }

    @Test
    public void testValidNoneTitles() {
        String[] validTitles = {"dr", "rev", "rthon", "prof"};
        for (String title : validTitles) {
            given().get(baseUrlOfSut + "/api/title/none/" + title)
                .then().statusCode(200)
                .body(equalTo("2"));
        }
    }

    @Test
    public void testInvalidSex() {
        given().get(baseUrlOfSut + "/api/title/invalid/mr")
            .then().statusCode(200)
            .body(equalTo("-1"));
    }

    @Test
    public void testInvalidTitleForMale() {
        given().get(baseUrlOfSut + "/api/title/male/mrs")
            .then().statusCode(200)
            .body(equalTo("-1"));
    }

    @Test
    public void testInvalidTitleForFemale() {
        given().get(baseUrlOfSut + "/api/title/female/mr")
            .then().statusCode(200)
            .body(equalTo("-1"));
    }

    @Test
    public void testInvalidTitleForNone() {
        given().get(baseUrlOfSut + "/api/title/none/mr")
            .then().statusCode(200)
            .body(equalTo("-1"));
    }

    @Test
    public void testSchemaValidation() {
        given().get(baseUrlOfSut + "/api/title/male/mr")
            .then().statusCode(200)
            .body(matchesJsonSchemaInClasspath("api_response_schema.json"));
    }

    @Test
    public void testInternalServerError() {
        // Simulate server failure or invalid input that forces a 5xx status code
        given().get(baseUrlOfSut + "/api/title/male/%2E%2E%2F%2E%2E%2F%2E%2E")
            .then().statusCode(500);
    }

    @Test
    public void testUnauthorized() {
        given().get(baseUrlOfSut + "/api/title/male/mr")
            .then().statusCode(401);
    }

    @Test
    public void testForbidden() {
        given().get(baseUrlOfSut + "/api/title/male/mr")
            .then().statusCode(403);
    }

    @Test
    public void testNotFound() {
        given().get(baseUrlOfSut + "/api/title/unknown/mr")
            .then().statusCode(404);
    }
}
