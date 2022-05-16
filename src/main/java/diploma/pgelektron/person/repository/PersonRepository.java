package diploma.pgelektron.person.repository;

import diploma.pgelektron.person.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    PersonEntity findPersonByEmail(String email);
    PersonEntity findPersonByUsername(String username);
    PersonEntity findPersonEntityByExternalId(UUID externalId);
}
