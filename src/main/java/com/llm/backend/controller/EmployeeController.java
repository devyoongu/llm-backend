package com.llm.backend.controller;

import com.llm.backend.dto.CommonResponse;
import com.llm.backend.dto.EmployeeDto.EmployeeResponseDto;
import com.llm.backend.dto.EmployeeDto.EmployeeSaveRequest;
import com.llm.backend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/api/employee")
    public ResponseEntity<CommonResponse> saveEmployee(@RequestBody EmployeeSaveRequest request) {
        EmployeeResponseDto savedEmployee = employeeService.saveEmployee(request);
        return ResponseEntity.ok(CommonResponse.ok(savedEmployee));
    }
} 