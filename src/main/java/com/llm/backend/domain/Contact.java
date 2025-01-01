package com.llm.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.llm.backend.dto.ContactDto.ContactSaveRequest;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Entity
@Getter @Setter
@NoArgsConstructor
public class Contact extends BaseTimeEntity{

    @Id
    @Column(name = "contact_id")
    private String id;

    private String name;

    private String phoneNumber;

    private String question;

    private String region;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_thread_id")
    @ToString.Exclude
    @JsonIgnore
    private ChatThread chatThread;

    public static Contact toEntity(ContactSaveRequest request, ChatThread chatThread) {
        Contact contact = Contact.builder()
            .id(UUID.randomUUID().toString())
            .name(request.getName())
            .phoneNumber(request.getPhoneNumber())
            .question(request.getQuestion())
            .region(request.getRegion())
            .chatThread(chatThread)
            .build();

        return contact;
    }

}
