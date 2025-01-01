package com.llm.backend.dto;

import lombok.Data;

@Data
public class ContactDto {

    @Data
    public static class ContactSaveRequest {
        public String region;
        public String name;
        public String phoneNumber;
        public String question;
        public Long chatThreadId;
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
        public Long chatThreadId;
        public String contactId;
    }



}
