package services;

import domain.Group;
import domain.User;
import exceptions.PermissionException;
import storage.GroupRepository;

import java.util.Optional;

import static exceptions.EntityExistException.groupAlreadyExists;
import static exceptions.EntityNotExistException.*;
import static exceptions.PermissionException.*;

public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void saveNewGroup(User caller, Group group) throws PermissionException, IllegalArgumentException {
        if (!caller.isAdmin()) {
            throw notEnoughPermission(caller);
        }
        Optional<Group> groupWithSameName = groupRepository.findGroupByName(group.getName());
        if (groupWithSameName.isPresent()) {
            throw groupAlreadyExists(group);
        }
        group.setId(null);
        groupRepository.saveNewEntity(group);
    }

    public void editGroup(User caller, Group group) throws PermissionException {
        if (!caller.isAdmin()) {
            throw notEnoughPermission(caller);
        }
        if (!groupRepository.existsById(group.getId())) {
            throw groupIsNotExist(group);
        }
        groupRepository.update(group);
    }

    public void deleteGroup(User caller, Integer groupId) throws PermissionException {
        if (!caller.isAdmin()) {
            throw notEnoughPermission(caller);
        }
        if (groupRepository.existsById(groupId)) {
            throw groupIsNotExist(groupId);
        }
        groupRepository.deleteById(groupId);
    }
}
