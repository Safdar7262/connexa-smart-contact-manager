package com.scm.controllers;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.services.ContactService;
import com.scm.services.UserService;

// All protected pages

@Controller
@RequestMapping("/user")
public class UserController {


      @GetMapping("/home")
    public String home() {
        return "home"; // this is your Thymeleaf/JSP page
    }



    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // inject services
    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

   @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {

    String email = null;

    if (authentication.getPrincipal() instanceof org.springframework.security.oauth2.core.user.OAuth2User oauthUser) {
        email = oauthUser.getAttribute("email");
    } else {
        email = authentication.getName();
    }

    User user = userService.getUserByEmail(email);

    model.addAttribute("loggedInUser", user); // THIS IS KEY

    List<Contact> recentContacts = contactService.getRecentContacts(user);

    model.addAttribute("recentContacts", recentContacts);

    long totalContacts = contactService.countByUser(user);
    long favoriteContacts = contactService.countFavorite(user);

    model.addAttribute("totalContacts", totalContacts);
    model.addAttribute("favoriteContacts", favoriteContacts);

    return "user/dashboard";
}

    // user profile page
    @RequestMapping("/profile")
    public String userProfile(Authentication authentication, Model model) {

        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        model.addAttribute("user", user);

        return "user/profile";
    }

    // // get all contacts with pagination
    // @GetMapping("/contacts")
    // public String getContacts(
    // @RequestParam(value = "page", defaultValue = "0") int page,
    // @RequestParam(value = "size", defaultValue = "5") int size,
    // Model model,
    // Principal principal) {

    // // get logged in user
    // String username = principal.getName();
    // User user = userService.getUserByEmail(username);

    // // get paginated contacts
    // Page<Contact> contactPage = contactService.getByUser(user, page, size);

    // // send data to frontend
    // model.addAttribute("contactPage", contactPage);
    // System.out.println("Contacts size: " + contactPage.getContent().size());
    // return "user/contacts";
    // }

    // // delete contact by id
    // @GetMapping("/contact/delete/{id}")
    // public String deleteContact(@PathVariable String id) {

    // // delete contact
    // contactService.delete(id);

    // // redirect to contact list
    // return "redirect:/user/contacts";
    // }

    // // view contact details
    // @GetMapping("/contact/view/{id}")
    // public String viewContact(@PathVariable String id, Model model) {

    // // get contact from database
    // Contact contact = contactService.getByid(id);

    // // send to frontend
    // model.addAttribute("contact", contact);

    // return "user/view_contact";
    // }

    // // edit contact page (optional next step)
    // @GetMapping("/contact/edit/{id}")
    // public String editContact(@PathVariable String id, Model model) {

    // Contact contact = contactService.getByid(id);

    // model.addAttribute("contact", contact);

    // return "user/edit_contact";
    // }
}