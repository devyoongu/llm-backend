package com.llm.backend.domain;

import com.llm.backend.dto.ContactDto.ContactSaveRequest;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Entity
@Getter @Setter
@NoArgsConstructor
public class Contact extends BaseTimeEntity{

    @Id
    @GeneratedValue
    @Column(name = "contact_id")
    private Long id;

    private String name;

    private String phoneNumber;

    private String question;

    @Column
    @Lob
    private String chatData;


    public static Contact toEntity(ContactSaveRequest request) {
        return Contact.builder()
            .name(request.getName())
            .phoneNumber(request.getPhoneNumber())
            .question(request.getQuestion())
            .build();
    }

}
