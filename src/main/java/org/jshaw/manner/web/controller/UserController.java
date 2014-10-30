package org.jshaw.manner.web.controller;

import org.jshaw.manner.common.Views;
import org.jshaw.manner.domain.User;
import org.jshaw.manner.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin(Principal principal) {
        if (principal != null && !principal.getName().equalsIgnoreCase("anonymousUser")) {
            return Views.REDIRECT_TO_HOME_PAGE;
        }
        return Views.SIGN_IN_PAGE;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Principal principal) {
        if (principal != null && !principal.getName().equalsIgnoreCase("anonymousUser")) {
            return Views.REDIRECT_TO_HOME_PAGE;
        }
        return Views.SIGN_UP_PAGE;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String doSignup(@ModelAttribute User user) throws Exception{
        logger.info(user.toString());
        userService.signUp(user);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            return Views.REDIRECT_TO_HOME_PAGE;
        }
        return Views.SIGN_UP_PAGE;
    }

}
