package org.jshaw.manner.web.controller;

import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.User;
import org.jshaw.manner.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public String doCreateGroup(HttpServletRequest request) {

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Group group = Group.of(request.getParameter("groupName"), new Date(), currentUser, new HashSet<>(), new ArrayList<>());
        userService.createGroup(currentUser, group);
        return "addGroup";
    }
}
