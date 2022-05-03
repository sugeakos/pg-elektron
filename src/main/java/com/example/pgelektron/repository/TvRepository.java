package com.example.pgelektron.repository;

import com.example.pgelektron.domain.TV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvRepository extends JpaRepository<TV, Long> {
}
