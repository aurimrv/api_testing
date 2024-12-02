
package market.rest;

import market.domain.UserAccount;
import market.dto.UserDTO;
import market.dto.assembler.UserAccountDtoAssembler;
import market.exception.EmailExistsException;
import market.security.AuthenticationService;
import market.service.UserAccountService;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@ExposesResourceFor(UserDTO.class)
public class CustomerRestController {

    private final UserAccountService userAccountService;
    private final AuthenticationService authenticationService;
    private final UserAccountDtoAssembler userAccountDtoAssembler = new UserAccountDtoAssembler();

    public CustomerRestController(UserAccountService userAccountService, AuthenticationService authenticationService) {
        this.userAccountService = userAccountService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/customer")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public UserDTO getCustomer(Principal principal) {
        UserAccount newAccount = userAccountService.findByEmail(principal.getName());
        return toDto(newAccount);
    }

    /**
     * New customer registration.
     *
     * @return newly created account, already authenticated
     * @throws EmailExistsException if the account with the specified email already exists
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createCustomer(@RequestBody @Valid UserDTO user) {
        UserAccount userData = userAccountDtoAssembler.toDomain(user);
        UserAccount newAccount = userAccountService.create(userData);
        authenticationService.authenticate(newAccount.getEmail(), user.getPassword());
        return toDto(newAccount);
    }

    private UserDTO toDto(UserAccount newAccount) {
        UserDTO dto = userAccountDtoAssembler.toModel(newAccount);
        dto.add(linkTo(CartRestController.class).withRel("Shopping cart"));
        dto.add(linkTo(getClass()).withRel("Manage contacts"));
        return dto;
    }
}



import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
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

public class v1_gpt35_run01_CustomerRestControllerTest {

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
    public void testGetCustomerEndpoint() {
        given()
            .when()
            .get(baseUrlOfSut + "/customer")
            .then()
            .statusCode(200);
    }

    @Test
    public void testCreateCustomerEndpoint() {
        given()
            .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
            .header("Content-Type", "application/json")
            .when()
            .post(baseUrlOfSut + "/register")
            .then()
            .statusCode(201);
    }
}
