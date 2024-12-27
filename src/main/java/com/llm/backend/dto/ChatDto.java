package com.llm.backend.dto;

import java.util.List;
import lombok.Data;

@Data
public class ChatDto {

    @Data
    public static class ChatSaveRequest {
        public String chatThreadId;
        public String chatThreadName;
        public List<ChatLogDto> chatLogs; // 변경
    }

    @Data
    public static class ChatLogDto {
        private String role;
        private String content;
        private long createdTime;
    }

}
