package com.llm.backend.service;

import com.llm.backend.dto.DepartmentDto.DepartmentResponseDto;
import com.llm.backend.repository.DepartmentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public List<DepartmentResponseDto> findDepartmentsWithEmployees(Pageable pageable) {
        Page<DepartmentResponseDto> departments = departmentRepository.findDepartmentsWithEmployees(pageable);

        return departments.getContent();
    }
}
