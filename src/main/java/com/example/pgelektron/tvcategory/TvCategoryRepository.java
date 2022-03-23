package com.example.pgelektron.tvcategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvCategoryRepository extends JpaRepository<TVCategory, Long> {

}
