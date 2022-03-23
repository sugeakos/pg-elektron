package com.example.pgelektron.tvcategory;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TvCategoryService {

    private final TvCategoryRepository tvCategoryRepository;

    public TVCategory saveTvCategory(TVCategory category) {
        return tvCategoryRepository.save(category);
    }

    public TVCategory getTvCategoryById(Long id){
        return tvCategoryRepository.getById(id);
    }

//    public TVCategory createNewTvCategory(TVCategory tvCategory){
//
//    }

    public List<TVCategory> getAllTvCategories() {
        List<TVCategory> categories = new ArrayList<>();
        tvCategoryRepository.findAll().forEach(categories::add);
        return categories;
    }
}
