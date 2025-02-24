package com.llm.backend.service;

import com.llm.backend.domain.Department;
import com.llm.backend.domain.Employee;
import com.llm.backend.domain.EmployeeJob;
import com.llm.backend.domain.Job;
import com.llm.backend.dto.EmployeeDto.EmployeeResponseDto;
import com.llm.backend.dto.EmployeeDto.EmployeeSaveRequest;
import com.llm.backend.repository.DepartmentRepository;
import com.llm.backend.repository.EmployeeJobRepository;
import com.llm.backend.repository.EmployeeRegionRepository;
import com.llm.backend.repository.EmployeeRepository;
import com.llm.backend.repository.JobRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final JobRepository jobRepository;
    private final EmployeeJobRepository employeeJobRepository;
    private final EmployeeRegionRepository employeeRegionRepository;

    @Transactional
    public EmployeeResponseDto saveEmployee(EmployeeSaveRequest request) {
        // 1. 부서 조회
        Department department = departmentRepository.findById(request.getDepartmentId())
            .orElseThrow(() -> new IllegalArgumentException("Department not found"));

        // 2. Employee 생성 및 저장
        Employee employee = Employee.builder()
            .userId(request.getUserId())
            .employeeName(request.getEmployeeName())
            .extensionNumber(request.getExtensionNumber())
            .personalPhone(request.getPersonalPhone())
            .department(department)
            .build();

        Employee savedEmployee = employeeRepository.save(employee);

        // 3. EmployeeJob 생성 및 저장
        List<EmployeeJob> employeeJobs = new ArrayList<>();
        if (request.getJobNames() != null && !request.getJobNames().isEmpty()) {
            for (String jobName : request.getJobNames()) {
                Job job = jobRepository.findByName(jobName)
                    .orElseGet(() -> jobRepository.save(Job.builder()
                        .name(jobName)
                        .build()));

                EmployeeJob employeeJob = EmployeeJob.builder()
                    .employee(savedEmployee)
                    .job(job)
                    .build();

                employeeJobs.add(employeeJob);
            }
            employeeJobRepository.saveAll(employeeJobs);
        }


        // 5. Response DTO 생성
        return EmployeeResponseDto.builder()
            .employeeId(savedEmployee.getId())
            .userId(savedEmployee.getUserId())
            .employeeName(savedEmployee.getEmployeeName())
            .extensionNumber(savedEmployee.getExtensionNumber())
            .personalPhone(savedEmployee.getPersonalPhone())
            .departmentId(department.getId())
            .jobNames(employeeJobs.stream()
                .map(ej -> ej.getJob().getName())
                .collect(Collectors.toList()))
            .build();
    }
} 