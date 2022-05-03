package com.example.pgelektron.repository;

import com.example.pgelektron.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findPersonByEmail(String email);
    Person findPersonByUsername(String username);

}
