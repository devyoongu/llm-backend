package com.llm.backend.service;


import com.llm.backend.domain.ChatThread;
import com.llm.backend.domain.Contact;
import com.llm.backend.dto.ContactDto.ContactSaveRequest;
import com.llm.backend.repository.ChatThreadRepository;
import com.llm.backend.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    private final ChatThreadRepository chatThreadRepository;

    @Transactional
    public Contact saveContact(ContactSaveRequest request) {
        ChatThread chatThread = chatThreadRepository.getById(request.getChatThreadId());

        Contact contactEntity = Contact.toEntity(request, chatThread);
        return contactRepository.save(contactEntity);
    }

}
