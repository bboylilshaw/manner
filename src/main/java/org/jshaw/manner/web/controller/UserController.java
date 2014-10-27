package org.jshaw.manner.web.controller;

import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.Item;
import org.jshaw.manner.domain.User;
import org.jshaw.manner.security.CurrentUser;
import org.jshaw.manner.service.GroupService;
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
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;

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
        groupService.addGroup(groupName, currentUser);
        redirectAttributes.addFlashAttribute("message", "Added group successfully!");
        return "redirect:/";
    }

    @RequestMapping(value = "/group/{groupId}/items", method = RequestMethod.GET)
    public String listGroupItems(@PathVariable("groupId") Long groupId,
                                 @RequestParam(value = "startPage", defaultValue = "0") int startPage,
                                 ModelMap modelMap) {
        int pageSize = 10; //return 10 items each time
        Page<Item> itemPage = userService.listGroupItems(groupId, startPage, pageSize);
        modelMap.addAttribute("page", itemPage);
        modelMap.addAttribute("groupId", groupId);
        return "user/list-items";
    }

    @RequestMapping(value = "/group/{groupId}/item", method = RequestMethod.GET)
    public String createItem(@PathVariable("groupId") Long groupId, ModelMap modelMap) {
        List<User> users = (List<User>) userService.getGroupDetails(groupId).getUsers();
        modelMap.addAttribute("groupId", groupId);
        modelMap.addAttribute("users", users);
        return "user/add-item";
    }

    @RequestMapping(value = "/group/{groupId}/item", method = RequestMethod.POST)
    public String doCreateItem(@PathVariable("groupId") Long groupId,
                               @CurrentUser Authentication authentication,
                               @ModelAttribute Item item,
                               HttpServletRequest request) {
        logger.info("adding a new item");
        User currentUser = (User) authentication.getPrincipal();
        String ownerId = request.getParameter("ownerId");
        User owner = userService.getUser(Long.parseLong(ownerId));
        item.setCreatedBy(currentUser);
        item.setCreatedDate(new Date());
        item.setOwner(owner);
        userService.createItem(groupId, item);
        return "redirect:/group/" + groupId + "/items";
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public String listGroups(@CurrentUser Authentication authentication, ModelMap modelMap) {
        Long userId = ((User)authentication.getPrincipal()).getId();
        List<Group> allGroups = userService.listAllGroups(userId);
        modelMap.addAttribute("allGroups", allGroups);
        return "user/list-groups";
    }

    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.GET)
    public String getGroupDetails(@PathVariable("groupId") Long groupId, ModelMap modelMap) {
        Group group = userService.getGroupDetails(groupId);
        modelMap.addAttribute("group", group);
        return "user/group-details";
    }
}
