package com.llm.backend.repository;

import static com.llm.backend.domain.QDepartment.department;
import static com.llm.backend.domain.QEmployee.employee;
import static com.llm.backend.domain.QEmployeeJob.employeeJob;
import static com.llm.backend.domain.QEmployeeRegion.employeeRegion;

import com.llm.backend.dto.DepartmentDto.DepartmentResponseDto;
import com.llm.backend.dto.DepartmentDto.EmployeeJobDto;
import com.llm.backend.dto.DepartmentDto.EmployeeRegionDto;
import com.llm.backend.dto.DepartmentDto.EmployeeResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class DepartmentRepositoryImpl implements  DepartmentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<DepartmentResponseDto> findDepartmentsWithEmployees(Pageable pageable) {
        // 1. 부서 기본 정보 조회
        List<DepartmentResponseDto> results = queryFactory
            .select(Projections.fields(
                DepartmentResponseDto.class,
                department.id.as("departmentId"),
                department.departmentName.as("departmentName")
            ))
            .from(department)
            .orderBy(department.id.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        // 2. 각 부서에 속한 직원 리스트 추가 조회
        results.forEach(dto -> {
            List<EmployeeResponseDto> employees = queryFactory
                .select(Projections.fields(
                    EmployeeResponseDto.class,
                    employee.id.as("employeeId"),
                    employee.employeeName.as("employeeName"),
                    employee.userId
                ))
                .from(employee)
                .where(employee.department.id.eq(dto.getDepartmentId()))
                .fetch();

            // 직원별 employeeJobs와 employeeRegions 추가 조회
            employees.forEach(emp -> {
                List<EmployeeJobDto> jobs = queryFactory
                    .select(Projections.fields(
                        EmployeeJobDto.class,
                        employeeJob.employee.id.as("employeeId"),
                        employeeJob.job.name.as("jobName")
                    ))
                    .from(employeeJob)
                    .where(employeeJob.employee.id.eq(emp.getEmployeeId()))
                    .fetch();

                emp.setEmployeeJobs(jobs);

                List<EmployeeRegionDto> regions = queryFactory
                    .select(Projections.fields(
                        EmployeeRegionDto.class,
                        employeeRegion.employee.id.as("employeeId"),
                        employeeRegion.region.name.as("region")
                    ))
                    .from(employeeRegion)
                    .where(employeeRegion.employee.id.eq(emp.getEmployeeId()))
                    .fetch();

                emp.setEmployeeRegions(regions);
            });

            dto.setEmployees(employees);
        });

        // 3. 전체 데이터 수 조회
        long total = queryFactory
            .select(department.count())
            .from(department)
            .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

}
