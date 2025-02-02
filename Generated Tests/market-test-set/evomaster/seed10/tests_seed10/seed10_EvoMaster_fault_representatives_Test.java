package em.embedded.market;
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
 * This file was automatically generated by EvoMaster on 2024-05-03T21:52:53.906-03:00[America/Araguaina]
 * <br>
 * The generated test suite contains 5 tests
 * <br>
 * Covered targets: 312
 * <br>
 * Used time: 1h 0m 9s
 * <br>
 * Needed budget for current results: 83%
 * <br>
 * This file contains one example of each category of fault. The test cases in this file are a subset of the set of test cases likely to indicate faults.
 */
public class seed10_EvoMaster_fault_representatives_Test {

    
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
        controller.resetDatabase(Arrays.asList("CONTACTS","USER_ROLE","CUSTOMER_ORDER","PRODUCT","CART","DISTILLERY","ORDERED_PRODUCT","BILL"));
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
                .header("x-EMextraHeader123", "")
                .contentType("application/json")
                .body(" { " + 
                    " \"_links\": [ " + 
                    " { " + 
                    " \"hreflang\": \"_EM_60_XYZ_\", " + 
                    " \"media\": \"_EM_61_XYZ_\", " + 
                    " \"rel\": {}, " + 
                    " \"title\": \"_EM_64_XYZ_\", " + 
                    " \"type\": \"_EM_65_XYZ_\" " + 
                    " }, " + 
                    " { " + 
                    " \"media\": \"_EM_68_XYZ_\", " + 
                    " \"name\": \"_EM_69_XYZ_\", " + 
                    " \"rel\": { " + 
                    " \"_EM_71_XYZ_\": \"_EM_72_XYZ_\" " + 
                    " } " + 
                    " }, " + 
                    " { " + 
                    " \"href\": \"_EM_76_XYZ_\", " + 
                    " \"name\": \"_EM_79_XYZ_\", " + 
                    " \"profile\": \"_EM_80_XYZ_\", " + 
                    " \"title\": \"_EM_91_XYZ_\" " + 
                    " }, " + 
                    " { " + 
                    " \"deprecation\": \"_EM_93_XYZ_\", " + 
                    " \"hreflang\": \"_EM_95_XYZ_\", " + 
                    " \"media\": \"_EM_96_XYZ_\", " + 
                    " \"rel\": { " + 
                    " \"_EM_98_XYZ_\": \"\", " + 
                    " \"_EM_99_XYZ_\": \"_EM_100_XYZ_\", " + 
                    " \"_EM_101_XYZ_\": \"_EM_102_XYZ_\", " + 
                    " \"_EM_103_XYZ_\": \"_EM_104_XYZ_\" " + 
                    " }, " + 
                    " \"type\": \"_EM_106_XYZ_\" " + 
                    " } " + 
                    " ] " + 
                    " } ")
                .put(baseUrlOfSut + "/customer/contacts?" + 
                    "name=_EM_57_XYZ_&" + 
                    "EMextraParam123=_EM_108_XYZ_")
                .then()
                .statusCode(500) // market/rest/exception/RestErrorResponse_50_getFieldErrors
                .assertThat()
                .contentType("application/json")
                .body("'message'", containsString("JSON parse error: Expected relation name; nested exception is com.fasterxml.jackson.databind.JsonMappingException: Expected relation name\n at [Source: (ByteArrayInputStream); line: 1, column: 13] (through reference chain: market.dto.ContactsDTO[\"_links\"])"))
                .body("'description'", containsString("uri=/customer/contacts"))
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
                .header("x-EMextraHeader123", "_EM_144_XYZ_")
                .get(baseUrlOfSut + "/customer/contacts")
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
                .header("x-EMextraHeader123", "_EM_152_XYZ_")
                .get(baseUrlOfSut + "/customer?EMextraParam123=_EM_151_XYZ_")
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
        List<InsertionDto> insertions = sql().insertInto("CONTACTS", 972L)
                .d("PHONE", "\"_EM_3230_XYZ_\"")
                .d("ADDRESS", "\"_EM_8094_XYZ_\"")
                .d("CITY_REGION", "\"eJ_oC\"")
            .and().insertInto("CONTACTS", 973L)
                .d("PHONE", "NULL")
                .d("ADDRESS", "\"Ta\"")
                .d("CITY_REGION", "\"_EM_8095_XYZ_\"")
            .and().insertInto("USER_ROLE", 974L)
                .d("USER_ID", "981")
                .d("ROLE_ID", "658")
            .and().insertInto("USER_ROLE", 975L)
                .d("USER_ID", "2097413")
                .d("ROLE_ID", "472")
            .and().insertInto("USER_ROLE", 976L)
                .d("USER_ID", "765")
                .d("ROLE_ID", "-125828396")
            .and().insertInto("USER_ROLE", 977L)
                .d("USER_ID", "5")
                .d("ROLE_ID", "262144")
            .and().insertInto("USER_ROLE", 978L)
                .d("USER_ID", "8389560")
                .d("ROLE_ID", "0")
            .and().insertInto("CUSTOMER_ORDER", 979L)
                .d("ID", "-523979")
                .d("USER_ACCOUNT_ID", "4")
                .d("DATE_CREATED", "\"1963-09-01\"")
                .d("EXECUTED", "false")
                .d("PRODUCTS_COST", "1024")
                .d("DELIVERY_INCLUDED", "true")
                .d("DELIVERY_COST", "2097811")
            .and().insertInto("CUSTOMER_ORDER", 980L)
                .d("ID", "1721")
                .d("USER_ACCOUNT_ID", "-2211037008098573397")
                .d("DATE_CREATED", "\"2001-06-25\"")
                .d("EXECUTED", "true")
                .d("PRODUCTS_COST", "502")
                .d("DELIVERY_INCLUDED", "true")
                .d("DELIVERY_COST", "478")
            .and().insertInto("CUSTOMER_ORDER", 981L)
                .d("ID", "-131071")
                .d("USER_ACCOUNT_ID", "0")
                .d("DATE_CREATED", "\"2067-05-30\"")
                .d("EXECUTED", "false")
                .d("PRODUCTS_COST", "330")
                .d("DELIVERY_INCLUDED", "true")
                .d("DELIVERY_COST", "2097813")
            .and().insertInto("CUSTOMER_ORDER", 982L)
                .d("ID", "33563415")
                .d("USER_ACCOUNT_ID", "0")
                .d("DATE_CREATED", "\"1993-10-31\"")
                .d("EXECUTED", "false")
                .d("PRODUCTS_COST", "781")
                .d("DELIVERY_INCLUDED", "false")
                .d("DELIVERY_COST", "0")
            .and().insertInto("PRODUCT", 998L)
                .d("NAME", "\"_EM_8096_XYZ_\"")
                .d("DISTILLERY_ID", "-2")
                .d("AGE", "735")
                .d("ALCOHOL", "0.923288")
                .d("VOLUME", "-130286")
                .d("PRICE", "0.2756708154744373")
                .d("DESCRIPTION", "\"D3AHIGg79\"")
                .d("AVAILABLE", "false")
            .and().insertInto("PRODUCT", 999L)
                .d("NAME", "\"e_Byeo\"")
                .d("DISTILLERY_ID", "838")
                .d("AGE", "826")
                .d("ALCOHOL", "0.0")
                .d("VOLUME", "66522")
                .d("PRICE", "-0.1624334161590985")
                .d("DESCRIPTION", "\"19LvFSEgjU\"")
                .d("AVAILABLE", "true")
            .and().insertInto("PRODUCT", 1000L)
                .d("NAME", "\"_EM_8097_XYZ_\"")
                .d("DISTILLERY_ID", "91")
                .d("AGE", "-15385")
                .d("ALCOHOL", "0.0")
                .d("VOLUME", "-1024")
                .d("PRICE", "0.0")
                .d("DESCRIPTION", "\"jkFQ\"")
                .d("AVAILABLE", "true")
            .and().insertInto("CART", 1001L)
                .d("TOTAL_ITEMS", "-1537317963")
                .d("PRODUCTS_COST", "4195830")
                .d("DELIVERY_INCLUDED", "false")
            .and().insertInto("DISTILLERY", 1147L)
                .d("TITLE", "\"FjFnjlXS4wR\"")
                .d("REGION_ID", "NULL")
                .d("DESCRIPTION", "\"_EM_4424_XYZ_\"")
            .and().insertInto("DISTILLERY", 1148L)
                .d("TITLE", "\"gkL16vcEXduN\"")
                .d("REGION_ID", "432")
                .d("DESCRIPTION", "\"_EM_4425_XYZ_\"")
            .and().insertInto("ORDERED_PRODUCT", 4025L)
                .d("CUSTOMER_ORDER_ID", "NULL")
                .d("PRODUCT_ID", "NULL")
                .d("QUANTITY", "909")
            .and().insertInto("ORDERED_PRODUCT", 4026L)
                .d("CUSTOMER_ORDER_ID", "521")
                .d("PRODUCT_ID", "NULL")
                .d("QUANTITY", "NULL")
            .and().insertInto("ORDERED_PRODUCT", 4027L)
                .d("CUSTOMER_ORDER_ID", "NULL")
                .d("PRODUCT_ID", "NULL")
                .d("QUANTITY", "37")
            .and().insertInto("BILL", 4028L)
                .d("NUMBER", "615")
                .d("DATE_CREATED", "\"2069-01-21\"")
                .d("TOTAL_COST", "707")
                .d("PAYED", "false")
                .d("CC_NUMBER", "\"KH7\"")
            .and().insertInto("BILL", 4029L)
                .d("NUMBER", "871")
                .d("DATE_CREATED", "NULL")
                .d("TOTAL_COST", "845")
                .d("PAYED", "false")
                .d("CC_NUMBER", "\"Ylzuj\"")
            .and().insertInto("BILL", 4030L)
                .d("NUMBER", "614")
                .d("DATE_CREATED", "\"2014-05-16\"")
                .d("TOTAL_COST", "301")
                .d("PAYED", "false")
                .d("CC_NUMBER", "NULL")
            .dtos();
        InsertionResultsDto insertionsresult = controller.execInsertionsIntoDatabase(insertions);
        ExpectationHandler expectationHandler = expectationHandler();
        
        ValidatableResponse res_0 = given().accept("*/*")
                .header("Authorization", "Basic aXZhbi5wZXRyb3ZAeWFuZGV4LnJ1OnBldHJvdg==") // user
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
                .header("x-EMextraHeader123", "_EM_229_XYZ_")
                .get(baseUrlOfSut + "/customer/orders?name=_EM_227_XYZ_")
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
