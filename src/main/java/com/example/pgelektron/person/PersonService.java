package com.example.pgelektron.person;

import com.example.pgelektron.person.role.PersonRole;

import java.util.List;

public interface PersonService {
    String passwordEncoder(String password);
    Person savePerson(Person person);
    Person getPersonById(Long id);
    List<Person> getAllPersons();
    Person getPerson(String email);
    String signUpUser(Person person);
    void addRoleToUser(String email, String roleName);
    PersonRole saveRole(PersonRole role);

}
