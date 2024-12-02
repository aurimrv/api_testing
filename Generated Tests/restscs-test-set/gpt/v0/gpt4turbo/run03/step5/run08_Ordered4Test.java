
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

public class run08_Ordered4Test {
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
        given().pathParam("w", "apple")
               .pathParam("x", "banana")
               .pathParam("y", "date")
               .pathParam("z", "cherry")
               .when()
               .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
               .then()
               .statusCode(200)
               .body(is("increasing"));
    }

    @Test
    public void testOrdered4Decreasing() {
        given().pathParam("w", "date")
               .pathParam("x", "cherry")
               .pathParam("y", "apple")
               .pathParam("z", "banana")
               .when()
               .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
               .then()
               .statusCode(200)
               .body(is("decreasing"));
    }

    @Test
    public void testOrdered4Unordered() {
        given().pathParam("w", "cherry")
               .pathParam("x", "apple")
               .pathParam("y", "date")
               .pathParam("z", "banana")
               .when()
               .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
               .then()
               .statusCode(200)
               .body(is("unordered"));
    }

    @Test
    public void testOrdered4BadRequest() {
        given().pathParam("w", "a")
               .pathParam("x", "b")
               .pathParam("y", "d")
               .pathParam("z", "c")
               .when()
               .get(baseUrlOfSut + "/api/ordered4/{w}/{x}/{z}/{y}")
               .then()
               .statusCode(404); // Correctly expecting 404 status for invalid input cases.
    }
}
