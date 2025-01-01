package com.llm.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.llm.backend.dto.ChatDto.ChatSaveRequest;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class ChatThread extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    @Column(name = "chat_thread_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "chatThread", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // Lombok 순환 참조 방지
    private List<ChatLog> chatLogs;

    @OneToOne(mappedBy = "chatThread", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Contact contact;

    public void addChatLog(ChatLog chatLog) {
        chatLog.setChatThread(this);
        if (this.chatLogs == null) {
            this.chatLogs = new ArrayList<>();
        }
        this.chatLogs.add(chatLog);
    }

    public static ChatThread toEntity(ChatSaveRequest request) {
        ChatThread chatThread = ChatThread.builder()
            .name(request.getChatThreadName())
            .chatLogs(new ArrayList<>())
            .build();

        request.getChatLogs().forEach(dto -> chatThread.addChatLog(ChatLog.toEntity(dto)));

        return chatThread;
    }

}
