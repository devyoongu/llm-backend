package com.llm.backend.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class EmployeeDto {
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployeeSaveRequest {
        private String userId;
        private String employeeName;
        private String extensionNumber;
        private String personalPhone;
        private Long departmentId;
        private List<String> jobNames;
        private List<String> regions;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployeeResponseDto {
        private Long employeeId;
        private String userId;
        private String employeeName;
        private String extensionNumber;
        private String personalPhone;
        private Long departmentId;
        private List<String> jobNames;
        private List<String> regions;
    }
} 