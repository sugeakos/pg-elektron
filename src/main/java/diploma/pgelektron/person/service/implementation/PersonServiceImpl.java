package diploma.pgelektron.person.service.implementation;

import diploma.pgelektron.enumeration.Role;
import diploma.pgelektron.exception.domain.EmailExistException;
import diploma.pgelektron.exception.domain.EmailNotFoundException;
import diploma.pgelektron.exception.domain.UserNotFoundException;
import diploma.pgelektron.exception.domain.UsernameExistException;
import diploma.pgelektron.person.dto.converter.PersonConverter;
import diploma.pgelektron.person.dto.domain.PersonDto;
import diploma.pgelektron.person.entity.PersonPrincipal;
import diploma.pgelektron.person.repository.PersonRepository;
import diploma.pgelektron.person.entity.PersonEntity;
import diploma.pgelektron.person.service.PersonService;
import diploma.pgelektron.service.EmailService;
import diploma.pgelektron.service.LoginAttemptService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.data.domain.Sort;

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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static diploma.pgelektron.constant.FileConstant.*;
import static diploma.pgelektron.constant.PersonServiceImplConstant.*;
import static diploma.pgelektron.enumeration.Role.ROLE_USER;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.lang3.StringUtils.*;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@Qualifier("userDetailsService")
public class PersonServiceImpl implements PersonService, UserDetailsService {

    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final LoginAttemptService loginAttemptService;
    private final EmailService emailService;
    private final PersonConverter personConverter;
    private final ModelMapper mapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PersonEntity user = personRepository.findPersonEntityByUsername(username);
        if (user == null) {
            log.error("User not found by username: " + username);
            throw new UsernameNotFoundException("User not found by username: " + username);
        } else {
            validateLoginAttempt(user);
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            personRepository.save(user);
            PersonPrincipal personPrincipal = new PersonPrincipal(user);
            log.info("User found by username: " + username);
            return personPrincipal;
        }
    }


    @Override
    public PersonDto register(PersonDto personDto)
            throws UserNotFoundException, EmailExistException, UsernameExistException, MessagingException {
        validateNewUsernameAndEmail(EMPTY, personDto.getUsername(), personDto.getEmail());

        personDto.setExternalId(generateNewExternalId());
        personDto.setPassword(bCryptPasswordEncoder.encode(personDto.getPassword()));
        personDto.setJoinDate(new Date());
        personDto.setActive(false);
        personDto.setNotLocked(true);
        personDto.setRole(ROLE_USER.name());
        personDto.setAuthorities(ROLE_USER.getAuthorities());
        personDto.setVerification(generateNewExternalId().toString());
        PersonEntity savedUser = personConverter.convertDtoToEntity(personDto);
        personRepository.save(savedUser);
        emailService.sendVerificationEmail(personDto);
        return personDto;
    }


    @Override
    public PersonDto addNewUser(String firstName,
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

        String tempUsername = generateNewUsername(firstName);
        if (username.equals("")) {

            validateNewUsernameAndEmail(EMPTY, tempUsername, email);
        } else {
            validateNewUsernameAndEmail(EMPTY, username, email);
        }

        String tempPassword = generateRandomNewPassword();
        log.info("New password is: " + tempPassword);
        PersonEntity user = new PersonEntity();
        user.setExternalId(generateNewExternalId());
        user.setFirstName(firstName);
        user.setLastName(lastName);

        if (username.equals("")) {
            user.setUsername(tempUsername);
        } else {
            user.setUsername(username);
        }

        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(tempPassword));

        user.setPhoneFix(phoneFix);
        user.setPhoneMobile(phoneMobile);
        user.setAddress(address);
        user.setJoinDate(new Date());
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(getRoleEnumName(role).name());
        user.setAuthorities(getRoleEnumName(role).getAuthorities());
        personRepository.save(user);
        PersonDto returnUser = personConverter.convertEntityToDto(user);
        emailService.sendNewUserEmail(firstName, tempPassword, email, tempUsername);
        emailService.sendVerificationEmail(returnUser);
        return returnUser;
    }

    @Override
    public List<PersonDto> getUsers() {
        List<PersonEntity> entityList = personRepository.findAll(Sort.by("lastName").ascending());
        return entityList.stream()
                .map(entity -> mapper.map(entity, PersonDto.class)).collect(Collectors.toList());
    }

    @Override
    public PersonEntity findPersonByUsername(String username) {
        return personRepository.findPersonEntityByUsername(username);
    }

    @Override
    public PersonEntity findPersonByEmail(String email) {
        return personRepository.findPersonEntityByEmail(email);
    }
    @Override
    public PersonDto updateUser(String currentUsername,
                                String newFirstName,
                                String newLastName,
                                String newUsername,
                                String newEmail,
                                String newPassword,
                                String newPhoneFix,
                                String newPhoneMobile,
                                String newAddress,
                                String role,
                                boolean isNonLocked,
                                boolean isActive)
            throws UserNotFoundException, EmailExistException, UsernameExistException, IOException {
        PersonEntity currentUser = validateNewUsernameAndEmail(currentUsername, newUsername, newEmail);


        if (newFirstName.equals("")) {

            currentUser.setFirstName(currentUser.getFirstName());
        } else {

            currentUser.setFirstName(newFirstName);
        }

        if (newLastName.equals("")) {
            currentUser.setLastName(currentUser.getLastName());
        } else {
            currentUser.setLastName(newLastName);
        }

        if (currentUser.getUsername().equals(newUsername)) {
            currentUser.setUsername(currentUser.getUsername());
        } else {
            currentUser.setUsername(newUsername);
        }

        if (currentUser.getEmail().equals(newEmail)) {
            currentUser.setEmail(currentUser.getEmail());
        } else {
            currentUser.setEmail(newEmail);
        }

        if (newPassword.equals("")) {
            bCryptPasswordEncoder.upgradeEncoding(currentUser.getPassword());
        } else {

            currentUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
        }

        if (newPhoneFix.equals("")) {
            currentUser.setPhoneFix(currentUser.getPhoneFix());
        } else {
            currentUser.setPhoneFix(newPhoneFix);
        }

        if (newPhoneMobile.equals("")) {
            currentUser.setPhoneFix(currentUser.getPhoneMobile());
        } else {
            currentUser.setPhoneMobile(newPhoneMobile);
        }

        if (newAddress.equals("")) {
            currentUser.setAddress(currentUser.getAddress());
        } else {
            currentUser.setAddress(newAddress);
        }

        if (!role.equals("")) {
            currentUser.setRole(getRoleEnumName(role).name());
            currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());
        } else {
            currentUser.setRole(getRoleEnumName(currentUser.getRole().toUpperCase()).name());
            currentUser.setAuthorities(getRoleEnumName(currentUser.getRole().toUpperCase()).getAuthorities());
        }

        log.info("Current username: " + currentUser.getUsername());
        personRepository.save(currentUser);
        return personConverter.convertEntityToDto(currentUser);
    }

    @Override
    public void resetPassword(String email) throws EmailNotFoundException, MessagingException {
        PersonEntity user = personRepository.findPersonEntityByEmail(email);
        if (user == null) {
            throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL + email);
        }
        String password = generateRandomNewPassword();
        log.info(password);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        personRepository.save(user);
        emailService.sendNewPasswordEmail(user.getFirstName(), password, user.getEmail());
    }

    @Override
    public boolean verifyPerson(String verification) {
        PersonEntity person = personRepository.findPersonByVerification(verification);
        if (person == null || person.isActive()) {
            return false;
        } else {
            person.setVerification(null);
            person.setActive(true);
            personRepository.save(person);
            return true;
        }
    }

    @Override
    public PersonEntity updateProfileImage(String username, MultipartFile profileImage)
            throws UserNotFoundException, EmailExistException, UsernameExistException, IOException {
        PersonEntity personEntity = validateNewUsernameAndEmail(username, null, null);
        saveProfileImage(personEntity, profileImage);
        return null;
    }

    @Override
    public PersonEntity findPersonByExternalId(UUID externalId) {
        return personRepository.findPersonEntityByExternalId(externalId);
    }

    private void validateLoginAttempt(PersonEntity user) {
        if (user.isNotLocked()) {
            if(loginAttemptService.hasExceededMaxAttempts(user.getUsername())){
                user.setNotLocked(false);
            }

        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }

    private PersonEntity validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail)
            throws UserNotFoundException, UsernameExistException, EmailExistException {
        PersonEntity personEntityByNewUsername = findPersonByUsername(newUsername);
        PersonEntity personEntityByNewEmail = findPersonByEmail(newEmail);

        if (isNotBlank(currentUsername)) {
            PersonEntity currentUser = findPersonByUsername(currentUsername);
            if (currentUser == null) {
                throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + " " + currentUsername);
            }
            if (personEntityByNewUsername != null && !currentUser.getId().equals(personEntityByNewUsername.getId())) {
                throw new UsernameExistException(USERNAME_IS_ALREADY_IN_USE);
            }
            if (personEntityByNewEmail != null && !currentUser.getId().equals(personEntityByNewEmail.getId())) {
                throw new EmailExistException(EMAIL_IS_ALREADY_IN_USE);
            }
            return currentUser;
        } else {
            if (personEntityByNewUsername != null) {
                throw new UsernameExistException(USERNAME_IS_ALREADY_IN_USE);
            }

            if (personEntityByNewEmail != null) {
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

    private String generateNewUsername(String firstName) {
        return firstName.toLowerCase() + "-" + LocalDateTime.now().getSecond();

    }

    private void saveProfileImage(PersonEntity user, MultipartFile profileImage) throws IOException {
        if (profileImage != null) {
            Path userFolder = Paths.get(USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
            if (!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
                log.info(DIRECTORY_CREATED + userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + user.getUsername() + DOT + JPG_EXTENSION));

            Files.copy(profileImage.getInputStream(), userFolder.resolve(user.getUsername() + DOT + JPG_EXTENSION), REPLACE_EXISTING);

            user.setProfileImageUrl(setProfileImageUrl(user.getUsername()));
            personRepository.save(user);
            log.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
        }
    }

    private String setProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(USER_IMAGE_PATH + username + FORWARD_SLASH + username + DOT + JPG_EXTENSION).toUriString();
    }

    public UUID generateNewExternalId() {
        return UUID.randomUUID();
    }

}
