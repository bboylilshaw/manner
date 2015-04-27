package org.jshaw.manner.web.controller;

import org.jshaw.manner.common.Views;
import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.SignUpForm;
import org.jshaw.manner.domain.User;
import org.jshaw.manner.security.CurrentUser;
import org.jshaw.manner.service.GroupService;
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
import java.util.List;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;

    /**
     * render home page
     * @param authentication
     * @param modelMap
     * @return home page
     */
    @RequestMapping("/")
    public String home(@CurrentUser Authentication authentication, ModelMap modelMap) {
        User user = (User) authentication.getPrincipal();
        List<Group> groups = groupService.listGroups(user.getId());
        modelMap.addAttribute("groups", groups);
        return Views.HOME_PAGE;
    }

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
    public String doSignup(@ModelAttribute SignUpForm signUpForm) throws Exception{
        logger.info(signUpForm.toString());
        userService.signUp(signUpForm);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            return Views.REDIRECT_TO_HOME_PAGE;
        }
        return Views.SIGN_UP_PAGE;
    }

}
