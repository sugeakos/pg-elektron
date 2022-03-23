package com.example.pgelektron.createtv;

import com.example.pgelektron.person.PersonService;
import com.example.pgelektron.tvcategory.TvCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateNewTvService {
    private final PersonService personService;
    private final TvCategoryService tvCategoryService;

    public String createNewTv(ResponseEntity responseEntity){
        return null;
    }
}
