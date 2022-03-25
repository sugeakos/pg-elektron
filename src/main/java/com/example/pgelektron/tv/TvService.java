package com.example.pgelektron.tv;

import com.example.pgelektron.person.Person;
import com.example.pgelektron.tvcategory.TVCategory;

import java.time.LocalDateTime;

public interface TvService {
    TV saveTv(TV tv);
    TV getTvById(Long id);
    TV createNewTv(Person personId, TVCategory tvCategoryId, String errorSeenByCustomer, LocalDateTime reservedDateToRepair);

}
