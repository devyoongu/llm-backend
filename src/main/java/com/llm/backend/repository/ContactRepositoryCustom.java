package com.llm.backend.repository;

import java.util.List;
import com.llm.backend.domain.Contact;

public interface ContactRepositoryCustom {

    List<Contact> findByNameContaining(String name);

    List<Contact> findByPhoneNumberContaining(String phoneNumber);

}
