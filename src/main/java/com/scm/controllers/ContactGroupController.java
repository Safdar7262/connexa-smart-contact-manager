package com.scm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.scm.entities.Contact;
import com.scm.entities.ContactGroup;
import com.scm.services.ContactGroupService;
import com.scm.services.ContactService;

@Controller
@RequestMapping("/user/groups")
public class ContactGroupController {

    @Autowired
    private ContactGroupService service;

    @Autowired
    private ContactService contactService;

    // Show page
    @GetMapping
    public String groupsPage(Model model) {
        model.addAttribute("groups", service.getAll());
        model.addAttribute("group", new ContactGroup());
        return "user/groups";
    }

    // Add group
    @PostMapping("/add")
    public String addGroup(@ModelAttribute ContactGroup group) {
        service.save(group);
        return "redirect:/user/groups";
    }

    // View group
    @GetMapping("/view/{id}")
    public String viewGroup(@PathVariable String id, Model model) {

        ContactGroup group = service.getById(id);
        List<Contact> allContacts = contactService.getAll();

        model.addAttribute("group", group);
        model.addAttribute("contacts", allContacts);

        return "user/group_details";
    }

    // Delete group
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/user/groups";
    }

    //  FIXED METHOD
    @PostMapping("/add-contact")
    public String addContactsToGroup(@RequestParam String groupId,
            @RequestParam List<String> contactIds) {

        ContactGroup group = service.getById(groupId);

        List<Contact> selectedContacts = contactService.getByIds(contactIds);

        group.getContacts().addAll(selectedContacts);

        service.save(group);

        return "redirect:/user/groups/view/" + groupId;
    }

    @GetMapping("/remove-contact")
    public String removeContactFromGroup(@RequestParam String groupId,
            @RequestParam String contactId) {

        ContactGroup group = service.getById(groupId);

        // remove contact
        group.getContacts().removeIf(c -> c.getId().equals(contactId));

        service.save(group);

        return "redirect:/user/groups/view/" + groupId;
    }
}