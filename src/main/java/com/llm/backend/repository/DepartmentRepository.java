package com.llm.backend.repository;

import com.llm.backend.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long>, DepartmentRepositoryCustom {

}
