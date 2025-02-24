package com.llm.backend.repository;

import com.llm.backend.domain.EmbeddingFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmbeddingFileRepository extends JpaRepository<EmbeddingFile, Long> {
} 