package com.llm.backend.service;


import com.llm.backend.domain.Contact;
import com.llm.backend.dto.ContactDto.ContactSaveRequest;
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

    @Transactional
    public Contact saveContact(ContactSaveRequest request) {
        Contact contactEntity = Contact.toEntity(request);
        return contactRepository.save(contactEntity);

    }

}
