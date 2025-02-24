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
        private String mainPhone;
        private List<EmployeeResponseDto> employees;
        private List<DepartmentJobDto> departmentJobs;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployeeResponseDto {
        private Long employeeId;
        private String employeeName;
        private String personalPhone;
        private List<EmployeeJobDto> employeeJobs;
        private List<EmployeeRegionDto> employeeRegions;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployeeJobDto {
        private Long employeeId;
        private String jobName;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DepartmentJobDto {
        private Long departmentId;
        private String jobName;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployeeRegionDto {
        private Long employeeId;
        private String region;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DepartmentSaveRequest {
        private String departmentName;
        private String mainPhone;
        private Long parentId;
        private int depth;
        private List<String> jobNames; // 부서에 할당할 직무 이름 목록
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DepartmentUpdateRequest {
        private Long departmentId;
        private String departmentName;
        private String mainPhone;
        private Long parentId;
        private int depth;
        private List<String> jobNames;
    }
}
