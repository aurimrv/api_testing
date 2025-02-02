
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
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class v2_gpt4turbo_run02_Ordered4Test {

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
            .baseUri(baseUrlOfSut)
            .pathParam("w", "abcde")
            .pathParam("x", "bcdef")
            .pathParam("y", "cdefg")
            .pathParam("z", "defgh")
            .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
            .then()
            .statusCode(200)
            .body(equalTo("increasing"));

        assertNotNull(response);
    }

    @Test
    public void testOrdered4Decreasing() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("w", "defgh")
            .pathParam("x", "cdefg")
            .pathParam("y", "bcdef")
            .pathParam("z", "abcde")
            .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
            .then()
            .statusCode(200)
            .body(equalTo("decreasing"));

        assertNotNull(response);
    }

    @Test
    public void testOrdered4Unordered() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("w", "abcde")
            .pathParam("x", "defgh")
            .pathParam("y", "bcdef")
            .pathParam("z", "cdefg")
            .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
            .then()
            .statusCode(200)
            .body(equalTo("unordered"));

        assertNotNull(response);
    }

    @Test
    public void testOrdered4ErrorScenario() {
        ValidatableResponse response = given()
            .baseUri(baseUrlOfSut)
            .pathParam("w", "a")
            .pathParam("x", "b")
            .pathParam("y", "c")
            .pathParam("z", "d")
            .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
            .then()
            .statusCode(500);

        assertNotNull(response);
    }

    @Test
    public void testOrdered4SchemaValidation() {
        given()
            .baseUri(baseUrlOfSut)
            .pathParam("w", "abcde")
            .pathParam("x", "bcdef")
            .pathParam("y", "cdefg")
            .pathParam("z", "defgh")
            .when()
            .get("/api/ordered4/{w}/{x}/{z}/{y}")
            .then()
            .assertThat()
            .body(matchesJsonSchemaInClasspath("ordered4-schema.json"));
    }
}
