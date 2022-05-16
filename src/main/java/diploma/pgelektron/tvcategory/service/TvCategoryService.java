package diploma.pgelektron.tvcategory.service;

import diploma.pgelektron.tvcategory.dto.domain.TvCategoryDto;
import diploma.pgelektron.tvcategory.entity.TvCategoryEntity;

import java.util.Collection;
import java.util.List;

public interface TvCategoryService {
    TvCategoryEntity saveTvCategory(TvCategoryEntity category);
    TvCategoryEntity getTvCategoryById(Long id);
    TvCategoryEntity getTvByDescription(String description);
    List<TvCategoryEntity> getAllTvCategories();
    List<TvCategoryDto> getAllTvCategoryDtos();
    TvCategoryDto saveTvCategoryDto(TvCategoryDto tvCategoryDto);
}
