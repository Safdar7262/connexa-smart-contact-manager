package com.scm.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helpers.Helper;
import com.scm.helpers.Message;
import com.scm.helpers.Messagetype;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    // add contact page
    @GetMapping("/add")
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();
        contactForm.setFavrite(true);
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    // save contact
    @PostMapping("/add")
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm,
            BindingResult result,
            Authentication authentication,
            HttpSession session) {

        if (result.hasErrors()) {
            return "user/add_contact";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);

        String filename = UUID.randomUUID().toString();
        String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavrite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());

        // important
        contact.setUser(user);

        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebSiteLink(contactForm.getWebsiteLink());
        contact.setPicture(fileURL);
        contact.setCloudinaryImagePublicId(filename);

        contactService.save(contact);

        session.setAttribute("message",
                Message.builder()
                        .content("Contact added successfully")
                        .type(Messagetype.red)
                        .build());

        return "redirect:/user/contacts";
    }

    // view all contacts
    @GetMapping
    public String viewContacts(Model model, Authentication authentication) {

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);

        List<Contact> contacts = contactService.getByUser(user);

        model.addAttribute("contacts", contacts);

        return "user/contacts";
    }

    // delete contact
    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable String id, RedirectAttributes ra) {

        contactService.delete(id);
        ra.addFlashAttribute("message", "Contact deleted successfully!");

        return "redirect:/user/contacts";
    }

    // view single contact
    @GetMapping("/view/{id}")
    public String viewContact(@PathVariable String id, Model model) {

        Contact contact = contactService.getByid(id);

        model.addAttribute("contact", contact);

        return "user/view_contact";
    }

    // Edit contact
    @GetMapping("/edit/{id}")
    public String editContact(@PathVariable String id, Model model) {

        Contact contact = contactService.getByid(id);

        model.addAttribute("contact", contact);

        return "user/edit_contact";
    }

    // update contact
    @PostMapping("/update")
    public String updateContact(
            @Valid @ModelAttribute("contact") Contact contact,
            BindingResult result,
            @RequestParam("contactImage") MultipartFile file) {

        if (result.hasErrors()) {
            return "user/edit_contact";
        }

        contactService.update(contact);

        return "redirect:/user/contacts";
    }

}