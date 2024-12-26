package com.llm.backend.dto;

import java.util.List;
import lombok.Data;

@Data
public class ContactDto {

    @Data
    public static class ContactSaveRequest {
        public String region;
        public String name;
        public String phoneNumber;
        public String question;
        public List<DialogDto> dialogues; // 변경
    }

    @Data
    public static class DialogDto {
        private String role;
        private String content;
    }

    @Data
    public static class ContactSearchRequest {
        public String name;
        public String phoneNumber;
    }

    @Data
    public static class ContactResponse {
        public String name;
        public String phoneNumber;
        public String question;
    }



}
