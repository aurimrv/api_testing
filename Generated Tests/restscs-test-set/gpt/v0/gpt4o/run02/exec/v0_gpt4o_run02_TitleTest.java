
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

public class v0_gpt4o_run02_TitleTest {

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
        String[] maleTitles = {"mr", "dr", "sir", "rev", "rthon", "prof"};
        for (String title : maleTitles) {
            String response = given()
                .pathParam("sex", "male")
                .pathParam("title", title)
                .when()
                .get(baseUrlOfSut + "/api/title/{sex}/{title}")
                .then()
                .statusCode(200)
                .extract().asString();
            assertEquals("1", response);
        }
    }

    @Test
    public void testValidFemaleTitles() {
        String[] femaleTitles = {"mrs", "miss", "ms", "dr", "lady", "rev", "rthon", "prof"};
        for (String title : femaleTitles) {
            String response = given()
                .pathParam("sex", "female")
                .pathParam("title", title)
                .when()
                .get(baseUrlOfSut + "/api/title/{sex}/{title}")
                .then()
                .statusCode(200)
                .extract().asString();
            assertEquals("0", response);
        }
    }

    @Test
    public void testValidNoneTitles() {
        String[] noneTitles = {"dr", "rev", "rthon", "prof"};
        for (String title : noneTitles) {
            String response = given()
                .pathParam("sex", "none")
                .pathParam("title", title)
                .when()
                .get(baseUrlOfSut + "/api/title/{sex}/{title}")
                .then()
                .statusCode(200)
                .extract().asString();
            assertEquals("2", response);
        }
    }

    @Test
    public void testInvalidMaleTitles() {
        String[] invalidTitles = {"mrs", "miss", "ms", "lady"};
        for (String title : invalidTitles) {
            String response = given()
                .pathParam("sex", "male")
                .pathParam("title", title)
                .when()
                .get(baseUrlOfSut + "/api/title/{sex}/{title}")
                .then()
                .statusCode(200)
                .extract().asString();
            assertEquals("-1", response);
        }
    }

    @Test
    public void testInvalidFemaleTitles() {
        String[] invalidTitles = {"mr", "sir"};
        for (String title : invalidTitles) {
            String response = given()
                .pathParam("sex", "female")
                .pathParam("title", title)
                .when()
                .get(baseUrlOfSut + "/api/title/{sex}/{title}")
                .then()
                .statusCode(200)
                .extract().asString();
            assertEquals("-1", response);
        }
    }

    @Test
    public void testInvalidNoneTitles() {
        String[] invalidTitles = {"mr", "mrs", "miss", "ms", "sir", "lady"};
        for (String title : invalidTitles) {
            String response = given()
                .pathParam("sex", "none")
                .pathParam("title", title)
                .when()
                .get(baseUrlOfSut + "/api/title/{sex}/{title}")
                .then()
                .statusCode(200)
                .extract().asString();
            assertEquals("-1", response);
        }
    }

    @Test
    public void testInvalidSex() {
        String[] sexes = {"unknown", "other"};
        String[] titles = {"mr", "mrs", "miss", "ms", "dr", "sir", "lady", "rev", "rthon", "prof"};
        for (String sex : sexes) {
            for (String title : titles) {
                String response = given()
                    .pathParam("sex", sex)
                    .pathParam("title", title)
                    .when()
                    .get(baseUrlOfSut + "/api/title/{sex}/{title}")
                    .then()
                    .statusCode(200)
                    .extract().asString();
                assertEquals("-1", response);
            }
        }
    }

    @Test
    public void testNotFound() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/title/invalid_path")
            .then()
            .statusCode(404);
    }

    @Test
    public void testUnauthorized() {
        given()
            .auth().preemptive().basic("invalidUser", "invalidPass")
            .pathParam("sex", "male")
            .pathParam("title", "mr")
            .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then()
            .statusCode(401);
    }

    @Test
    public void testForbidden() {
        given()
            .auth().preemptive().basic("forbiddenUser", "forbiddenPass")
            .pathParam("sex", "male")
            .pathParam("title", "mr")
            .when()
            .get(baseUrlOfSut + "/api/title/{sex}/{title}")
            .then()
            .statusCode(403);
    }
}
