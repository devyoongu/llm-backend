package com.llm.backend.controller;

import com.llm.backend.dto.CommonResponse;
import com.llm.backend.dto.EmbeddingFileDto.EmbeddingFileRequest;
import com.llm.backend.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class EmbeddingApiController {

    private final EmbeddingService embeddingService;

    @PostMapping("/api/embedding/upload")
    public ResponseEntity<CommonResponse> uploadFile(
            @RequestBody EmbeddingFileRequest request) {
        return ResponseEntity.ok(CommonResponse.ok(
            embeddingService.saveFileInfo(request)
        ));
    }
}
