package com.llm.backend.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class ChatDto {

    @Data
    public static class ChatSaveRequest {
        public String chatThreadId;
        public String chatThreadName;
        public List<ChatLogDto> chatLogs; // 변경
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ChatLogDto {
        private String role;
        private String content;
        private long createdTime;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ChatThreadResponseDto {
        private LocalDateTime createdDate;
        private String chatThreadId;
        private Long userChatLogCount;
        private List<ChatLogDto> chatLogs;
    }


}
