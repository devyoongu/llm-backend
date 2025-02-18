package com.llm.backend.controller;


import com.llm.backend.dto.DepartmentDto.DepartmentResponseDto;
import com.llm.backend.service.DepartmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.llm.backend.dto.CommonResponse;
import com.llm.backend.dto.DepartmentDto.DepartmentSaveRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/department")
    public String findDepartmentsWithEmployees(@PageableDefault(size = 10, sort = "createdDate",direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        List<DepartmentResponseDto> departments = departmentService.findDepartmentsWithEmployees(pageable);

        model.addAttribute("departments", departments);

        return "department";
    }

    @PostMapping("/api/department")
    public ResponseEntity<CommonResponse> saveDepartment(@RequestBody DepartmentSaveRequest request) {
        DepartmentResponseDto savedDepartment = departmentService.saveDepartment(request);
        return ResponseEntity.ok(CommonResponse.ok(savedDepartment));
    }
}
