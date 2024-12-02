
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

public class v2_gpt4turbo_run03_Ordered4Test {
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
        ValidatableResponse response = given()
            .pathParam("w", "apple")
            .pathParam("x", "banana")
            .pathParam("z", "cherry")
            .pathParam("y", "date")
            .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then()
            .statusCode(200);
        String body = response.extract().body().asString();
        assertTrue("Expected 'increasing' but got '" + body + "'", "increasing".equals(body));
    }

    @Test
    public void testOrdered4Decreasing() {
        ValidatableResponse response = given()
            .pathParam("w", "date")
            .pathParam("x", "cherry")
            .pathParam("z", "banana")
            .pathParam("y", "apple")
            .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then()
            .statusCode(200);
        String body = response.extract().body().asString();
        assertTrue("Expected 'decreasing' but got '" + body + "'", "decreasing".equals(body));
    }

    @Test
    public void testOrdered4Unordered() {
        given()
            .pathParam("w", "banana")
            .pathParam("x", "apple")
            .pathParam("z", "date")
            .pathParam("y", "cherry")
            .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then()
            .statusCode(200)
            .body(equalTo("unordered"));
    }

    @Test
    public void testOrdered4Error() {
        given()
            .pathParam("w", "ba")
            .pathParam("x", "ap")
            .pathParam("z", "da")
            .pathParam("y", "ch")
            .when()
            .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
            .then()
            .statusCode(500); // Assuming the server responds with 500 for invalid inputs
    }
}
