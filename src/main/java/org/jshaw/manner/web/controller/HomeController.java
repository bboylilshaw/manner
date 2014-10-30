package org.jshaw.manner.web.controller;

import org.jshaw.manner.common.Views;
import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.User;
import org.jshaw.manner.security.CurrentUser;
import org.jshaw.manner.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private GroupService groupService;

    @RequestMapping("/")
    public String home(@CurrentUser Authentication authentication, ModelMap modelMap) {
        User user = (User) authentication.getPrincipal();
        List<Group> groups = groupService.listGroups(user.getId());
        modelMap.addAttribute("groups", groups);
        return Views.HOME_PAGE;
    }

}
