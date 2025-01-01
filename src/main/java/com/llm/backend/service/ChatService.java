package com.llm.backend.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.llm.backend.domain.ChatLog;
import com.llm.backend.domain.ChatThread;
import com.llm.backend.dto.ChatDto.ChatSaveRequest;
import com.llm.backend.dto.ChatDto.ChatThreadResponseDto;
import com.llm.backend.repository.ChatLogRepository;
import com.llm.backend.repository.ChatThreadRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public List<ChatThreadResponseDto> searchChatLog(Pageable pageable) {
        Page<ChatThreadResponseDto> byCondition = chatThreadRepository.findByCondition(pageable);
        List<ChatThreadResponseDto> content = byCondition.getContent();

        // ObjectMapper 인스턴스 생성
        ObjectMapper objectMapper = new ObjectMapper();

        // chatLogs를 JSON으로 변환하여 ChatThreadResponseDto에 추가
        content.forEach(chatThreadResponseDto -> {
            try {
                String chatLogsJson = objectMapper.writeValueAsString(chatThreadResponseDto.getChatLogs());
                chatThreadResponseDto.setChatLogsJson(chatLogsJson); // JSON 문자열 설정
            } catch (JsonProcessingException e) {
                chatThreadResponseDto.setChatLogsJson("[]"); // JSON 변환 실패 시 빈 배열 설정
            }
        });

        return content;
    }

}
