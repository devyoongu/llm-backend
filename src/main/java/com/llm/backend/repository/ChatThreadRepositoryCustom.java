package com.llm.backend.repository;

import com.llm.backend.dto.ChatDto.ChatThreadResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatThreadRepositoryCustom {

    Page<ChatThreadResponseDto> findByCondition(Pageable pageable);

}
