package com.llm.backend.controller;

import com.llm.backend.domain.ChatThread;
import com.llm.backend.dto.ChatDto.ChatSaveRequest;
import com.llm.backend.dto.CommonResponse;
import com.llm.backend.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/api/chat-log")
    public ResponseEntity<CommonResponse> saveChatLog(@RequestBody ChatSaveRequest request) {

        ChatThread saved = chatService.saveChatLog(request);

        return ResponseEntity.ok(CommonResponse.ok(saved));
    }

}
