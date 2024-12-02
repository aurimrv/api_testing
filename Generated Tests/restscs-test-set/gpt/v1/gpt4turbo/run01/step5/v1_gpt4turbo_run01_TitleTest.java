
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

public class v1_gpt4turbo_run01_TitleTest {

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
    public void testTitleEndpointMaleMr() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("sex", "male")
            .pathParam("title", "mr")
            .when()
            .get("/api/title/{sex}/{title}")
            .then()
            .statusCode(200)
            .body(equalTo("1"));

        assertEquals("1", response.extract().asString());
    }

    @Test
    public void testTitleEndpointFemaleMrs() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("sex", "female")
            .pathParam("title", "mrs")
            .when()
            .get("/api/title/{sex}/{title}")
            .then()
            .statusCode(200)
            .body(equalTo("0"));

        assertEquals("0", response.extract().asString());
    }

    @Test
    public void testTitleEndpointNoneDr() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("sex", "none")
            .pathParam("title", "dr")
            .when()
            .get("/api/title/{sex}/{title}")
            .then()
            .statusCode(200)
            .body(equalTo("2"));

        assertEquals("2", response.extract().asString());
    }

    @Test
    public void testTitleEndpointInvalidSex() {
        given().baseUri(baseUrlOfSut)
            .pathParam("sex", "unknown")
            .pathParam("title", "mr")
            .when()
            .get("/api/title/{sex}/{title}")
            .then()
            .statusCode(200)
            .body(equalTo("-1"));
    }

    @Test
    public void testTitleEndpointInvalidTitle() {
        given().baseUri(baseUrlOfSut)
            .pathParam("sex", "male")
            .pathParam("title", "king")
            .when()
            .get("/api/title/{sex}/{title}")
            .then()
            .statusCode(200)
            .body(equalTo("-1"));
    }
}
