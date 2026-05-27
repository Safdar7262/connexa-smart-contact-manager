package com.scm.helpers;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

// import lombok.var;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {

        // if user login with email then how to find the password
        if (authentication instanceof OAuth2AuthenticationToken) {
            
            var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
            var client = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (OAuth2User)authentication.getPrincipal();
            String username = "";

            if (client.equalsIgnoreCase("google")) {
                
                // signin with google
                System.out.println("Getting email from google");
                username = oauth2User.getAttribute("email").toString();

            } else if (client.equalsIgnoreCase("github")) {
                
                //sign with github
                System.out.println("Getting email from github");
                username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString() :oauth2User.getAttribute("login").toString() + "@gmail.com";
            }

            return username;

        } else {
            System.out.println("Getting data from local database");
            return authentication.getName();
        }
        
    }

}
