package diploma.pgelektron.tv.repository;

import diploma.pgelektron.tv.entity.TvEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface TvRepository extends JpaRepository<TvEntity, Long> {
    TvEntity findTvEntityByExternalId(UUID externalId);

    @Query(value = "select * from tv inner join person on tv.person_id = person.id inner join tv_category on tv.tv_category_external = tv_category.id where person.email = ?1 order by tv.reserved_date_to_repair desc", nativeQuery = true)
    List<TvEntity> findAllTvByPersonEmail(String personEmail);

    List<TvEntity> findTvEntitiesByReservedDateToRepair(Date reservedDateToRepair);

    @Query(value = "select * from tv inner join person on tv.person_id = person.id inner join tv_category on tv.tv_category_external = tv_category.id where tv.external_id = ?1", nativeQuery = true)
    TvEntity findTvByExternalId(UUID externalId);
}
