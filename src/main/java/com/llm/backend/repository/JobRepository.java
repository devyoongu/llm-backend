package com.llm.backend.repository;

import com.llm.backend.domain.Job;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
    Optional<Job> findByName(String name);
} 