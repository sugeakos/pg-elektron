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

    @Query(value = "SELECT * FROM tv where tv.customer_person_id = ?1", nativeQuery = true)
    List<TvEntity> findAllTvByPersonExternalId(UUID personExternalId);
}
