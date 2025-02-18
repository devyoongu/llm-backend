package com.llm.backend.service;

import com.llm.backend.domain.Department;
import com.llm.backend.domain.DepartmentJob;
import com.llm.backend.domain.Job;
import com.llm.backend.dto.DepartmentDto.DepartmentJobDto;
import com.llm.backend.dto.DepartmentDto.DepartmentResponseDto;
import com.llm.backend.dto.DepartmentDto.DepartmentSaveRequest;
import com.llm.backend.repository.DepartmentRepository;
import com.llm.backend.repository.JobRepository;
import com.llm.backend.repository.DepartmentJobRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    private final JobRepository jobRepository;
    private final DepartmentJobRepository departmentJobRepository;

    @Transactional(readOnly = true)
    public List<DepartmentResponseDto> findDepartmentsWithEmployees(Pageable pageable) {
        Page<DepartmentResponseDto> departments = departmentRepository.findDepartmentsWithEmployees(pageable);

        return departments.getContent();
    }

    @Transactional
    public DepartmentResponseDto saveDepartment(DepartmentSaveRequest request) {
        Department department = Department.builder()
            .departmentName(request.getDepartmentName())
            .mainPhone(request.getMainPhone())
            .parentId(request.getParentId())
            .depth(request.getDepth())
            .build();

        Department savedDepartment = departmentRepository.save(department);

        List<DepartmentJob> departmentJobs = new ArrayList<>();
        if (request.getJobNames() != null && !request.getJobNames().isEmpty()) {
            for (String jobName : request.getJobNames()) {
                Job job = jobRepository.findByName(jobName)
                    .orElseGet(() -> jobRepository.save(Job.builder()
                        .name(jobName)
                        .build()));

                DepartmentJob departmentJob = DepartmentJob.builder()
                    .department(savedDepartment)
                    .job(job)
                    .build();

                departmentJobs.add(departmentJob);
            }
            
            departmentJobRepository.saveAll(departmentJobs);
        }

        return DepartmentResponseDto.builder()
            .departmentId(savedDepartment.getId())
            .departmentName(savedDepartment.getDepartmentName())
            .mainPhone(savedDepartment.getMainPhone())
            .departmentJobs(departmentJobs.stream()
                .map(dj -> DepartmentJobDto.builder()
                    .departmentId(dj.getDepartment().getId())
                    .jobName(dj.getJob().getName())
                    .build())
                .collect(Collectors.toList()))
            .build();
    }
}
