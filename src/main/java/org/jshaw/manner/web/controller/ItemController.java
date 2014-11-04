package org.jshaw.manner.web.controller;

import org.jshaw.manner.common.Views;
import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.Item;
import org.jshaw.manner.domain.User;
import org.jshaw.manner.security.CurrentUser;
import org.jshaw.manner.service.GroupService;
import org.jshaw.manner.service.ItemService;
import org.jshaw.manner.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private GroupService groupService;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @ModelAttribute("groups")
    private List<Group> groups(@CurrentUser Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return groupService.listGroups(currentUser.getId());
    }

    @RequestMapping(value = "/group/{groupId}/items", method = RequestMethod.GET)
    public String listGroupItems(@PathVariable("groupId") Long groupId,
                                 @RequestParam(value = "startPage", defaultValue = "0") int startPage,
                                 ModelMap modelMap) {
        int pageSize = 10; //return 10 items each time
        Page<Item> itemPage = itemService.listItemsInGroup(groupId, startPage, pageSize);
        modelMap.addAttribute("page", itemPage);
        modelMap.addAttribute("groupId", groupId);
        return Views.LIST_ITEMS_PAGE;
    }

    @RequestMapping(value = "/group/{groupId}/item", method = RequestMethod.GET)
    public String addItem(@PathVariable("groupId") Long groupId, ModelMap modelMap) {
        logger.info("render add item page");
        List<User> members = (List<User>) groupService.getGroup(groupId).getUsers();
        modelMap.addAttribute("groupId", groupId);
        modelMap.addAttribute("members", members);
        return Views.ADD_ITEM_PAGE;
    }

    @RequestMapping(value = "/group/{groupId}/item", method = RequestMethod.POST)
    public String doAddItem(@PathVariable("groupId") Long groupId,
                            @ModelAttribute Item item,
                            HttpServletRequest request) {
        logger.info("adding a new item");
        String ownerId = request.getParameter("ownerId");
        User owner = userService.getUser(Long.parseLong(ownerId));
        item.setOwner(owner);
        itemService.addItem(groupId, item);
        return "redirect:/group/" + groupId + "/items";
    }

    @RequestMapping(value = "/group/{groupId}/item/{itemId}", method = RequestMethod.GET)
    public Object itemDetails(@PathVariable("groupId") Long groupId,
                              @PathVariable("itemId") Long itemId,
                              ModelMap modelMap) {
        logger.info("get item details");
        Item item = itemService.getItem(itemId);
        modelMap.addAttribute("item", item);
        return Views.ITEM_DETAILS_PAGE;
    }

//    @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    @ResponseBody
//    public String doUploadAttachment(HttpServletRequest request,
//                                     MultipartHttpServletRequest multipartHttpServletRequest) {
//
//        FileInputStream inputStream = multipartHttpServletRequest.getFile("attachment").getInputStream();
//        gridFsTemplate.store()
//
//        logger.info("uploading an attachment");
//        return "upload successfully!";
//    }

}
