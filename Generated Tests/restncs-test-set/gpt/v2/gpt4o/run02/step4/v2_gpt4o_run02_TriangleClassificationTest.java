
package org.restncs;

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

public class v2_gpt4o_run02_TriangleClassificationTest {

    private static final SutHandler controller = new em.embedded.org.restncs.EmbeddedEvoMasterController();
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
    public void testTriangleClassificationSuccess() {
        given().pathParam("a", 3)
               .pathParam("b", 4)
               .pathParam("c", 5)
               .when().get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
               .then().statusCode(200)
               .body("resultAsInt", equalTo(1));
    }

    @Test
    public void testTriangleClassificationEquilateral() {
        given().pathParam("a", 3)
               .pathParam("b", 3)
               .pathParam("c", 3)
               .when().get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
               .then().statusCode(200)
               .body("resultAsInt", equalTo(3));
    }

    @Test
    public void testTriangleClassificationIsosceles() {
        given().pathParam("a", 3)
               .pathParam("b", 3)
               .pathParam("c", 2)
               .when().get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
               .then().statusCode(200)
               .body("resultAsInt", equalTo(2));
    }

    @Test
    public void testTriangleClassificationInvalidZero() {
        given().pathParam("a", 0)
               .pathParam("b", 4)
               .pathParam("c", 5)
               .when().get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
               .then().statusCode(200)
               .body("resultAsInt", equalTo(0));
    }

    @Test
    public void testTriangleClassificationInvalidNegative() {
        given().pathParam("a", -3)
               .pathParam("b", 4)
               .pathParam("c", 5)
               .when().get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
               .then().statusCode(200)
               .body("resultAsInt", equalTo(0));
    }

    @Test
    public void testTriangleClassificationInvalidSum() {
        given().pathParam("a", 1)
               .pathParam("b", 2)
               .pathParam("c", 3)
               .when().get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
               .then().statusCode(200)
               .body("resultAsInt", equalTo(0));
    }

    @Test
    public void testTriangleClassificationServerError() {
        given().pathParam("a", Integer.MAX_VALUE)
               .pathParam("b", Integer.MAX_VALUE)
               .pathParam("c", Integer.MAX_VALUE)
               .when().get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
               .then().statusCode(500);
    }

    @Test
    public void testTriangleClassificationSchemaValidation() {
        ValidatableResponse response = given().pathParam("a", 3)
                                              .pathParam("b", 4)
                                              .pathParam("c", 5)
                                              .when().get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
                                              .then().statusCode(200);

        String json = response.extract().asString();
        JsonPath jp = new JsonPath(json);

        assertTrue(jp.getMap("").keySet().containsAll(Arrays.asList("resultAsDouble", "resultAsInt")));
        assertThat(jp.get("resultAsInt"), instanceOf(Integer.class));
        assertThat(jp.get("resultAsDouble"), instanceOf(Double.class));
    }

    @Test
    public void testTriangleClassificationMissingParams() {
        given().pathParam("a", 3)
               .pathParam("b", 4)
               .pathParam("c", (Integer) null)
               .when().get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
               .then().statusCode(404);
    }

    @Test
    public void testTriangleClassificationInvalidType() {
        given().pathParam("a", "invalid")
               .pathParam("b", 4)
               .pathParam("c", 5)
               .when().get(baseUrlOfSut + "/api/triangle/{a}/{b}/{c}")
               .then().statusCode(400);
    }
}
