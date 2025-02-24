package com.llm.backend.repository;

import com.llm.backend.domain.DepartmentJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentJobRepository extends JpaRepository<DepartmentJob, Long> {
    void deleteByDepartmentId(Long departmentId);
} 