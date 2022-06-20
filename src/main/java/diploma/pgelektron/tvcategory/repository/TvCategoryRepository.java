package diploma.pgelektron.tvcategory.repository;

import diploma.pgelektron.tvcategory.entity.TvCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TvCategoryRepository extends JpaRepository<TvCategoryEntity, Long> {
    TvCategoryEntity findTvCategoryEntityByDescription(String description);

    @Query(value = "select description from tv_category where external_id = ?1",nativeQuery = true)
    String findDescriptionById(UUID categoryExternalId);

    @Query(value = "select description from tv_category where id = ?1", nativeQuery = true)
    String findDescripton(Long id);
    @Query(value ="select * from tv_category order by description asc" ,nativeQuery = true)
    List<TvCategoryEntity> findAllCatOrderByDescriptionAsc();
}
