
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

public class run03_PatTest {
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
    public void testSubjectWithPat() {
        String txt = "example";
        String pat = "amp";
        ValidatableResponse response = given().basePath(baseUrlOfSut)
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when().get("/api/pat/{txt}/{pat}")
            .then().statusCode(200)
            .body(is("1"));

        assertNotNull(response);
    }

    @Test
    public void testSubjectWithReversePat() {
        String txt = "elpmaxe";
        String pat = "amp";
        ValidatableResponse response = given().basePath(baseUrlOfSut)
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when().get("/api/pat/{txt}/{pat}")
            .then().statusCode(200)
            .body(is("2"));

        assertNotNull(response);
    }

    @Test
    public void testSubjectWithBothPatAndReverse() {
        String txt = "exampleelpmaxe";
        String pat = "amp";
        ValidatableResponse response = given().basePath(baseUrlOfSut)
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when().get("/api/pat/{txt}/{pat}")
            .then().statusCode(200)
            .body(is("3"));

        assertNotNull(response);
    }

    @Test
    public void testSubjectWithPalindromePatFollowedByReverse() {
        String txt = "ampelpmaxe";
        String pat = "amp";
        ValidatableResponse response = given().basePath(baseUrlOfSut)
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when().get("/api/pat/{txt}/{pat}")
            .then().statusCode(200)
            .body(is("4"));

        assertNotNull(response);
    }

    @Test
    public void testSubjectWithPalindromeReverseFollowedByPat() {
        String txt = "elpmaxeamp";
        String pat = "amp";
        ValidatableResponse response = given().basePath(baseUrlOfSut)
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when().get("/api/pat/{txt}/{pat}")
            .then().statusCode(200)
            .body(is("5"));

        assertNotNull(response);
    }

    @Test
    public void testSubjectWithNoMatch() {
        String txt = "hello";
        String pat = "amp";
        ValidatableResponse response = given().basePath(baseUrlOfSut)
            .pathParam("txt", txt)
            .pathParam("pat", pat)
            .when().get("/api/pat/{txt}/{pat}")
            .then().statusCode(200)
            .body(is("0"));

        assertNotNull(response);
    }
}
