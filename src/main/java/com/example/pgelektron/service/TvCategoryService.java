package com.example.pgelektron.service;

import com.example.pgelektron.domain.TVCategory;

import java.util.List;

public interface TvCategoryService {
    TVCategory saveTvCategory(TVCategory category);
    TVCategory getTvCategoryById(Long id);
    TVCategory getTvByDescription(String description);
    List<TVCategory> getAllTvCategories();

}
