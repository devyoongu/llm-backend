package com.llm.backend.controller;

import com.llm.backend.domain.ChatThread;
import com.llm.backend.dto.ChatDto.ChatSaveRequest;
import com.llm.backend.dto.ChatDto.ChatThreadResponseDto;
import com.llm.backend.dto.CommonResponse;
import com.llm.backend.service.ChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatApiController {

    private final ChatService chatService;

    @PostMapping("/api/chat-log")
    public ResponseEntity<CommonResponse> saveChatLog(@RequestBody ChatSaveRequest request) {

        ChatThread saved = chatService.saveChatLog(request);

        return ResponseEntity.ok(CommonResponse.ok(saved));
    }

    @GetMapping("/api/chat-log")
    public ResponseEntity<CommonResponse> searchChatLog(Pageable pageable) {

        List<ChatThreadResponseDto> chatLogs = chatService.searchChatLog(pageable);

        return ResponseEntity.ok(CommonResponse.ok(chatLogs));
    }

}
