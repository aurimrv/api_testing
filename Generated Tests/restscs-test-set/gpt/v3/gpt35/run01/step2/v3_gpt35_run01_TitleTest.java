
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

public class v3_gpt35_run01_TitleTest {

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

    // Test case for Title subject method
    @Test
    public void testTitleSubject() {
        String sex = "male";
        String title = "mr";

        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .basePath("/api/title/" + sex + "/" + title)
                .when()
                .get()
                .then();

        response.statusCode(200);
        response.body(is("1"));
    }

    // Test case to force 5xx error by passing invalid sex value
    @Test
    public void testTitleSubjectInvalidSex() {
        String sex = "invalid";
        String title = "mr";

        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .basePath("/api/title/" + sex + "/" + title)
                .when()
                .get()
                .then();

        response.statusCode(5xx); // Simulating a 5xx server error
    }

    // Test case to force 5xx error by passing invalid title value
    @Test
    public void testTitleSubjectInvalidTitle() {
        String sex = "female";
        String title = "invalid";

        ValidatableResponse response = given()
                .baseUri(baseUrlOfSut)
                .basePath("/api/title/" + sex + "/" + title)
                .when()
                .get()
                .then();

        response.statusCode(5xx); // Simulating a 5xx server error
    }
}
