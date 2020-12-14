package storage;

import domain.Group;

public interface GroupRepository {

    void createGroup(Integer groupId, String name, String groupHeadLogin);
    void deleteGroup(Integer groupId, String name, String groupHeadLogin);
    Group findGroupById(Integer groupId);
    void updateGroupInfo(Integer groupId, String newName, String newGroupHeadLogin);
}
