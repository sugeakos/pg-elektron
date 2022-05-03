package com.example.pgelektron.repository;

import com.example.pgelektron.domain.TVCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvCategoryRepository extends JpaRepository<TVCategory, Long> {

}
