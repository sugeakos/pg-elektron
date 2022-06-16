package diploma.pgelektron.person.repository;

import diploma.pgelektron.person.entity.PersonEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<PersonEntity, Long>, PagingAndSortingRepository<PersonEntity,Long> {
    PersonEntity findPersonEntityByEmail(String email);
    PersonEntity findPersonEntityByUsername(String username);
    PersonEntity findPersonEntityByExternalId(UUID externalId);

    @Query(value ="Select * from person order by first_name desc" ,nativeQuery = true)
    List<PersonEntity> findAllOrderByFirstNameAsc();


}
