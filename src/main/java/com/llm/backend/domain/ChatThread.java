package com.llm.backend.domain;

import com.llm.backend.dto.ChatDto.ChatSaveRequest;
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
public class ChatThread extends BaseTimeEntity{

    @Id
    @Column(name = "chat_thread_id")
    private String id;

    private String name;

    @OneToMany(mappedBy = "chatThread", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // Lombok 순환 참조 방지
    private List<ChatLog> chatLogs;

    public void addDialogue(ChatLog chatLog) {
        chatLog.setChatThread(this);
        if (this.chatLogs == null) {
            this.chatLogs = new ArrayList<>();
        }
        this.chatLogs.add(chatLog);
    }

    public static ChatThread toEntity(ChatSaveRequest request) {
        ChatThread chatThread = ChatThread.builder()
            .id(UUID.randomUUID().toString())
            .name(request.getChatThreadName())
            .chatLogs(new ArrayList<>())
            .build();

        request.getChatLogs().forEach(dto -> chatThread.addDialogue(ChatLog.toEntity(dto)));

        return chatThread;
    }

}
