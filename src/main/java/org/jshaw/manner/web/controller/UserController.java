package org.jshaw.manner.web.controller;

import org.jshaw.manner.common.Priority;
import org.jshaw.manner.common.Status;
import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.Item;
import org.jshaw.manner.domain.User;
import org.jshaw.manner.security.CurrentUser;
import org.jshaw.manner.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

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
    public String doCreateGroup(@CurrentUser Authentication authentication,
                                HttpServletRequest request,
                                RedirectAttributes redirectAttributes) {
        String groupName = request.getParameter("groupName");
        User currentUser = (User) authentication.getPrincipal();
        Group group = Group.of(groupName, new Date(), currentUser, new HashSet<>());
        userService.createGroup(currentUser, group);
        redirectAttributes.addFlashAttribute("message", "Added group successfully!");
        return "redirect:/";
    }

    @RequestMapping(value = "/group/{groupId}/items", method = RequestMethod.GET)
    public String listGroupItems(@PathVariable("groupId") Long groupId,
                                 @RequestParam(value = "startPage", defaultValue = "0") int startPage,
                                 ModelMap modelMap) {
        Page<Item> itemPage = userService.listGroupItems(groupId, startPage);
        modelMap.addAttribute("page", itemPage);
        modelMap.addAttribute("groupId", groupId);
        return "user/list-items";
    }

    @RequestMapping(value = "/group/{groupId}/item", method = RequestMethod.GET)
    public String createItem(@PathVariable("groupId") Long groupId, ModelMap modelMap) {
        modelMap.addAttribute("groupId", groupId);
        return "user/add-item";
    }

    @RequestMapping(value = "/group/{groupId}/item", method = RequestMethod.POST)
    public String doCreateItem(@PathVariable("groupId") Long groupId,
                               @CurrentUser Authentication authentication,
                               HttpServletRequest request) {
        User currentUser = (User) authentication.getPrincipal();
        String content = request.getParameter("itemContent");
        Item item = Item.of(content, currentUser, currentUser, LocalDate.now(Clock.systemUTC()), null, Status.NEW, 0, null, null, Priority.HIGH, null);
        userService.createItem(groupId, item);
        return "redirect:/group/" + groupId + "/items";
    }
}
