package com.llm.backend.domain;

import com.llm.backend.dto.ContactDto.ContactSaveRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // Lombok 순환 참조 방지
    private List<Dialogue> dialogues;

    public void addDialogue(Dialogue dialogue) {
        dialogue.setContact(this);
        if (this.dialogues == null) {
            this.dialogues = new ArrayList<>();
        }
        this.dialogues.add(dialogue);
    }

    public static Contact toEntity(ContactSaveRequest request) {
        // Contact를 생성하면서 Dialogue와의 관계를 설정
        Contact contact = Contact.builder()
            .id(UUID.randomUUID().toString())
            .name(request.getName())
            .phoneNumber(request.getPhoneNumber())
            .question(request.getQuestion())
            .region(request.getRegion())
            .dialogues(new ArrayList<>())
            .build();

        request.getDialogues().forEach(dto -> contact.addDialogue(Dialogue.toEntity(dto)));

        return contact;
    }

}
