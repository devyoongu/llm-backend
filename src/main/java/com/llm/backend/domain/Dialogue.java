package com.llm.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.llm.backend.dto.ContactDto.DialogDto;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Entity
@Getter @Setter
@NoArgsConstructor
public class Dialogue extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dialogue_id")
    private Long id;

    private String role;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    @ToString.Exclude // Lombok 순환 참조 방지
    @JsonIgnore // JSON 직렬화 시 순환 참조 방지
    private Contact contact;

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public static Dialogue toEntity(DialogDto dialogDtos) {
        return Dialogue.builder()
            .role(dialogDtos.getRole())
            .content(dialogDtos.getContent())
            .build();
    }
}
