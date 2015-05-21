package org.jshaw.manner.web.controller;

import org.hibernate.pretty.MessageHelper;
import org.jshaw.manner.common.GlobalConstants;
import org.jshaw.manner.common.MessageType;
import org.jshaw.manner.common.Views;
import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.User;
import org.jshaw.manner.security.CurrentUser;
import org.jshaw.manner.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@Controller
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService groupService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public String createGroup() {
        return Views.ADD_GROUP_PAGE;
    }

    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public String doCreateGroup(@CurrentUser Authentication authentication,
                                HttpServletRequest request,
                                RedirectAttributes redirectAttributes,
                                Locale locale) {
        logger.info(locale.toString());
        String groupName = request.getParameter("groupName");
        User currentUser = (User) authentication.getPrincipal();
        Group group = new Group();
        group.setName(groupName);
        group.setOwner(currentUser);
        groupService.addGroup(group, currentUser);
        redirectAttributes.addFlashAttribute(GlobalConstants.MESSAGE_TYPE, MessageType.INFO)
                .addFlashAttribute(GlobalConstants.MESSAGE, messageSource.getMessage("add.group.success",null,locale));
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
    public String deleteGroup(@PathVariable("groupId") Long groupId, RedirectAttributes redirectAttributes, Locale locale) {
        groupService.deleteGroup(groupId);
        redirectAttributes.addFlashAttribute(GlobalConstants.MESSAGE_TYPE, "info")
                .addFlashAttribute(GlobalConstants.MESSAGE, messageSource.getMessage("delete.group.success", null, locale));
        return Views.REDIRECT_TO_HOME_PAGE;
    }

}
