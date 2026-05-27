package com.scm.helpers;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class LoggedInUserAdvice {

    @ModelAttribute
    public void addLoggedInUser(Model model, Principal principal) {

        if (principal != null) {
            model.addAttribute("loggedInUser", principal.getName());
        }

    }

}