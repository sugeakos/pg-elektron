package com.example.pgelektron.tvcategory;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TvCategoryServiceImpl implements TvCategoryService{

    private final TvCategoryRepository tvCategoryRepository;

    @Override
    public TVCategory saveTvCategory(TVCategory category) {
        return tvCategoryRepository.save(category);
    }

    @Override
    public TVCategory getTvCategoryById(Long id){
        return tvCategoryRepository.getById(id);
    }

//    public TVCategory createNewTvCategory(TVCategory tvCategory){
//
//    }

    @Override
    public List<TVCategory> getAllTvCategories() {
        List<TVCategory> categories = new ArrayList<>();
        tvCategoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    @Override
    public TVCategory getTvByDescription(String description) {
        return null;
    }
}
