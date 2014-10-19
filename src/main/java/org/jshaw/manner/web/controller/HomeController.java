package org.jshaw.manner.web.controller;

import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.User;
import org.jshaw.manner.security.CurrentUser;
import org.jshaw.manner.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Collection;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private static final String REDIRECT_TO_HOME_PAGE = "redirect:/";
    private static final String HOME_PAGE = "home";
    private static final String SIGN_IN_PAGE = "signin";
    private static final String SIGN_UP_PAGE = "signup";

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String home(ModelMap modelMap, @CurrentUser Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Collection<Group> groups = userService.listGroups(user.getId());
        modelMap.addAttribute("groups", groups);
        return HOME_PAGE;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin(Principal principal) {
        if (principal != null && !principal.getName().equalsIgnoreCase("anonymousUser")) {
            return REDIRECT_TO_HOME_PAGE;
        }
        return SIGN_IN_PAGE;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Principal principal) {
        if (principal != null && !principal.getName().equalsIgnoreCase("anonymousUser")) {
            return REDIRECT_TO_HOME_PAGE;
        }
        return SIGN_UP_PAGE;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String doSignup(@ModelAttribute User user) throws Exception{
        logger.info(user.toString());
        userService.signup(user);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            return REDIRECT_TO_HOME_PAGE;
        }
        return SIGN_UP_PAGE;
    }

}
