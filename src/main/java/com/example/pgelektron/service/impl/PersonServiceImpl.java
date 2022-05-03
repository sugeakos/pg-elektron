package com.example.pgelektron.service.impl;

import com.example.pgelektron.domain.Person;
import com.example.pgelektron.domain.PersonPrincipal;
import com.example.pgelektron.enumeration.Role;
import com.example.pgelektron.exception.domain.EmailExistException;
import com.example.pgelektron.exception.domain.EmailNotFoundException;
import com.example.pgelektron.exception.domain.UserNotFoundException;
import com.example.pgelektron.exception.domain.UsernameExistException;
import com.example.pgelektron.repository.PersonRepository;

import com.example.pgelektron.service.EmailService;
import com.example.pgelektron.service.LoginAttemptService;
import com.example.pgelektron.service.PersonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import static com.example.pgelektron.constant.FileConstant.*;
import static com.example.pgelektron.constant.PersonServiceImplConstant.*;
import static com.example.pgelektron.enumeration.Role.ROLE_USER;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.lang3.StringUtils.*;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@Qualifier("userDetailsService")
public class PersonServiceImpl implements UserDetailsService, PersonService {
    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final LoginAttemptService loginAttemptService;
    private final EmailService emailService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person user = personRepository.findPersonByUsername(username);
        if (user == null) {
            log.error("User not found by username: " + username);
            throw new UsernameNotFoundException("User not found by username: " + username);
        } else {
            validateLoginAttempt(user);
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            personRepository.save(user);
            PersonPrincipal personPrincipal = new PersonPrincipal(user);
            log.info("User not found by username: " + username);
            return personPrincipal;
        }
    }


    @Override
    public Person register(String firstName,
                           String lastName,
                           String username,
                           String email,
                           String password,
                           String phoneFix,
                           String phoneMobile,
                           String address)
            throws UserNotFoundException, EmailExistException, UsernameExistException{
        validateNewUsernameAndEmail(EMPTY, username, email);
        Person user = new Person();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setPhoneFix(phoneFix);
        user.setPhoneMobile(phoneMobile);
        user.setAddressLine(address);
        user.setJoinDate(new Date());
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(ROLE_USER.name());
        user.setAuthorities(ROLE_USER.getAuthorities());
        personRepository.save(user);
        return user;
    }


    @Override
    public Person addNewUser(String firstName,
                             String lastName,
                             String username,
                             String email,
                             String phoneFix,
                             String phoneMobile,
                             String address,
                             String role,
                             boolean isNonLocked,
                             boolean isActive)
            throws UserNotFoundException, EmailExistException, UsernameExistException, IOException, MessagingException {

        validateNewUsernameAndEmail(EMPTY, username, email);
        String tempPassword = generateRandomNewPassword();
        log.info("New password is: " + tempPassword);
        Person user = new Person();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(tempPassword));

        user.setPhoneFix(phoneFix);
        user.setPhoneMobile(phoneMobile);
        user.setAddressLine(address);
        user.setJoinDate(new Date());
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(getRoleEnumName(role).name());
        user.setAuthorities(getRoleEnumName(role).getAuthorities());
        personRepository.save(user);
        emailService.sendNewPasswordEmail(firstName, tempPassword, email);
        return user;
    }

    @Override
    public List<Person> getUsers() {
        return personRepository.findAll();
    }

    @Override
    public Person findPersonByUsername(String username) {
        return personRepository.findPersonByUsername(username);
    }

    @Override
    public Person findPersonByEmail(String email) {
        return personRepository.findPersonByEmail(email);
    }


    @Override
    public Person updateUser(String currentUsername,
                             String newFirstName,
                             String newLastName,
                             String newUsername,
                             String newEmail,
                             String newPassword,
                             String role,
                             boolean isNonLocked,
                             boolean isActive,
                             MultipartFile profileImage)
            throws UserNotFoundException, EmailExistException, UsernameExistException, IOException {
        Person currentUser = validateNewUsernameAndEmail(currentUsername, newUsername, newEmail);


        currentUser.setFirstName(newFirstName);
        currentUser.setLastName(newLastName);
        currentUser.setUsername(newUsername);
        currentUser.setEmail(newEmail);
        currentUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
        currentUser.setActive(isActive);
        currentUser.setNotLocked(isNonLocked);
        currentUser.setRole(getRoleEnumName(role).name());
        currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());

        personRepository.save(currentUser);
        saveProfileImage(currentUser, profileImage);
        return currentUser;
    }

    @Override
    public void deleteUser(Long id) {
    personRepository.deleteById(id);
    }

    @Override
    public void resetPassword(String email) throws EmailNotFoundException, MessagingException {
        Person user = personRepository.findPersonByEmail(email);
        if (user == null) {
            throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL + email);
        }
        String password = generateRandomNewPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        personRepository.save(user);
        emailService.sendNewPasswordEmail(user.getFirstName(), password,user.getEmail());
    }

    @Override
    public Person updateProfileImage(String username, MultipartFile profileImage)
            throws UserNotFoundException, EmailExistException, UsernameExistException, IOException {
        Person person = validateNewUsernameAndEmail(username, null, null);
        saveProfileImage(person, profileImage);
        return null;
    }


    private void validateLoginAttempt(Person user) {
        if (user.isNotLocked()) {
            if (loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
                user.setNotLocked(false);
            } else {
                user.setNotLocked(true);
            }
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }

    private Person validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail)
            throws UserNotFoundException, UsernameExistException, EmailExistException {
        Person personByNewUsername = findPersonByUsername(newUsername);
        Person personByNewEmail = findPersonByEmail(newEmail);

        if (isNotBlank(currentUsername)) {
            Person currentUser = findPersonByUsername(currentUsername);
            if (currentUser == null) {
                throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + " " + currentUsername);
            }
            if (personByNewUsername != null && !currentUser.getId().equals(personByNewUsername.getId())) {
                throw new UsernameExistException(USERNAME_IS_ALREADY_IN_USE);
            }
            if (personByNewEmail != null && !currentUser.getId().equals(personByNewEmail.getId())) {
                throw new EmailExistException(EMAIL_IS_ALREADY_IN_USE);
            }
            return currentUser;
        } else {
            if (personByNewUsername != null) {
                throw new UsernameExistException(USERNAME_IS_ALREADY_IN_USE);
            }

            if (personByNewEmail != null) {
                throw new EmailExistException(EMAIL_IS_ALREADY_IN_USE);
            }
            return null;
        }
    }

    private String generateRandomNewPassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private Role getRoleEnumName(String role) {
        return Role.valueOf(role.toUpperCase());
    }

    private void saveProfileImage(Person user, MultipartFile profileImage) throws IOException {
        if (profileImage != null) {
            Path userFolder = Paths.get(USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
            if (!Files.exists(userFolder)){
                Files.createDirectories(userFolder);
                log.info(DIRECTORY_CREATED + userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + user.getUsername() + DOT + JPG_EXTENSION));

            Files.copy(profileImage.getInputStream(),userFolder.resolve(user.getUsername() + DOT + JPG_EXTENSION), REPLACE_EXISTING);

            user.setProfileImageUrl(setProfileImageUrl(user.getUsername()));
            personRepository.save(user);
            log.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
        }
    }

    private String setProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(USER_IMAGE_PATH + username + FORWARD_SLASH + username + DOT + JPG_EXTENSION).toUriString();
    }
}
