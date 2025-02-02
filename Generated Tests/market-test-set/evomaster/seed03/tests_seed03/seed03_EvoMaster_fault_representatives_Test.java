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
 * This file was automatically generated by EvoMaster on 2024-05-02T18:26:58.203-03:00[America/Araguaina]
 * <br>
 * The generated test suite contains 5 tests
 * <br>
 * Covered targets: 315
 * <br>
 * Used time: 1h 0m 6s
 * <br>
 * Needed budget for current results: 95%
 * <br>
 * This file contains one example of each category of fault. The test cases in this file are a subset of the set of test cases likely to indicate faults.
 */
public class seed03_EvoMaster_fault_representatives_Test {

    
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
    /**
    * rso - response structure oracle - checking that the response objects match the responses defined in the schema
    */
    private static boolean rso = false;
    
    
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
        controller.resetDatabase(Arrays.asList("CONTACTS","CUSTOMER_ORDER","CART"));
        controller.resetStateOfSUT();
    }
    
    
    
    
    /**
    * [test_0_with500] is a part of 1 or more clusters, as defined by the selected clustering options. 
    * ErrorText_1
    * LastLine_0
    */
    @Test @Timeout(60)
    public void test_0_with500() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=") // admin
                .header("x-EMextraHeader123", "_EM_517_XYZ_")
                .contentType("application/json")
                .body(" { " + 
                    " \"_links\": [ " + 
                    " { " + 
                    " \"hreflang\": \"_EM_473_XYZ_\", " + 
                    " \"profile\": \"_EM_476_XYZ_\", " + 
                    " \"rel\": { " + 
                    " \"_EM_477_XYZ_\": \"_EM_478_XYZ_\" " + 
                    " } " + 
                    " }, " + 
                    " { " + 
                    " \"media\": \"h6_QQFFiM\", " + 
                    " \"name\": \"_EM_484_XYZ_\", " + 
                    " \"profile\": \"_EM_485_XYZ_\", " + 
                    " \"title\": \"_EM_492_XYZ_\" " + 
                    " }, " + 
                    " { " + 
                    " \"deprecation\": \"_EM_494_XYZ_\", " + 
                    " \"hreflang\": \"_EM_496_XYZ_\", " + 
                    " \"media\": \"_EM_497_XYZ_\", " + 
                    " \"name\": \"_EM_498_XYZ_\", " + 
                    " \"title\": \"_EM_500_XYZ_\", " + 
                    " \"type\": \"_EM_501_XYZ_\" " + 
                    " }, " + 
                    " { " + 
                    " \"hreflang\": \"_EM_503_XYZ_\", " + 
                    " \"media\": \"_EM_504_XYZ_\", " + 
                    " \"profile\": \"_EM_506_XYZ_\", " + 
                    " \"title\": \"_EM_514_XYZ_\" " + 
                    " } " + 
                    " ], " + 
                    " \"productId\": 134, " + 
                    " \"quantity\": 674 " + 
                    " } ")
                .put(baseUrlOfSut + "/customer/cart?name=_EM_470_XYZ_")
                .then()
                .statusCode(500) // market/rest/exception/RestErrorResponse_50_getFieldErrors
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("JSON parse error: Expected relation name; nested exception is com.fasterxml.jackson.databind.JsonMappingException: Expected relation name\n at [Source: (ByteArrayInputStream); line: 1, column: 13] (through reference chain: market.dto.CartItemDTO[\"_links\"])"))
                .body("'description'", containsString("uri=/customer/cart"))
                .body("'entityName'", nullValue())
                .body("'fieldErrors'.size()", equalTo(0));
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 201, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    /**
    * [test_1_with500] is a part of 1 or more clusters, as defined by the selected clustering options. 
    * ErrorText_0
    * LastLine_0
    */
    @Test @Timeout(60)
    public void test_1_with500() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=") // admin
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/customer/contacts?EMextraParam123=_EM_194_XYZ_")
                .then()
                .statusCode(500) // market/dto/assembler/ContactsDtoAssembler_12_toModel
                .assertThat()
                .contentType("application/json")
                .body("'message'", nullValue())
                .body("'description'", containsString("uri=/customer/contacts"))
                .body("'entityName'", nullValue())
                .body("'fieldErrors'.size()", equalTo(0));
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    /**
    * [test_2_with500] is a part of 1 or more clusters, as defined by the selected clustering options. 
    * ErrorText_0
    * LastLine_0
    */
    @Test @Timeout(60)
    public void test_2_with500() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=") // admin
                .header("x-EMextraHeader123", "_EM_202_XYZ_")
                .get(baseUrlOfSut + "/customer")
                .then()
                .statusCode(500) // market/dto/assembler/UserAccountDtoAssembler_16_toModel
                .assertThat()
                .contentType("application/json")
                .body("'message'", nullValue())
                .body("'description'", containsString("uri=/customer"))
                .body("'entityName'", nullValue())
                .body("'fieldErrors'.size()", equalTo(0));
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    /**
    * [test_3_with500] is a part of 1 or more clusters, as defined by the selected clustering options. 
    * ErrorText_0
    * LastLine_0
    */
    @Test @Timeout(60)
    public void test_3_with500() throws Exception {
        List<InsertionDto> insertions = sql().insertInto("CONTACTS", 2325L)
                .d("PHONE", "NULL")
                .d("ADDRESS", "\"C0WAJkVv\"")
                .d("CITY_REGION", "NULL")
            .and().insertInto("CONTACTS", 2326L)
                .d("PHONE", "\"DF\"")
                .d("ADDRESS", "\"i27n0\"")
                .d("CITY_REGION", "NULL")
            .and().insertInto("CUSTOMER_ORDER", 10814L)
                .d("ID", "1128177350300132335")
                .d("USER_ACCOUNT_ID", "1")
                .d("DATE_CREATED", "\"2048-01-15\"")
                .d("EXECUTED", "true")
                .d("PRODUCTS_COST", "641")
                .d("DELIVERY_INCLUDED", "false")
                .d("DELIVERY_COST", "109")
            .and().insertInto("CUSTOMER_ORDER", 10815L)
                .d("ID", "728")
                .d("USER_ACCOUNT_ID", "NULL")
                .d("DATE_CREATED", "\"2074-04-13\"")
                .d("EXECUTED", "false")
                .d("PRODUCTS_COST", "831")
                .d("DELIVERY_INCLUDED", "true")
                .d("DELIVERY_COST", "975")
            .and().insertInto("CUSTOMER_ORDER", 10816L)
                .d("ID", "835")
                .d("USER_ACCOUNT_ID", "234")
                .d("DATE_CREATED", "NULL")
                .d("EXECUTED", "false")
                .d("PRODUCTS_COST", "925")
                .d("DELIVERY_INCLUDED", "true")
                .d("DELIVERY_COST", "869")
            .and().insertInto("CUSTOMER_ORDER", 10817L)
                .d("ID", "2059406478")
                .d("USER_ACCOUNT_ID", "NULL")
                .d("DATE_CREATED", "\"1908-05-17\"")
                .d("EXECUTED", "true")
                .d("PRODUCTS_COST", "735")
                .d("DELIVERY_INCLUDED", "false")
                .d("DELIVERY_COST", "534")
            .and().insertInto("CART", 10818L)
                .d("TOTAL_ITEMS", "NULL")
                .d("PRODUCTS_COST", "634")
                .d("DELIVERY_INCLUDED", "false")
            .and().insertInto("CART", 10819L)
                .d("TOTAL_ITEMS", "NULL")
                .d("PRODUCTS_COST", "NULL")
                .d("DELIVERY_INCLUDED", "true")
            .and().insertInto("CART", 10820L)
                .d("TOTAL_ITEMS", "146")
                .d("PRODUCTS_COST", "NULL")
                .d("DELIVERY_INCLUDED", "false")
            .and().insertInto("CART", 10821L)
                .d("TOTAL_ITEMS", "NULL")
                .d("PRODUCTS_COST", "NULL")
                .d("DELIVERY_INCLUDED", "true")
            .and().insertInto("CART", 10822L)
                .d("TOTAL_ITEMS", "NULL")
                .d("PRODUCTS_COST", "NULL")
                .d("DELIVERY_INCLUDED", "false")
            .dtos();
        InsertionResultsDto insertionsresult = controller.execInsertionsIntoDatabase(insertions);
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=") // admin
                .header("x-EMextraHeader123", "")
                .get(baseUrlOfSut + "/customer/orders")
                .then()
                .statusCode(500) // market/dto/assembler/OrderDtoAssembler_18_toModel
                .assertThat()
                .contentType("application/json")
                .body("'message'", nullValue())
                .body("'description'", containsString("uri=/customer/orders"))
                .body("'entityName'", nullValue())
                .body("'fieldErrors'.size()", equalTo(0));
        
        expectationHandler.expect(ems)
            .that(sco, Arrays.asList(200, 401, 403, 404).contains(res_0.extract().statusCode()));
    }
    
    
    @Test @Timeout(60)
    public void test_4() throws Exception {
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic aXZhbi5wZXRyb3ZAeWFuZGV4LnJ1OnBldHJvdg==") // user
                .header("x-EMextraHeader123", "_EM_323_XYZ_")
                .get(baseUrlOfSut + "/customer/orders?" + 
                    "name=_EM_322_XYZ_&" + 
                    "EMextraParam123=42")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType("application/json")
                .body("size()", equalTo(1))
                .body("[0].'userAccount'", containsString("ivan.petrov@yandex.ru"))
                .body("[0].'billNumber'", numberMatches(2.7132054E8))
                .body("[0].'dateCreated'", containsString("2019-12-27T03:00:00.000+00:00"))
                .body("[0].'productsCost'", numberMatches(8127.0))
                .body("[0].'deliveryCost'", numberMatches(400.0))
                .body("[0].'deliveryIncluded'", equalTo(true))
                .body("[0].'totalCost'", numberMatches(8527.0))
                .body("[0].'payed'", equalTo(true))
                .body("[0].'executed'", equalTo(false))
                .body("[0].'links'.size()", equalTo(0));
        
        expectationHandler.expect(ems)
            .that(rso, ((Map) ((List) res_0.extract().response().jsonPath().getJsonObject("")).get(0)).keySet().containsAll(Arrays.asList()));
    }


}
