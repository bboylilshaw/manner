package org.jshaw.manner.web.controller;

import org.jshaw.manner.domain.Role;
import org.jshaw.manner.domain.User;
import org.jshaw.manner.security.UserRepositoryUserDetailsService;
import org.jshaw.manner.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private static final String HOME_PAGE = "home";
    private static final String SIGN_IN_PAGE = "signin";
    private static final String SIGN_UP_PAGE = "signup";

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepositoryUserDetailsService userDetailsService;

    @RequestMapping("/")
    public String home() {
        return HOME_PAGE;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin(Principal principal) {
        if (principal != null && !principal.getName().equalsIgnoreCase("anonymousUser")) {
            return "redirect:/";
        }
        return SIGN_IN_PAGE;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Principal principal) {
        if (principal != null && !principal.getName().equalsIgnoreCase("anonymousUser")) {
            return "redirect:/";
        }
        return SIGN_UP_PAGE;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String doSignup(@ModelAttribute User user) throws Exception{
        String rawPassword = user.getPassword();
        user.setRole(Role.USER);
        user.setPassword(encoder.encode(rawPassword));
        logger.info(user.toString());

        userService.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        auth.setDetails(userDetails);

        if (auth.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:/";
        }

        return SIGN_UP_PAGE;
    }

}
