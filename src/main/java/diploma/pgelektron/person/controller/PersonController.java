package diploma.pgelektron.person.controller;

import diploma.pgelektron.domain.HttpResponse;
import diploma.pgelektron.exception.domain.EmailExistException;
import diploma.pgelektron.exception.domain.EmailNotFoundException;
import diploma.pgelektron.exception.domain.UserNotFoundException;
import diploma.pgelektron.exception.domain.UsernameExistException;
import diploma.pgelektron.exception.handler.ExceptionHandling;
import diploma.pgelektron.person.dto.converter.PersonConverter;
import diploma.pgelektron.person.dto.domain.PersonDto;
import diploma.pgelektron.person.entity.PersonEntity;
import diploma.pgelektron.person.entity.PersonPrincipal;
import diploma.pgelektron.person.service.PersonService;
import diploma.pgelektron.service.EmailService;
import diploma.pgelektron.utility.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static diploma.pgelektron.constant.ResourceControllerConstant.EMAIL_SENT;
import static diploma.pgelektron.constant.ResourceControllerConstant.USER_DELETED_SUCCESSFULLY;
import static diploma.pgelektron.constant.SecurityConstant.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@Validated
@RequiredArgsConstructor
@EnableMethodSecurity
@Transactional
public class PersonController extends ExceptionHandling {
    private final PersonService personService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PersonConverter personConverter;
    private final EmailService emailService;

    @PostMapping("/registration")
    public ResponseEntity<PersonDto> registerNewUser(@Valid @RequestBody PersonDto personDto)
            throws UserNotFoundException, EmailExistException, UsernameExistException, MessagingException {
        PersonDto newPersonDto =
                personService.register(personDto);
        return new ResponseEntity<>(newPersonDto, OK);
    }

    @PostMapping("/login")
    public ResponseEntity<PersonDto> login(@RequestBody PersonDto user) {
        authenticate(user.getUsername(), user.getPassword());
        PersonEntity loginUser = personService.findPersonByUsername(user.getUsername());
        PersonPrincipal personPrincipal = new PersonPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(personPrincipal);
        PersonDto returnUser = personConverter.convertEntityToDto(loginUser);
        return new ResponseEntity<>(returnUser, jwtHeader, OK);
    }

    @RolesAllowed({"ROLE_ADMIN, ROLE_SUPER_ADMIN"})
    @GetMapping("/person/list")
    public ResponseEntity<List<PersonDto>> listAllPersons() {
        List<PersonDto> personEntityList = personService.getUsers();
        return new ResponseEntity<>(personEntityList, OK);
    }

    @RolesAllowed({"ROLE_ADMIN, ROLE_SUPER_ADMIN"})
    @GetMapping("/person/{externalId}")

    public ResponseEntity<PersonDto> getPersonByExternalId(@PathVariable("externalId") UUID externalId) {
        PersonEntity getEntity = personService.findPersonByExternalId(externalId);
        return new ResponseEntity<>(personConverter.convertEntityToDto(getEntity), OK);

    }

    @RolesAllowed({"ROLE_ADMIN, ROLE_SUPER_ADMIN"})
    @PostMapping("/person/add")
    public ResponseEntity<PersonDto> addNewPerson(@Valid @RequestBody PersonDto personDto)
            throws UserNotFoundException, EmailExistException, MessagingException, IOException, UsernameExistException {
        PersonDto newPersonEntity =
                personService.addNewUser(personDto.getFirstName(),
                        personDto.getLastName(),
                        personDto.getUsername(),
                        personDto.getEmail(),
                        personDto.getPhoneFix(),
                        personDto.getPhoneMobile(),
                        personDto.getAddress(),
                        personDto.getRole(),
                        personDto.isNotLocked(),
                        personDto.isActive());

        return new ResponseEntity<>(newPersonEntity, OK);

    }

    @PostMapping("/person/update/{currentUsername}")
    public ResponseEntity<PersonDto> updateUser(@RequestBody PersonDto personDto,
                                                @PathVariable("currentUsername") String currentUsername)
            throws UserNotFoundException, EmailExistException, IOException, UsernameExistException {

        PersonDto updatedPersonDto = personService.updateUser(currentUsername,
                personDto.getFirstName(), personDto.getLastName(), personDto.getUsername(), personDto.getEmail(),
                personDto.getPassword(),
                personDto.getPhoneFix(), personDto.getPhoneMobile(), personDto.getAddress(), personDto.getRole(),
                personDto.isNotLocked(), personDto.isActive());
        return new ResponseEntity<>(updatedPersonDto, OK);
    }

    @GetMapping("/verify/{code}")
    public ResponseEntity<?> verifyUser(@PathVariable("code") String code) {
        if (personService.verifyPerson(code)) {
            return response(OK, "Sikeresen aktiválta a fiókját");
        } else {
            return response(BAD_REQUEST, "Sikertelen aktiválás");
        }
    }

    @GetMapping("/person/reset-password/{email}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email") String email) throws EmailNotFoundException, MessagingException {
        personService.resetPassword(email);
        return response(OK, EMAIL_SENT + email);
    }



    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @RolesAllowed({"ROLE_ADMIN, ROLE_SUPER_ADMIN"})
    @GetMapping("/person/find/{username}")

    public ResponseEntity<PersonEntity> getUser(@PathVariable("username") String username) {
        PersonEntity user = personService.findPersonByUsername(username);
        return new ResponseEntity<>(user, OK);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(
                new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
                httpStatus);
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    private HttpHeaders getJwtHeader(PersonPrincipal personPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(personPrincipal));
        return headers;
    }
}
