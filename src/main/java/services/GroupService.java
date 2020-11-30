package services;

import domain.Group;
import domain.User;
import domain.UserType;
import storage.GroupRepository;

public class GroupService {

    private final AuthenticationService authenticationService;
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository, AuthenticationService authenticationService) {
        this.groupRepository = groupRepository;
        this.authenticationService = authenticationService;

    }

    public boolean createGroup(User user, Integer id, String name, String groupHeadLogin) {
        if (!(authenticationService.isValid(user) && user.getUserType() == UserType.ADMIN)) {
            return false;
        }
        groupRepository.createGroup(id, name, groupHeadLogin);
        return true;
    }

    public boolean editGroup(User user, Integer groupId, String newName, String newGroupHeadLogin) {
        if (!(authenticationService.isValid(user) && user.getUserType() == UserType.ADMIN)) {
            return false;
        }
        Group group = groupRepository.findGroupById(groupId);
        if (newName.equals("")) {
            newName = group.getName();
        }
        if (newGroupHeadLogin.equals("")) {
            newGroupHeadLogin = group.getGroupHeadLogin();
        }
        groupRepository.updateGroupInfo(groupId, newName, newGroupHeadLogin);
        return true;
    }

    public boolean deleteGroup(User user, Integer id, String name, String groupHeadLogin) {
        if (!(authenticationService.isValid(user) && user.getUserType() == UserType.ADMIN)) {
            return false;
        }
        groupRepository.deleteGroup(id, name, groupHeadLogin);
        return true;
    }
}
