package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// import lombok.var;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
            
                logger.info("OAuthAuthenicationSuccessHandler");

                // identify the provider

                var OAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;

                String authorizedClientRegistrationId = OAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
                logger.info(authorizedClientRegistrationId);

                var oauthUser = (DefaultOAuth2User)authentication.getPrincipal();

                oauthUser.getAttributes().forEach((key, value) -> {
                    logger.info(key + " : " + value);
                });

                User user = new User();

                user.setUserId(UUID.randomUUID().toString());
                user.setRoleList(List.of(AppConstants.ROLE_USER));
                user.setEmailVerified(true);
                user.setEnabled(true);
                user.setPassword("Dummy");

                if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {

                    user.setEmail(oauthUser.getAttribute("email").toString());
                    user.setProfilePic(oauthUser.getAttribute("picture").toString());
                    user.setName(oauthUser.getName());
                    user.setProvider(Providers.GOOGLE);
                    user.setAbout("This Account creating using Google...");
                    
                } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {

                    String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString() : oauthUser.getAttribute("login").toString() + "@gmail.com";
                    String picture = oauthUser.getAttribute("avatar_url").toString();
                    String name = oauthUser.getAttribute("login").toString();
                    String providerUserId = oauthUser.getName();

                    user.setEmail(email);
                    user.setProfilePic(picture);
                    user.setName(name);
                    user.setProviderUserId(providerUserId);
                    user.setProvider(Providers.GITHUB);
                    user.setAbout("This Account creating using Github...");
                    
                } else {
                    logger.info("authorizedClientRegistrationId : Unknown Provider");
                }

                //save data in database
/* 
                DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();

                // logger.info(user.getName());

                // user.getAttributes().forEach((key, value) -> {
                //     logger.info("{} => {}", key, value);
                // });

                // logger.info(user.getAttributes().toString());

                String email = user.getAttribute("email").toString();
                String name = user.getAttribute("name").toString();
                String picture = user.getAttribute("picture").toString();

                // create a user and save in database

                User user2 = new User();

                user2.setEmail(email);
                user2.setName(name);
                user2.setProfilePic(picture);
                user2.setPassword("password");
                user2.setUserId(UUID.randomUUID().toString());
                user2.setProvider(Providers.GOOGLE);
                user2.setEnabled(true);
                user2.setEmailVerified(true);
                user2.setProviderUserId(user.getName());
                user2.setRoleList(List.of(AppConstants.ROLE_USER));
                user2.setAbout("This account is create using google...");

                */

                User user3 = userRepo.findByEmail(user.getEmail()).orElse(null);

                if (user3 == null) {
                    userRepo.save(user);
                }
                    

                new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }

}
