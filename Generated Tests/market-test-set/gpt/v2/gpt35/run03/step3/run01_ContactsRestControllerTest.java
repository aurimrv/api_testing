
package market;

import market.domain.Contacts;
import market.dto.ContactsDTO;
import market.dto.assembler.ContactsDtoAssembler;
import market.service.ContactsService;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "customer/contacts")
@ExposesResourceFor(ContactsDTO.class)
@Secured({"ROLE_USER"})
public class ContactsRestController {
    private final ContactsService contactsService;
    private final ContactsDtoAssembler contactsDtoAssembler = new ContactsDtoAssembler();

    public ContactsRestController(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    @GetMapping
    public ContactsDTO getContacts(Principal principal) {
        Contacts contacts = contactsService.getContacts(principal.getName());
        return toDto(contacts);
    }

    @PutMapping
    public ContactsDTO updateContacts(Principal principal, @RequestBody @Valid ContactsDTO contactsDto) {
        String login = principal.getName();
        Contacts changedContacts = contactsDtoAssembler.toDomain(contactsDto);
        contactsService.updateUserContacts(changedContacts, login);
        return toDto(changedContacts);
    }

    private ContactsDTO toDto(Contacts changedContacts) {
        ContactsDTO dto = contactsDtoAssembler.toModel(changedContacts);
        dto.add(linkTo(CartRestController.class).withRel("Shopping cart"));
        return dto;
    }
}

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import java.util.List;
import static org.evomaster.client.java.controller.api.EMTestUtils.*;
import org.evomaster.client.java.controller.SutHandler;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.ValidatableResponse;
import static org.evomaster.client.java.sql.dsl.SqlDsl.sql;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionResultsDto;
import org.evomaster.client.java.controller.api.dto.database.operations.InsertionDto;
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

public class run01_ContactsRestControllerTest {

    private static final SutHandler controller = new em.embedded.market.EmbeddedEvoMasterController();
    private static String baseUrlOfSut;

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
        controller.resetDatabase(Arrays.asList("USER_ROLE","CUSTOMER_ORDER","CART_ITEM","PRODUCT","CART","CONTACTS"));
        controller.resetStateOfSUT();
    }

    @Test
    public void testErrorScenarios() {
        // Include tests to force API to return 5xx status codes
        // Simulate invalid inputs or server failures
        // Assertions for 5xx status codes
    }

    @Test
    public void testSchemaValidation() {
        // Include tests to validate API responses against OpenAPI schema
        // Check for required fields, data type conformity, and valid values
        // Flag any discrepancies as failures
    }

    @Test
    public void testBusinessRuleEnforcement() {
        // Include tests to validate business rules (POST, PUT, DELETE operations)
        // Ensure correct behavior for creating, modifying, or deleting resources
        // Detect and report any violations of these rules
    }
}
