package com.example.pgelektron.person.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<PersonRole, Long > {
    PersonRole findByDescription(String description);
}
