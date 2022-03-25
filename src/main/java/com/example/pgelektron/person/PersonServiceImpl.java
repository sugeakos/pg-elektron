package com.example.pgelektron.person;

import com.example.pgelektron.person.role.PersonRole;
import com.example.pgelektron.registration.token.ConfirmationToken;
import com.example.pgelektron.registration.token.ConfirmationTokenService;
import com.example.pgelektron.person.role.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PersonServiceImpl implements UserDetailsService, PersonService {

    private final static String USER_NOT_FOUND = "User not found";
    private final PersonRepository personRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personRepository.findPersonByEmail(email);
        if (person == null){
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        } else {
            log.info("User found in the database: {}", email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        person.getUserRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getDescription()));
        });
        return new User(person.getUsername(),person.getPassword(), authorities);
    }

    @Override
    public String passwordEncoder(String password){
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public Person savePerson(Person person){
       return personRepository.save(person);
    }

    @Override
    public Person getPersonById(Long id){
        return personRepository.getById(id);
    }

    @Override
    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        personRepository.findAll().forEach(persons::add);
        return persons;
    }

    @Override
    public String signUpUser(Person person) {
        Person userExists = personRepository.findPersonByEmail(person.getEmail());

        boolean confirmedUser = person.isEnabled();
        System.out.println("User enabled: " + confirmedUser);

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

    @Override
    public void addRoleToUser(String username, String roleName) {
        Person person = personRepository.findPersonByEmail(username);
        PersonRole role = roleRepository.findByDescription(roleName);
        person.getUserRole().add(role);
    }

    @Override
    public Person getPerson(String email) {
        return personRepository.findPersonByEmail(email);
    }

    @Override
    public PersonRole saveRole(PersonRole role) {
        return roleRepository.save(role);
    }
    public PersonRole findRoleByDescription(String description) {
        return roleRepository.findByDescription(description);
    }
}
