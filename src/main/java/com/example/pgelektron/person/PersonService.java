package com.example.pgelektron.person;

import com.example.pgelektron.registration.token.ConfirmationToken;
import com.example.pgelektron.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PersonService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "User with email: %s not found";
    private final PersonRepository personRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return personRepository.findPersonByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public String passwordEncoder(String password){
        return bCryptPasswordEncoder.encode(password);
    }

    public Person savePerson(Person person){
       return personRepository.save(person);
    }

    public Person getPersonById(Long id){
        return personRepository.getById(id);
    }

    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        personRepository.findAll().forEach(persons::add);
        return persons;
    }

    public String signUpUser(Person person) {
        boolean userExists = personRepository.findPersonByEmail(person.getEmail()).isPresent();
        boolean confirmedUser = person.isEnabled();
        System.out.println("User enabled: " + confirmedUser);
        if (userExists) {
            System.out.println("User enabled: " + confirmedUser);
            throw new IllegalStateException("Email is already in use");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);

        personRepository.save(person);

        //Create a token and save it
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                person
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        //todo send email
        return token;
    }

    public int enablePerson(String email) {
        return personRepository.enablePerson(email);
    }

}
