package com.llm.backend.repository;

import com.llm.backend.domain.EmployeeJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeJobRepository extends JpaRepository<EmployeeJob, Long> {
} 