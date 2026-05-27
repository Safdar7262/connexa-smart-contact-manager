package com.scm.services.implementation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entities.ContactGroup;
import com.scm.repositories.ContactGroupRepository;
import com.scm.services.ContactGroupService;

@Service
public class ContactGroupServiceImpl implements ContactGroupService {

    @Autowired
    private ContactGroupRepository repo;

    @Override
    public ContactGroup save(ContactGroup group) {
        return repo.save(group);
    }

    @Override
    public List<ContactGroup> getAll() {
        return repo.findAll();
    }

    // 🔥 FIX: Long → String
    @Override
    public ContactGroup getById(String id) {
        return repo.findById(id).orElse(null);
    }

    // 🔥 FIX: Long → String
    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }

    // optional (agar use karna hai)
    public List<ContactGroup> getByIds(List<String> ids) {
        return repo.findAllById(ids);
    }
}