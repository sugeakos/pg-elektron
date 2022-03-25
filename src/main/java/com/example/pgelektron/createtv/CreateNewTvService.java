package com.example.pgelektron.createtv;

import com.example.pgelektron.person.PersonServiceImpl;
import com.example.pgelektron.tvcategory.TvCategoryServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateNewTvService {
    private final PersonServiceImpl personService;
    private final TvCategoryServiceImpl tvCategoryService;

    public String createNewTv(ResponseEntity responseEntity){
        return null;
    }
}
