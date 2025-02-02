package org.restncs;

import  org.junit.AfterClass;
import  org.junit.BeforeClass;
import  org.junit.Before;
import  org.junit.Test;
import static org.junit.Assert.*;
import  java.util.Map;
import  java.util.List;
import static org.evomaster.client.java.controller.api.EMTestUtils.*;
import  org.evomaster.client.java.controller.SutHandler;
import  io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import  io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.*;
import  io.restassured.config.JsonConfig;
import  io.restassured.path.json.config.JsonPathConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static org.evomaster.client.java.controller.contentMatchers.NumberMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.StringMatcher.*;
import static org.evomaster.client.java.controller.contentMatchers.SubStringMatcher.*;
import static org.evomaster.client.java.controller.expect.ExpectationHandler.expectationHandler;
import  org.evomaster.client.java.controller.expect.ExpectationHandler;
import  io.restassured.path.json.JsonPath;
import  java.util.Arrays;




/**
 * This file was automatically generated by EvoMaster on 2024-06-01T16:17:18.044079-03:00[America/Araguaina]
 * <br>
 * The generated test suite contains 46 tests
 * <br>
 * Covered targets: 605
 * <br>
 * Used time: 1h 0m 0s
 * <br>
 * Needed budget for current results: 92%
 * <br>
 * This file contains test cases that represent successful calls.
 */
public class seed06_EvoMaster_successes_Test {

    
    private static final SutHandler controller = new em.embedded.org.restncs.EmbeddedEvoMasterController();
    private static String baseUrlOfSut;
    /** [ems] - expectations master switch - is the variable that activates/deactivates expectations individual test cases
    * by default, expectations are turned off. The variable needs to be set to [true] to enable expectations
    */
    private static boolean ems = false;
    /**
    * sco - supported code oracle - checking that the response status code is among those supported according to the schema
    */
    private static boolean sco = false;
    
    
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
    
    
    
    
    @Test(timeout = 60000)
    public void test_0() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .get(baseUrlOfSut + "/v2/api-docs")
                .then()
                .statusCode(200);
        
        expectationHandler.expect(ems)
            /*
             Note: No supported codes appear to be defined. https://swagger.io/docs/specification/describing-responses/.
             This is somewhat unexpected, so the code below is likely to lead to a failed expectation
            */
            .that(sco, Arrays.asList().contains(res_0.extract().statusCode()));
    }
    
    
    @Test(timeout = 60000)
    public void test_1() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_59_XYZ_")
                .get(baseUrlOfSut + "/api/triangle/-222/474/0?EMextraParam123=_EM_58_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(0.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_2() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/triangle/290/-268434982/2097579")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(0.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_3() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "42")
                .get(baseUrlOfSut + "/api/triangle/131635/524721/0?EMextraParam123=_EM_44_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(0.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_4() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/triangle/488/488/488?EMextraParam123=_EM_1284_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(3.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_5() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_75_XYZ_")
                .get(baseUrlOfSut + "/api/expint/0/1.1")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.3026100760891632));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_6() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_180_XYZ_")
                .get(baseUrlOfSut + "/api/triangle/803/530/29?EMextraParam123=_EM_179_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(0.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_7() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/triangle/296/717/183")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(0.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_8() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/remainder/0/84?EMextraParam123=_EM_122_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(-1.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_9() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_22_XYZ_")
                .get(baseUrlOfSut + "/api/triangle/243/233/497?EMextraParam123=_EM_21_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(0.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_10() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/remainder/51/0")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(-1.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_11() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_41_XYZ_")
                .get(baseUrlOfSut + "/api/expint/33554019/0.0")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(2.9802690098097936E-8));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_12() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/triangle/284/264/264?EMextraParam123=_EM_1139_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(2.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_13() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/bessj/8/0.0")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.0));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_14() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/triangle/895/933/566")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(1.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_15() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_1285_XYZ_")
                .get(baseUrlOfSut + "/api/triangle/488/488/229")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(2.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_16() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/triangle/563/433/430?EMextraParam123=_EM_44_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(1.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_17() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "42")
                .get(baseUrlOfSut + "/api/triangle/824/686/920")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(1.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_18() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_10323_XYZ_")
                .get(baseUrlOfSut + "/api/triangle/910/947/910")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(2.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_19() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/remainder/445/189")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(67.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_20() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_873_XYZ_")
                .get(baseUrlOfSut + "/api/remainder/729/-215")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(84.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_21() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/remainder/-890/-2")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(0.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_22() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_123_XYZ_")
                .get(baseUrlOfSut + "/api/remainder/-465/15?EMextraParam123=_EM_122_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", numberMatches(0.0))
                .body("'resultAsDouble'", nullValue());
        
    }
    
    
    @Test(timeout = 60000)
    public void test_23() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/fisher/-65491/526/0.7688879464251451")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(1.0));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_24() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_1_XYZ_")
                .get(baseUrlOfSut + "/api/fisher/580/-8388493/-47795.854285650836")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", containsString("NaN"));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_25() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_3189_XYZ_")
                .get(baseUrlOfSut + "/api/expint/348/1.0?EMextraParam123=_EM_3188_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.0010571160711654046));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_26() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_957_XYZ_")
                .get(baseUrlOfSut + "/api/expint/1/0.4881296706663869?EMextraParam123=_EM_956_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.5744338976467454));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_27() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/expint/33555043/0.03860852002413917")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(2.8673106372537844E-8));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_28() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/gammq/0.103/0.0")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(1.0));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_29() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_1608_XYZ_")
                .get(baseUrlOfSut + "/api/fisher/939/94/0.0391394171876398")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.0));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_30() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_512_XYZ_")
                .get(baseUrlOfSut + "/api/fisher/567/-44/0.77868832?EMextraParam123=_EM_511_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(1.0));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_31() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/expint/328/55.48860456018823")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(2.0836354308954973E-27));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_32() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/fisher/580/115/0.33796652614987843?EMextraParam123=_EM_0_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(9.981649130361297E-15));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_33() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "42")
                .get(baseUrlOfSut + "/api/fisher/45/522/0.7688879464251451?EMextraParam123=42")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.13798537765706442));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_34() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/fisher/545/273/0.5998263702993882")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(2.885051222315375E-7));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_35() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/expint/4/0.5529748477179358?EMextraParam123=_EM_1781_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.15394822054525026));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_36() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_233_XYZ_")
                .get(baseUrlOfSut + "/api/gammq/8.988465674311579E307/4379.506963364756?EMextraParam123=_EM_232_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", containsString("NaN"));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_37() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/gammq/0.12322269591728463/0.2638089466625745")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.12390097388375321));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_38() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/bessj/8/-8.0?EMextraParam123=_EM_472_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.22345498635390812));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_39() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "42")
                .get(baseUrlOfSut + "/api/bessj/692/-0.1977807289964204?EMextraParam123=42")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.0));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_40() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/bessj/19/1.0585784912109375E-16")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", containsString("NaN"));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_41() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/gammq/0.7/1.7")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.10663184155050097));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_42() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_233_XYZ_")
                .get(baseUrlOfSut + "/api/gammq/0.8874878393494446/135.01460037394156?EMextraParam123=_EM_232_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(1.2326969538851028E-59));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_43() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/bessj/4/-6.203223696186327")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(0.3289028152890824));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_44() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/api/bessj/40/-587.3992966299028")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(-0.02895100998469423));
        
    }
    
    
    @Test(timeout = 60000)
    public void test_45() throws Exception {
        
        given().accept("application/json")
                .header("x-EMextraHeader123", "_EM_473_XYZ_")
                .get(baseUrlOfSut + "/api/bessj/5/-8.0?EMextraParam123=_EM_472_XYZ_")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("'resultAsInt'", nullValue())
                .body("'resultAsDouble'", numberMatches(-0.1857747723321807));
        
    }


}
