package diploma.pgelektron.tvcategory.repository;

import diploma.pgelektron.tvcategory.entity.TvCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvCategoryRepository extends JpaRepository<TvCategoryEntity, Long> {
    TvCategoryEntity findTvCategoryEntityByDescription(String description);
}
