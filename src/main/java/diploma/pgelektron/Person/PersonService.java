package diploma.pgelektron.Person;

import diploma.pgelektron.exception.domain.EmailExistException;
import diploma.pgelektron.exception.domain.EmailNotFoundException;
import diploma.pgelektron.exception.domain.UserNotFoundException;
import diploma.pgelektron.exception.domain.UsernameExistException;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface PersonService {
    Person register (String firstName,
                     String lastName,
                     String username,
                     String email,
                     String password,
                     String phoneFix,
                     String phoneMobile,
                     String address)
            throws UserNotFoundException, EmailExistException, UsernameExistException;
    List<Person> getUsers();
    Person findPersonByUsername(String username);
    Person findPersonByEmail(String email);
    Person addNewUser(String firstName,
                      String lastName,
                      String username,
                      String email,
                      String phoneFix,
                      String phoneMobile,
                      String address,
                      String role,
                      boolean isNonLocked,
                      boolean isActive) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException, MessagingException;

    Person updateUser(String currentUsername,
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
                      boolean isActive,
                      MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException;

    void deleteUser(Long id);
    void resetPassword(String email) throws EmailNotFoundException, MessagingException;

    Person updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException;

}
