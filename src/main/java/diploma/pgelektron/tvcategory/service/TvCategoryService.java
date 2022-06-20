package diploma.pgelektron.tvcategory.service;

import diploma.pgelektron.tv.entity.TvEntity;
import diploma.pgelektron.tvcategory.dto.domain.TvCategoryDto;
import diploma.pgelektron.tvcategory.entity.TvCategoryEntity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface TvCategoryService {
    TvCategoryEntity saveTvCategory(TvCategoryEntity category);
    TvCategoryEntity getTvCategoryById(Long id);
    TvCategoryEntity getTvByDescription(String description);
    List<TvCategoryEntity> getAllTvCategories();
    List<TvCategoryDto> getAllTvCategoryDtos();
    TvCategoryDto saveTvCategoryDto(String description);
    TvCategoryEntity findCategoryByDescription(String description);
    String findCategoryDescription(UUID externalCategoryId);

    String findCatById(Long id);
}
