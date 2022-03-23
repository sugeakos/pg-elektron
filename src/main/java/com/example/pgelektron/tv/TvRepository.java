package com.example.pgelektron.tv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvRepository extends JpaRepository<TV, Long> {
}
