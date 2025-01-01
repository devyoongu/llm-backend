package com.llm.backend.dto;

import com.llm.backend.dto.ContactDto.ContactResponse;
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
        public Long chatThreadId;
        public String chatThreadName;
        public List<ChatLogDto> chatLogs;
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
        private Long chatThreadId;
        private Long userChatLogCount;
        private List<ChatLogDto> chatLogs;
        private ContactResponse contact;
        private String firstChatLog;
        private String chatLogsJson; // JSON 문자열 추가 for thymeleaf
    }


}
