package com.example.pgelektron.service;

import com.example.pgelektron.domain.Person;
import com.example.pgelektron.domain.TV;
import com.example.pgelektron.domain.TVCategory;

import java.time.LocalDateTime;
import java.util.List;

public interface TvService {
    TV saveTv(TV tv);
    TV getTvById(Long id);
    TV createNewTv(Person person, TVCategory tvCategoryId, String errorSeenByCustomer, LocalDateTime reservedDateToRepair);
    List<TV> findAllTvs();
}
