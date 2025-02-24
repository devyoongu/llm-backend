package com.llm.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
public class EmbeddingFileDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmbeddingFileRequest {
        private String fileName;
        private String fileType;
        private Long fileSize;
        private boolean uploadSuccess;
        private String errorMessage;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmbeddingFileResponseDto {
        private Long id;
        private String fileName;
        private String fileType;
        private Long fileSize;
        private boolean uploadSuccess;
        private String errorMessage;
        private LocalDateTime uploadDate;
    }
} 