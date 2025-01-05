package com.llm.backend.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DepartmentDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DepartmentResponseDto {
        private Long departmentId;
        private String departmentName;
        private List<EmployeeResponseDto> employees;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployeeResponseDto {
        private Long employeeId;
        private String employeeName;
        private String userId;
        private List<EmployeeJobDto> employeeJobs;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployeeJobDto {
        private Long employeeId;
        private String employeeName;
        private String userId;
    }
}
