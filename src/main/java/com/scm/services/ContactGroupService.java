package com.scm.services;

import java.util.List;
import com.scm.entities.ContactGroup;

public interface ContactGroupService {

    ContactGroup save(ContactGroup group);

    List<ContactGroup> getAll();

    // 🔥 FIX: Long → String
    ContactGroup getById(String id);

    // 🔥 FIX: Long → String
    void delete(String id);

    List<ContactGroup> getByIds(List<String> ids);

    
}