package diploma.pgelektron.tvcategory.repository;

import diploma.pgelektron.tvcategory.entity.TvCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TvCategoryRepository extends JpaRepository<TvCategoryEntity, Long> {
    TvCategoryEntity findTvCategoryEntityByDescription(String description);

    @Query(value = "select description from tv_category where external_id = ?1",nativeQuery = true)
    String findDescriptionById(UUID categoryExternalId);
}
