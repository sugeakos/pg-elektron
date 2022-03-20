package com.example.pgelektron.person;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "User with email: %s not found";
    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return personRepository.findPersonByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND)));
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

}
