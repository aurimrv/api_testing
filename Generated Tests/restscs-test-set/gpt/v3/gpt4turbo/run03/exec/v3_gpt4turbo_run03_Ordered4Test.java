
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

public class v3_gpt4turbo_run03_Ordered4Test {

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
    public void testOrdered4Increasing() {
        String path = String.format("/api/ordered4/%s/%s/%s/%s", "apple", "banana", "cherry", "date");
        given().when().get(baseUrlOfSut + path).then()
            .statusCode(200)
            .body(not(equalTo("unordered")));
    }

    @Test
    public void testOrdered4Decreasing() {
        String path = String.format("/api/ordered4/%s/%s/%s/%s", "date", "cherry", "banana", "apple");
        given().when().get(baseUrlOfSut + path).then()
            .statusCode(200)
            .body(not(equalTo("unordered")));
    }

    @Test
    public void testOrdered4Unordered() {
        String path = String.format("/api/ordered4/%s/%s/%s/%s", "apple", "date", "banana", "cherry");
        given().when().get(baseUrlOfSut + path).then()
            .statusCode(200)
            .body(equalTo("unordered"));
    }

    @Test
    public void testOrdered4InvalidInputLength() {
        String path = String.format("/api/ordered4/%s/%s/%s/%s", "a", "b", "c", "d");
        given().when().get(baseUrlOfSut + path).then()
            .statusCode(200)
            .body(equalTo("unordered"));
    }

    @Test
    public void testOrdered4ServerError() {
        String path = String.format("/api/ordered4/%s/%s/%s/%s", "error", "will", "cause", "500");
        given().when().get(baseUrlOfSut + path).then()
            .statusCode(500);
    }
}
