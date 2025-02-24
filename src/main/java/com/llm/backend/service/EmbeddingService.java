package com.llm.backend.service;

import com.llm.backend.domain.EmbeddingFile;
import com.llm.backend.dto.EmbeddingFileDto.EmbeddingFileResponseDto;
import com.llm.backend.dto.EmbeddingFileDto.EmbeddingFileRequest;
import com.llm.backend.repository.EmbeddingFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmbeddingService {

    private final EmbeddingFileRepository embeddingFileRepository;

    @Transactional
    public EmbeddingFileResponseDto saveFileInfo(EmbeddingFileRequest request) {
        EmbeddingFile embeddingFile = EmbeddingFile.builder()
            .fileName(request.getFileName())
            .fileType(request.getFileType())
            .fileSize(request.getFileSize())
            .uploadSuccess(request.isUploadSuccess())
            .errorMessage(request.getErrorMessage())
            .build();

        EmbeddingFile savedFile = embeddingFileRepository.save(embeddingFile);
        return convertToDto(savedFile);
    }

    @Transactional(readOnly = true)
    public List<EmbeddingFileResponseDto> findEmbeddingFiles(Pageable pageable) {
        return embeddingFileRepository.findAll(pageable)
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    private EmbeddingFileResponseDto convertToDto(EmbeddingFile entity) {
        return EmbeddingFileResponseDto.builder()
            .id(entity.getId())
            .fileName(entity.getFileName())
            .fileType(entity.getFileType())
            .fileSize(entity.getFileSize())
            .uploadSuccess(entity.isUploadSuccess())
            .errorMessage(entity.getErrorMessage())
            .uploadDate(entity.getUploadDate())
            .build();
    }
} 