package org.jshaw.manner.web.controller;

import org.jshaw.manner.common.GlobalConstants;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public String createGroup() {
        return Views.ADD_GROUP_PAGE;
    }

    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public String doCreateGroup(@CurrentUser Authentication authentication,
                                HttpServletRequest request,
                                RedirectAttributes redirectAttributes) {
        String groupName = request.getParameter("groupName");
        User currentUser = (User) authentication.getPrincipal();
        groupService.addGroup(groupName, currentUser);
        redirectAttributes.addFlashAttribute(GlobalConstants.MESSAGE_TYPE, "info");
        redirectAttributes.addFlashAttribute(GlobalConstants.MESSAGE, "Added group successfully!");
        return Views.REDIRECT_TO_HOME_PAGE;
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public String listGroups(@CurrentUser Authentication authentication, ModelMap modelMap) {
        User currentUser = (User) authentication.getPrincipal();
        List<Group> groups = groupService.listGroups(currentUser.getId());
        modelMap.addAttribute("groups", groups);
        return Views.LIST_GROUPS_PAGE;
    }

    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.GET)
    public String getGroupDetails(@PathVariable("groupId") Long groupId, ModelMap modelMap) {
        Group group = groupService.getGroup(groupId);
        modelMap.addAttribute("group", group);
        return Views.GROUP_DETAILS_PAGE;
    }

    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.DELETE)
    public String deleteGroup(@PathVariable("groupId") Long groupId, RedirectAttributes redirectAttributes) {
        groupService.deleteGroup(groupId);
        redirectAttributes.addFlashAttribute(GlobalConstants.MESSAGE_TYPE, "info");
        redirectAttributes.addFlashAttribute(GlobalConstants.MESSAGE, "Deleted group successfully!");
        return Views.REDIRECT_TO_HOME_PAGE;
    }

}
