package em.embedded.market; //SEED 03

import  org.junit.jupiter.api.AfterAll;
import  org.junit.jupiter.api.BeforeAll;
import  org.junit.jupiter.api.BeforeEach;
import  org.junit.jupiter.api.Test;
import  org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;
import  java.util.Map;
import  java.util.List;
import static org.evomaster.client.java.controller.api.EMTestUtils.*;
import  org.evomaster.client.java.controller.SutHandler;
import  io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import  io.restassured.response.ValidatableResponse;
import static org.evomaster.client.java.sql.dsl.SqlDsl.sql;
import  org.evomaster.client.java.controller.api.dto.database.operations.InsertionResultsDto;
import  org.evomaster.client.java.controller.api.dto.database.operations.InsertionDto;
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
 * This file was automatically generated by EvoMaster on 2024-05-02T18:26:58.197-03:00[America/Araguaina]
 * <br>
 * The generated test suite contains 14 tests
 * <br>
 * Covered targets: 331
 * <br>
 * Used time: 1h 0m 6s
 * <br>
 * Needed budget for current results: 95%
 * <br>
 * This file contains test cases that represent failed calls, but not indicative of faults.
 */
public class seed03_EvoMaster_others_Test {

    
    private static final SutHandler controller = new em.embedded.market.EmbeddedEvoMasterController();
    private static String baseUrlOfSut;
    /** [ems] - expectations master switch - is the variable that activates/deactivates expectations individual test cases
    * by default, expectations are turned off. The variable needs to be set to [true] to enable expectations
    */
    private static boolean ems = false;
    /**
    * sco - supported code oracle - checking that the response status code is among those supported according to the schema
    */
    private static boolean sco = false;
    
    
    @BeforeAll
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
    
    
    @AfterAll
    public static void tearDown() {
        controller.stopSut();
    }
    
    
    @BeforeEach
    public void initTest() {
        controller.resetDatabase(Arrays.asList("CONTACTS","USER_ROLE"));
        controller.resetStateOfSUT();
    }
    
    
    
    
    @Test @Timeout(60)
    public void test_0() throws Exception {
        
        given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/customer?EMextraParam123=_EM_16_XYZ_")
                .then()
                .statusCode(401)
                .assertThat()
                .contentType("application/json")
                .body("'status'", numberMatches(401.0))
                .body("'error'", containsString("Unauthorized"))
                .body("'message'", containsString(""))
                .body("'path'", containsString("/customer"));
        
    }
    
    
    @Test @Timeout(60)
    public void test_1() throws Exception {
        
        given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/customer/orders/594038518?EMextraParam123=_EM_1_XYZ_")
                .then()
                .statusCode(401)
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("Acesso negado"))
                .body("'description'", containsString("uri=/customer/orders/594038518"))
                .body("'entityName'", nullValue())
                .body("'fieldErrors'.size()", equalTo(0));
        
    }
    
    
    @Test @Timeout(60)
    public void test_2() throws Exception {
        
        given().accept("*/*")
                .header("x-EMextraHeader123", "_EM_5_XYZ_")
                .get(baseUrlOfSut + "/customer/cart?EMextraParam123=_EM_4_XYZ_")
                .then()
                .statusCode(401)
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("Acesso negado"))
                .body("'description'", containsString("uri=/customer/cart"))
                .body("'entityName'", nullValue())
                .body("'fieldErrors'.size()", equalTo(0));
        
    }
    
    
    @Test @Timeout(60)
    public void test_3() throws Exception {
        
        given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .delete(baseUrlOfSut + "/customer/cart")
                .then()
                .statusCode(401)
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("Acesso negado"))
                .body("'description'", containsString("uri=/customer/cart"))
                .body("'entityName'", nullValue())
                .body("'fieldErrors'.size()", equalTo(0));
        
    }
    
    
    @Test @Timeout(60)
    public void test_4() throws Exception {
        
        given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/customer/contacts")
                .then()
                .statusCode(401)
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("Acesso negado"))
                .body("'description'", containsString("uri=/customer/contacts"))
                .body("'entityName'", nullValue())
                .body("'fieldErrors'.size()", equalTo(0));
        
    }
    
    
    @Test @Timeout(60)
    public void test_5() throws Exception {
        
        given().accept("*/*")
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/customer/orders")
                .then()
                .statusCode(401)
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("Acesso negado"))
                .body("'description'", containsString("uri=/customer/orders"))
                .body("'entityName'", nullValue())
                .body("'fieldErrors'.size()", equalTo(0));
        
    }
    
    
    @Test @Timeout(60)
    public void test_6() throws Exception {
        
        given().accept("*/*")
                .header("x-EMextraHeader123", "_EM_26_XYZ_")
                .put(baseUrlOfSut + "/customer/cart/delivery?" + 
                    "included=false&" + 
                    "name=9Ow&" + 
                    "EMextraParam123=_EM_25_XYZ_")
                .then()
                .statusCode(401)
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("Acesso negado"))
                .body("'description'", containsString("uri=/customer/cart/delivery"))
                .body("'entityName'", nullValue())
                .body("'fieldErrors'.size()", equalTo(0));
        
    }
    
    
    @Test @Timeout(60)
    public void test_7() throws Exception {
        
        given().accept("*/*")
                .header("x-EMextraHeader123", "_EM_14_XYZ_")
                .get(baseUrlOfSut + "/products/346")
                .then()
                .statusCode(404)
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("Requested entity doesn't exist"))
                .body("'description'", containsString("uri=/products/346"))
                .body("'entityName'", containsString("ProductDTO"))
                .body("'fieldErrors'.size()", equalTo(1))
                .body("'fieldErrors'[0].'field'", containsString("id"))
                .body("'fieldErrors'[0].'message'", containsString("No instance with this id"));
        
    }
    
    
    @Test @Timeout(60)
    public void test_8() throws Exception {
        
        given().accept("*/*")
                .header("Authorization", "Basic aXZhbi5wZXRyb3ZAeWFuZGV4LnJ1OnBldHJvdg==") // user
                .header("x-EMextraHeader123", "_EM_312_XYZ_")
                .get(baseUrlOfSut + "/customer/orders/551?name=_EM_311_XYZ_")
                .then()
                .statusCode(404)
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("Requested entity doesn't exist"))
                .body("'description'", containsString("uri=/customer/orders/551"))
                .body("'entityName'", containsString("OrderDTO"))
                .body("'fieldErrors'.size()", equalTo(1))
                .body("'fieldErrors'[0].'field'", containsString("id"))
                .body("'fieldErrors'[0].'message'", containsString("No instance with this id"));
        
    }
    
    
    @Test @Timeout(60)
    public void test_9() throws Exception {
        
        given().accept("*/*")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=") // admin
                .header("x-EMextraHeader123", "42")
                .contentType("application/json")
                .body(" { " + 
                    " \"productId\": 893738451, " + 
                    " \"quantity\": 515 " + 
                    " } ")
                .put(baseUrlOfSut + "/customer/cart?EMextraParam123=42")
                .then()
                .statusCode(404)
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("Requested entity doesn't exist"))
                .body("'description'", containsString("uri=/customer/cart"))
                .body("'entityName'", containsString("Product"))
                .body("'fieldErrors'.size()", equalTo(1))
                .body("'fieldErrors'[0].'field'", containsString("id"))
                .body("'fieldErrors'[0].'message'", containsString("No instance with this id"));
        
    }
    
    
    @Test @Timeout(60)
    public void test_10() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("x-EMextraHeader123", "42")
                .contentType("application/json")
                .body(" { " + 
                    " \"ccNumber\": \"\" " + 
                    " } ")
                .post(baseUrlOfSut + "/customer/cart/pay?EMextraParam123=42")
                .then()
                .statusCode(406)
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("Argument validation error"))
                .body("'description'", containsString("uri=/customer/cart/pay"))
                .body("'entityName'", containsString("creditCardDTO"))
                .body("'fieldErrors'.size()", equalTo(3))
                .body("'fieldErrors'[0].'field'", containsString("ccNumber"))
                .body("'fieldErrors'[0].'message'", containsString("Card number shall consist of 13-16 digits"))
                .body("'fieldErrors'[1].'field'", containsString("ccNumber"))
                .body("'fieldErrors'[1].'message'", containsString("The value shall not be empty"))
                .body("'fieldErrors'[2].'field'", containsString("ccNumber"))
                .body("'fieldErrors'[2].'message'", containsString("Not a valid credit card number"));
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(201, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test @Timeout(60)
    public void test_11() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=") // admin
                .header("x-EMextraHeader123", "_EM_262_XYZ_")
                .contentType("application/json")
                .body(" { " + 
                    " \"productId\": 584 " + 
                    " } ")
                .put(baseUrlOfSut + "/customer/cart?name=_EM_260_XYZ_")
                .then()
                .statusCode(406)
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("Argument validation error"))
                .body("'description'", containsString("uri=/customer/cart"))
                .body("'entityName'", containsString("cartItemDTO"))
                .body("'fieldErrors'.size()", equalTo(1))
                .body("'fieldErrors'[0].'field'", containsString("quantity"))
                .body("'fieldErrors'[0].'message'", containsString("Value shall be a positive number"));
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 201, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test @Timeout(60)
    public void test_12() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic aXZhbi5wZXRyb3ZAeWFuZGV4LnJ1OnBldHJvdg==") // user
                .header("x-EMextraHeader123", "")
                .contentType("application/json")
                .body(" {} ")
                .put(baseUrlOfSut + "/customer/contacts?EMextraParam123=_EM_439_XYZ_")
                .then()
                .statusCode(406)
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("Argument validation error"))
                .body("'description'", containsString("uri=/customer/contacts"))
                .body("'entityName'", containsString("contactsDTO"))
                .body("'fieldErrors'.size()", equalTo(2))
                .body("'fieldErrors'[0].'field'", containsString("phone"))
                .body("'fieldErrors'[0].'message'", containsString("The value shall not be empty"))
                .body("'fieldErrors'[1].'field'", containsString("address"))
                .body("'fieldErrors'[1].'message'", containsString("The value shall not be empty"));
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 201, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test @Timeout(60)
    public void test_13() throws Exception {
        List<InsertionDto> insertions = sql().insertInto("USER_ROLE", 1274L)
                .d("USER_ID", "NULL")
                .d("ROLE_ID", "NULL")
            .and().insertInto("USER_ROLE", 1275L)
                .d("USER_ID", "0")
                .d("ROLE_ID", "NULL")
            .dtos();
        InsertionResultsDto insertionsresult = controller.execInsertionsIntoDatabase(insertions);
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic aXZhbi5wZXRyb3ZAeWFuZGV4LnJ1OnBldHJvdg==") // user
                .header("x-EMextraHeader123", "")
                .contentType("application/json")
                .body(" { " + 
                    " \"address\": \"FfkwP1yo\", " + 
                    " \"email\": \"_EM_2298_XYZ_\", " + 
                    " \"name\": \" p\" " + 
                    " } ")
                .post(baseUrlOfSut + "/register?EMextraParam123=_EM_2299_XYZ_")
                .then()
                .statusCode(406)
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("Argument validation error"))
                .body("'description'", containsString("uri=/register"))
                .body("'entityName'", containsString("userDTO"))
                .body("'fieldErrors'.size()", equalTo(2))
                .body("'fieldErrors'[0].'field'", containsString("phone"))
                .body("'fieldErrors'[0].'message'", containsString("The value shall not be empty"))
                .body("'fieldErrors'[1].'field'", containsString("email"))
                .body("'fieldErrors'[1].'message'", containsString("The value shall be in the format of an email address"));
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(201, 401, 403, 404).contains(res_0.extract().statusCode()));
    }


}
