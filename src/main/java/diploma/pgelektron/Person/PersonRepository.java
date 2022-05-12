package diploma.pgelektron.Person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findPersonByEmail(String email);
    Person findPersonByUsername(String username);
}
