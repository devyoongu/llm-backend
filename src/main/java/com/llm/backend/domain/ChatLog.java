package com.llm.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.llm.backend.dto.ChatDto.ChatLogDto;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class ChatLog extends BaseTimeEntity {

    @Id
    @Column(name = "chat_log_id")
    private String id;

    private String role;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    private long createdTime;

    //채팅 시작 시 지역을 먼저 선택하는게 좋을 듯 그래야 나중에 담당자 컨택률이 높아짐 (파이썬에서 모듈식으로 필요에 따라 넣어야 할듯)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_thread_id")
    @ToString.Exclude // Lombok 순환 참조 방지
    @JsonIgnore // JSON 직렬화 시 순환 참조 방지
    private ChatThread chatThread;

    public void setChatThread(ChatThread chatThread) {
        this.chatThread = chatThread;
    }

    public static ChatLog toEntity(ChatLogDto chatLogDto) {
        return ChatLog.builder()
            .id(UUID.randomUUID().toString())
            .role(chatLogDto.getRole())
            .content(chatLogDto.getContent())
            .createdTime(chatLogDto.getCreatedTime())
            .build();
    }
}
