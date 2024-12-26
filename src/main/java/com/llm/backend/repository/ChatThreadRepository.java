package com.llm.backend.repository;

import com.llm.backend.domain.ChatThread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatThreadRepository extends JpaRepository<ChatThread, String> {


}
