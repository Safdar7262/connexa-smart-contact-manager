package com.scm.services.implementation;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.ContactRepo;
import com.scm.services.ContactService;

// import lombok.var;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactRepo contactRepo;

    @Autowired
    private ContactRepo repo;

    @Override
    public Contact save(Contact contact) {

        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);
    }

   @Override
public void update(Contact contact) {

    Contact existing = contactRepo.findById(contact.getId())
            .orElseThrow(() -> new RuntimeException("Contact not found"));

    // SAFE UPDATE (only required fields)
    existing.setName(contact.getName());
    existing.setEmail(contact.getEmail());
    existing.setPhoneNumber(contact.getPhoneNumber());
    existing.setAddress(contact.getAddress());
    existing.setDescription(contact.getDescription());
    existing.setWebSiteLink(contact.getWebSiteLink());
    existing.setLinkedInLink(contact.getLinkedInLink());
    existing.setFavorite(contact.isFavorite());

    // IMAGE HANDLE (important)
    if (contact.getPicture() != null && !contact.getPicture().isEmpty()) {
        existing.setPicture(contact.getPicture());
    }

    contactRepo.save(existing);
}
    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public void delete(String id) {
         var contact = contactRepo.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id" + id));
         contactRepo.delete(contact);
    }

    @Override
    public List<Contact> search(String name, String email, String phoneNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);
    }

    @Override
    public Contact getByid(String id) {
    
         return contactRepo.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id" + id));
    }

    @Override
    public List<Contact> getByUser(User user) {
        
        return contactRepo.findByUser(user);

    }

    @Override
    public Page<Contact> getByUser(User user, int page, int size) {
    
        Pageable pageable = PageRequest.of(page, size);
        return contactRepo.findByUser(user, pageable);
    }

    @Override
    public long countByUser(User user) {
        return contactRepo.countByUser(user);
    }

    @Override
    public long countFavorite(User user) {
        return contactRepo.countByUserAndFavoriteTrue(user);
    }

    @Override
    public List<Contact> getRecentContacts(User user) {
        return contactRepo.findTop5ByUserOrderByIdDesc(user);
    }

    @Override
    public List<Contact> getByIds(List<String> ids) {
        return repo.findAllById(ids);
    }

}
