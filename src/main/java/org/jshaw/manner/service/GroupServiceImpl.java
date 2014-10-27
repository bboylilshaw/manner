package org.jshaw.manner.service;

import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.User;
import org.jshaw.manner.repository.GroupRepository;
import org.jshaw.manner.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Override
    @Transactional
    public Group addGroup(String groupName, User owner) {
        Group group = new Group();
        group.setName(groupName);
        group.setOwner(owner);
        group.getUsers().add(owner);
        group.setCreatedDate(new Date());
        return groupRepository.save(group);
    }

    @Override
    @Transactional(readOnly = true)
    public Group getGroup(Long groupId) {
        return groupRepository.findOne(groupId);
    }

    @Override
    @Transactional
    public Group updateGroup(Long groupId, Group updatedGroup) {
        Group group = groupRepository.findOne(groupId);
        Assert.notNull(group, "group does not exist");
        BeanUtils.copyProperties(updatedGroup, group);
        return groupRepository.save(group);
    }

    @Override
    @Transactional
    public void deleteGroup(Long groupId) {
        Group group = groupRepository.getOne(groupId);
        group.getUsers().forEach(u -> {
            u.getGroups().remove(group);
        });
        group.setOwner(null);
        group.setUsers(null);
        groupRepository.delete(groupId);
    }

    @Override
    @Transactional
    public void transferOwnership(Long groupId, Long userId) {
        Group group = groupRepository.getOne(groupId);
        Assert.notNull(group, "group does not exist");

        User newOwner = userRepository.getOne(userId);
        Assert.notNull(newOwner, "user does not exist");

        group.setOwner(newOwner);
    }

    @Override
    @Transactional
    public void addMember(Long groupId, Long memberId) throws Exception {
        User member = userRepository.getOne(memberId);
        Assert.notNull(member, "user does not exist");

        Group group = groupRepository.getOne(groupId);

        List<User> userList = (List<User>) group.getUsers();
        if (userList.contains(member)) {
            throw new Exception("this group already contained the user");
        }

        group.getUsers().add(member);
        groupRepository.save(group);
    }

    @Override
    @Transactional
    public void removeMember(Long groupId, Long memberId) throws Exception {
        User member = userRepository.getOne(memberId);
        Assert.notNull(member, "user does not exist");

        Group group = groupRepository.getOne(groupId);

        List<User> userList = (List<User>) group.getUsers();

        if (group.getOwner().getId().equals(memberId)) {
            throw new RuntimeException("cannot remove the owner of group");
        }
        if (!userList.contains(member)) {
            throw new Exception("no such user found in the group");
        }

        group.getUsers().remove(member);
        groupRepository.save(group);

    }
}
