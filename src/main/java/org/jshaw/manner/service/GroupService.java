package org.jshaw.manner.service;

import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.User;

import java.util.List;

public interface GroupService {

    Group addGroup(String groupName, User owner);

    Group getGroup(Long groupId);

    List<Group> listGroups(Long userId);

    Group updateGroup(Long groupId, Group updatedGroup);

    void deleteGroup(Long groupId);

    void transferOwnership(Long groupId, Long userId);

    void addMember(Long groupId, Long memberId) throws Exception;

    void removeMember(Long groupId, Long memberId) throws Exception;

}
