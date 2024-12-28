package com.llm.backend.repository;

import static com.llm.backend.domain.QChatLog.chatLog;
import static com.llm.backend.domain.QChatThread.chatThread;

import com.llm.backend.dto.ChatDto.ChatLogDto;
import com.llm.backend.dto.ChatDto.ChatThreadResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ChatThreadRepositoryImpl implements  ChatThreadRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public Page<ChatThreadResponseDto> findByCondition(Pageable pageable) {
        // 메인 쿼리: ChatThread와 사용자 대화 수
        List<ChatThreadResponseDto> results = queryFactory
            .select(Projections.fields(
                ChatThreadResponseDto.class,
                chatThread.createdDate.as("createdDate"), // 생성일자
                chatThread.id.as("chatThreadId"), // ChatThread ID
                chatLog.count().as("userChatLogCount") // 사용자 대화 수
            ))
            .from(chatThread)
            .leftJoin(chatThread.chatLogs, chatLog)
            .where(chatLog.role.eq("user")) // 사용자 대화만 필터링
            .groupBy(chatThread.id)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        // 각 ChatThread에 해당하는 chatLogs를 추가 조회
        results.forEach(dto -> {
            List<ChatLogDto> chatLogs = queryFactory
                .select(Projections.constructor(
                    ChatLogDto.class,
                    chatLog.role, // 역할
                    chatLog.content, // 내용
                    chatLog.createdTime // 생성 시간
                ))
                .from(chatLog)
                .where(chatLog.chatThread.id.eq(dto.getChatThreadId())) // 해당 ChatThread의 chatLogs
                .orderBy(chatLog.createdDate.asc())
                .fetch();

            dto.setChatLogs(chatLogs); // 결과에 chatLogs 설정
        });

        // 전체 데이터 수
        long total = queryFactory
            .select(chatThread.count())
            .from(chatThread)
            .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }




}
