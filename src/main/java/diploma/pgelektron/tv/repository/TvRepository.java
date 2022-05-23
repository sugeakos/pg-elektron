package diploma.pgelektron.tv.repository;

import diploma.pgelektron.tv.entity.TvEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface TvRepository extends JpaRepository<TvEntity, Long> {
    TvEntity findTvEntityByExternalId(UUID externalId);

    @Query(value = "select * from tv inner join person on tv.person_id = person.id inner join tv_category on tv.tv_category_external = tv_category.id where person.email = ?1", nativeQuery = true)
    List<TvEntity> findAllTvByPersonEmail(String personEmail);
}
