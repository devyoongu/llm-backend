package com.llm.backend.repository;

import com.llm.backend.domain.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatLogRepository extends JpaRepository<ChatLog, String> {


}
