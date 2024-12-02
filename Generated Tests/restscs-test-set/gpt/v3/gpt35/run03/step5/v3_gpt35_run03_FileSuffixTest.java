
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

public class v3_gpt35_run03_FileSuffixTest {

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
    public void testFileSuffix_ValidInput_Ok() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/filesuffix/text/file.txt")
            .then()
            .statusCode(200);
    }

    @Test
    public void testFileSuffix_InvalidInput_ServerError() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/filesuffix/text/file")
            .then()
            .statusCode(500);
    }

    @Test
    public void testFileSuffix_MissingDirectory_ServerError() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/filesuffix//file.txt")
            .then()
            .statusCode(500);
    }

    @Test
    public void testFileSuffix_MissingFile_ServerError() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/filesuffix/text/")
            .then()
            .statusCode(500);
    }

    @Test
    public void testFileSuffix_InvalidDirectory_ServerError() {
        given()
            .when()
            .get(baseUrlOfSut + "/api/filesuffix/invalid/file.txt")
            .then()
            .statusCode(500);
    }
}
