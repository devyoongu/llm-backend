package com.llm.backend.repository;

import static com.llm.backend.domain.QChatLog.chatLog;
import static com.llm.backend.domain.QChatThread.chatThread;
import static com.llm.backend.domain.QContact.contact;

import com.llm.backend.dto.ChatDto.ChatLogDto;
import com.llm.backend.dto.ChatDto.ChatThreadResponseDto;
import com.llm.backend.dto.ContactDto.ContactResponse;
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
        List<ChatThreadResponseDto> results = queryFactory
            .select(Projections.fields(
                ChatThreadResponseDto.class,
                chatThread.createdDate.as("createdDate")
                , chatThread.id.as("chatThreadId")
                , chatLog.count().as("userChatLogCount")
            ))
            .from(chatThread)
            .leftJoin(chatThread.chatLogs, chatLog)
            .where(chatLog.role.eq("user")) // 사용자 대화만 필터링하여 count
            .groupBy(chatThread.id)
            .orderBy(chatThread.createdDate.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        /**
         * 각 ChatThread에 해당하는 chatLogs를 추가 조회
         * Projections.fields 방식은 JPA/QueryDSL에서 DTO를 생성할 때 명시적으로 필드를 매핑하는 방식으로,
         * 컬렉션 타입(예: List<ChatLog>)을 직접 매핑하지 못하는 제한이 발생. 그래서 chatLogs를 추가로 조회하는 방식으로 해결해야 함
         * */
        results.forEach(dto -> {
            ContactResponse contactDto = queryFactory
                .select(Projections.fields(
                        ContactResponse.class,
                        contact.name.as("name"),
                        contact.phoneNumber.as("phoneNumber"),
                        contact.question.as("question")
                    )
                )
                .from(contact)
                .where(contact.chatThread.id.eq(dto.getChatThreadId()))
                .fetchOne();

            dto.setContact(contactDto);

            List<ChatLogDto> chatLogs = queryFactory
                .select(Projections.constructor(
                    ChatLogDto.class,
                    chatLog.role,
                    chatLog.content,
                    chatLog.createdTime
                ))
                .from(chatLog)
                .where(chatLog.chatThread.id.eq(dto.getChatThreadId()))
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
