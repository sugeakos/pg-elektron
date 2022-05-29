package diploma.pgelektron.person.service;

import diploma.pgelektron.exception.domain.EmailExistException;
import diploma.pgelektron.exception.domain.EmailNotFoundException;
import diploma.pgelektron.exception.domain.UserNotFoundException;
import diploma.pgelektron.exception.domain.UsernameExistException;
import diploma.pgelektron.person.dto.domain.PersonDto;
import diploma.pgelektron.person.entity.PersonEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface PersonService {
    PersonDto register (String firstName,
                        String lastName,
                        String username,
                        String email,
                        String password,
                        String phoneFix,
                        String phoneMobile,
                        String address)
            throws UserNotFoundException, EmailExistException, UsernameExistException;
    List<PersonDto> getUsers();
    PersonEntity findPersonByUsername(String username);
    PersonEntity findPersonByEmail(String email);
    PersonDto addNewUser(String firstName,
                            String lastName,
                            String username,
                            String email,
                            String phoneFix,
                            String phoneMobile,
                            String address,
                            String role,
                            boolean isNonLocked,
                            boolean isActive) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException, MessagingException;

    PersonDto updateUser(String currentUsername,
                            String newFirstName,
                            String newLastName,
                            String newUsername,
                            String newEmail,
                            String newPassword,
                            String newPhoneFix,
                            String newPhoneMobile,
                            String address,
                            String role,
                            boolean isNonLocked,
                            boolean isActive) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException;

    void deleteUser(UUID id);
    void resetPassword(String email) throws EmailNotFoundException, MessagingException;

    PersonEntity findPersonByExternalId(UUID externalId);
    PersonEntity updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException;

}
