package storage;

import domain.User;
import domain.UserType;

public interface UserRepository {

    User getUserByLogin(String login);
    void addAccount(String login, String name, UserType userType, String password, Integer groupId);
    void deleteAccount(String login);

    void changeAccountType(String login, UserType newType);
    void changeAccountGroup(String login, Integer newGroupId);
    void changeAccountPassword(String login, String newPassword);

}
