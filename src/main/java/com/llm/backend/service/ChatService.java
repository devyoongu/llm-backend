package com.llm.backend.service;


import com.llm.backend.domain.ChatLog;
import com.llm.backend.domain.ChatThread;
import com.llm.backend.dto.ChatDto.ChatSaveRequest;
import com.llm.backend.repository.ChatLogRepository;
import com.llm.backend.repository.ChatThreadRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatThreadRepository chatThreadRepository;

    private final ChatLogRepository chatLogRepository;

    @Transactional
    public ChatThread saveChatLog(ChatSaveRequest request) {

        if (request.getChatThreadId() == null) {
            ChatThread chatThreadEntity = ChatThread.toEntity(request);
            return chatThreadRepository.save(chatThreadEntity);
        } else {
            ChatThread chatThreadEntity = chatThreadRepository.findById(request.getChatThreadId())
                .orElseThrow(() -> new IllegalArgumentException());

            List<ChatLog> chatLogs = request.getChatLogs().stream()
                .map(dto ->
                    {
                        ChatLog chatLog = ChatLog.toEntity(dto);
                        chatLog.setChatThread(chatThreadEntity);
                        return chatLog;
                    }
                )
                .collect(Collectors.toList());

            chatLogRepository.saveAll(chatLogs);

            return chatThreadEntity;
        }

    }

}
