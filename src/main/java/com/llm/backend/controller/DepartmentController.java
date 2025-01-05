package com.llm.backend.controller;


import com.llm.backend.dto.DepartmentDto.DepartmentResponseDto;
import com.llm.backend.service.DepartmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/department")
    public List<DepartmentResponseDto> findDepartmentsWithEmployees(@PageableDefault(size = 10, sort = "createdDate",direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        return departmentService.findDepartmentsWithEmployees(pageable);
    }
}
