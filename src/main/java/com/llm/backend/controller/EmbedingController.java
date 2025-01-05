package com.llm.backend.controller;

import com.llm.backend.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class EmbedingController {

    private final ChatService chatService;

    @GetMapping("/embeding")
    @CrossOrigin(origins = "http://localhost:8080")
    public String searchChatLog(@PageableDefault(size = 10, sort = "createdDate",direction = Sort.Direction.DESC) Pageable pageable, Model model) {

//        List<ChatThreadResponseDto> chatThreads = chatService.searchChatLog(pageable);
//
//        model.addAttribute("chatThreads", chatThreads);

        return "embeding";
    }

}
