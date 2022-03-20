package com.example.pgelektron.tvcategory;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
