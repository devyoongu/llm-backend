package com.llm.backend.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "embedding_file")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddingFile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileType;  // document or department

    private Long fileSize;    // KB 단위

    @Column(nullable = false)
    private boolean uploadSuccess;

    @Column(length = 1000)
    private String errorMessage;

    @Builder.Default
    private LocalDateTime uploadDate = LocalDateTime.now();
} 