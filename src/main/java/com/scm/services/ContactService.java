package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactService {

    // save contacts
    Contact save(Contact contact);

    //update contacts
    void update(Contact contact);

    //get contacts
    List<Contact> getAll();

    Contact getByid(String id);

    //Delete contacts
    void delete(String id);

    // search contacts
    List<Contact> search(String name, String email, String phoneNumber);

    //get contacts by UserId
    List<Contact> getByUserId(String id);

    List<Contact> getByUser(User user);

    // Pagination
    Page<Contact> getByUser(User user, int page, int size);

     long countByUser(User user);

    long countFavorite(User user);

    List<Contact> getRecentContacts(User user);

    List<Contact> getByIds(List<String> ids);

}
