package org.jshaw.manner.web.controller;

import org.jshaw.manner.common.Status;
import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.Item;
import org.jshaw.manner.domain.User;
import org.jshaw.manner.security.CurrentUser;
import org.jshaw.manner.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ModelAttribute("groups")
    private Collection<Group> groups(@CurrentUser Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return userService.listGroups(currentUser.getId());
    }

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public String createGroup() {
        return "user/add-group";
    }

    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public String doCreateGroup(HttpServletRequest request, @CurrentUser Authentication authentication) {
        String groupName = request.getParameter("groupName");
        User currentUser = (User) authentication.getPrincipal();
        Group group = Group.of(groupName, new Date(), currentUser, new HashSet<>());
        userService.createGroup(currentUser, group);
        return "redirect:/";
    }

    @RequestMapping(value = "/group/{groupId}/items", method = RequestMethod.GET)
    public String listGroupItems(@PathVariable("groupId") Long groupId, ModelMap modelMap) {
        List<Item> items = userService.listGroupItems(groupId);
        modelMap.addAttribute("items", items);
        modelMap.addAttribute("groupId", groupId);
        return "user/list-items";
    }

    @RequestMapping(value = "/group/{groupId}/item", method = RequestMethod.GET)
    public String createItem(@PathVariable("groupId") Long groupId, ModelMap modelMap) {
        modelMap.addAttribute("groupId", groupId);
        return "user/add-item";
    }

    @RequestMapping(value = "/group/{groupId}/item", method = RequestMethod.POST)
    public String doCreateItem(@PathVariable("groupId") Long groupId, HttpServletRequest request, @CurrentUser Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        String content = request.getParameter("itemContent");
        Item item = Item.of(content, currentUser, currentUser, null, Status.NEW, 0);
        userService.createItem(groupId, item);
        return "redirect:/group/" + groupId + "/items";
    }
}
