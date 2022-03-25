package com.example.pgelektron.tvcategory;

import java.util.List;

public interface TvCategoryService {
    TVCategory saveTvCategory(TVCategory category);
    TVCategory getTvCategoryById(Long id);
    TVCategory getTvByDescription(String description);
    List<TVCategory> getAllTvCategories();

}
