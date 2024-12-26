package com.llm.backend.controller;

import com.llm.backend.repository.ContactRepository;
import com.llm.backend.service.ContactService;
import java.util.ArrayList;
import java.util.List;
import com.llm.backend.domain.Contact;
import com.llm.backend.dto.CommonResponse;
import com.llm.backend.dto.ContactDto.ContactSaveRequest;
import com.llm.backend.dto.ContactDto.ContactSearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ContactController {

    private final ContactRepository contactRepository;

    private final ContactService contactService;

    @PostMapping("/api/contact")
    public ResponseEntity<CommonResponse> saveContact(@RequestBody ContactSaveRequest request) {
        Contact saved = contactService.saveContact(request);
        return ResponseEntity.ok(CommonResponse.ok(saved));
    }

    @GetMapping("/api/contact")
    public ResponseEntity<CommonResponse> searchContact(ContactSearchRequest request) {
        List<Contact> searchList = new ArrayList<>();

        if (!StringUtils.isBlank(request.getName())) {
            searchList = contactRepository.findByNameContaining(request.getName());
        } else if (!StringUtils.isBlank(request.getPhoneNumber())) {
            searchList = contactRepository.findByPhoneNumberContaining(request.getPhoneNumber());
        } else {
            searchList = contactRepository.findAll();
        }

        return ResponseEntity.ok(CommonResponse.ok(searchList));
    }

}
