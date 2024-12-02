
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

public class v3_gpt4turbo_run01_TitleTest {

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
    public void testSubjectMaleValidTitle() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("sex", "male")
            .pathParam("title", "mr")
            .when().get("/api/title/{sex}/{title}")
            .then().statusCode(200);

        String result = response.extract().asString();
        assertEquals("1", result);
    }

    @Test
    public void testSubjectFemaleInvalidTitle() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("sex", "female")
            .pathParam("title", "mr")
            .when().get("/api/title/{sex}/{title}")
            .then().statusCode(404); // Assuming the API should respond with 404 for invalid combinations

        String result = response.extract().asString();
        assertTrue(result.contains("Not Found"));
    }

    @Test
    public void testSubjectNoneValidTitle() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("sex", "none")
            .pathParam("title", "dr")
            .when().get("/api/title/{sex}/{title}")
            .then().statusCode(200);

        String result = response.extract().asString();
        assertEquals("2", result);
    }

    @Test
    public void testServerErrorSimulation() {
        ValidatableResponse response = given().baseUri(baseUrlOfSut)
            .pathParam("sex", "none")
            .pathParam("title", "invalidTitle")
            .when().get("/api/title/{sex}/{title}")
            .then().statusCode(500); // Assuming the API should throw 500 Internal Server Error for unexpected errors

        String result = response.extract().asString();
        assertTrue(result.contains("Internal Server Error"));
    }
}
