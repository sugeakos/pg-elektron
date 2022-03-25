package com.example.pgelektron.tv;

import com.example.pgelektron.customer.Customer;
import com.example.pgelektron.person.Person;
import com.example.pgelektron.tvcategory.TVCategory;

import java.time.LocalDateTime;
import java.util.List;

public interface TvService {
    TV saveTv(TV tv);
    TV getTvById(Long id);
    TV createNewTv(Person person, TVCategory tvCategoryId, String errorSeenByCustomer, LocalDateTime reservedDateToRepair);
    List<TV> findAllTvs();
}
