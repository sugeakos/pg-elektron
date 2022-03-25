package com.example.pgelektron.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findPersonByEmail(String email);


    @Transactional
    @Modifying
    @Query("UPDATE Person a " +
            "set a.enabled = TRUE where a.email = ?1")
    int enablePerson(String email);
}
