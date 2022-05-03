package com.example.pgelektron.controllers;

import com.example.pgelektron.domain.HttpResponse;
import com.example.pgelektron.domain.Person;
import com.example.pgelektron.domain.PersonPrincipal;
import com.example.pgelektron.exception.domain.EmailExistException;
import com.example.pgelektron.exception.domain.EmailNotFoundException;
import com.example.pgelektron.exception.domain.UserNotFoundException;
import com.example.pgelektron.exception.domain.UsernameExistException;
import com.example.pgelektron.service.PersonService;
import com.example.pgelektron.utility.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;

import static com.example.pgelektron.constant.ResourceControllerConstant.EMAIL_SENT;
import static com.example.pgelektron.constant.ResourceControllerConstant.USER_DELETED_SUCCESSFULLY;
import static com.example.pgelektron.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class PersonController {
    private final PersonService personService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public PersonController(PersonService personService,
                            AuthenticationManager authenticationManager,
                            JwtTokenProvider jwtTokenProvider) {
        this.personService = personService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/registration")
    public ResponseEntity<Person> registration(@RequestBody Person person)
            throws UserNotFoundException, EmailExistException, UsernameExistException {
        Person newPerson =
                personService.register(person.getFirstName(),
                        person.getLastName(),
                        person.getUsername(),
                        person.getEmail(),
                        person.getPassword(),
                        person.getPhoneFix(),
                        person.getPhoneMobile(),
                        person.getAddressLine());
        return new ResponseEntity<>(newPerson, OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Person> login(@RequestBody Person user) {
        authenticate(user.getUsername(), user.getPassword());
        Person loginUser = personService.findPersonByUsername(user.getUsername());
        PersonPrincipal personPrincipal = new PersonPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(personPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
    }

    @GetMapping("/person/list")
    public ResponseEntity<List<Person>> listAllPersons() {
        List<Person> personList = personService.getUsers();
        return new ResponseEntity<>(personList, OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Person> addNewPerson(@RequestParam("firstName") String firstName,
                                               @RequestParam("lastName") String lastName,
                                               @RequestParam("username") String username,
                                               @RequestParam("email") String email,
                                               @RequestParam("phoneFix") String phoneFix,
                                               @RequestParam("phoneMobile") String phoneMobile,
                                               @RequestParam("addressLine") String addressLine,
                                               @RequestParam("role") String role,
                                               @RequestParam("isNonLocked") String isNonLocked,
                                               @RequestParam("isActive") String isActive)
            throws UserNotFoundException, EmailExistException, MessagingException, IOException, UsernameExistException {
        Person newPerson =
                personService.addNewUser(firstName,
                        lastName,
                        username,
                        email,
                        phoneFix,
                        phoneMobile,
                        addressLine,
                        role,
                        Boolean.parseBoolean(isNonLocked),
                        Boolean.parseBoolean(isActive));
        return new ResponseEntity<>(newPerson, OK);

    }

    @PostMapping("/update")
    public ResponseEntity<Person> updateUser(@RequestParam("currentUsername") String currentUsername,
                                                 @RequestParam("firstName") String firstName,
                                                 @RequestParam("lastName") String lastName,
                                                 @RequestParam("username") String username,
                                                 @RequestParam("email") String email,
                                                 @RequestParam("password") String password,
                                                 @RequestParam("role") String role,
                                                 @RequestParam("isActive") String isActive,
                                                 @RequestParam("isNonLocked") String isNonLocked,
                                                 @RequestParam(value = "profileImage", required = false) MultipartFile profileImage)
            throws UserNotFoundException, EmailExistException, IOException, UsernameExistException {

        Person updatedPerson = personService.updateUser(currentUsername, firstName, lastName, username, email, password, role,
                Boolean.parseBoolean(isNonLocked), Boolean.parseBoolean(isActive), profileImage);
        return new ResponseEntity<>(updatedPerson, OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable("id") Long id) {
        personService.deleteUser(id);
        return response(NO_CONTENT, USER_DELETED_SUCCESSFULLY);
    }

    @GetMapping("/reset-password/{email}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email") String email) throws EmailNotFoundException, MessagingException {
        personService.resetPassword(email);
        return response(OK, EMAIL_SENT + email);
    }
    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<Person> getUser(@PathVariable("username") String username) {
        Person user = personService.findPersonByUsername(username);
        return new ResponseEntity<>(user, OK);
    }
    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(
                new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message.toUpperCase()),
                httpStatus);
    }

    private HttpHeaders getJwtHeader(PersonPrincipal personPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(personPrincipal));
        return headers;
    }

}
