package com.llm.backend.repository;

import com.llm.backend.dto.DepartmentDto.DepartmentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentRepositoryCustom {

    Page<DepartmentResponseDto> findDepartmentsWithEmployees(Pageable pageable);
}
