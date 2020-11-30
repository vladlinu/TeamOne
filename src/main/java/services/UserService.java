package services;


import domain.User;
import domain.UserType;
import storage.UserRepository;

public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public UserService(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    boolean addAccount(User caller, String login, String name, UserType userType, String password, Integer groupId) {
        if (!(authenticationService.isValid(caller) && caller.getUserType() == UserType.ADMIN)) {
            return false;
        }
        try {
            userRepository.addAccount(login, name, userType, password, groupId);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    boolean deleteAccount(User caller, String login) {
        if (!(authenticationService.isValid(caller) && caller.getUserType() == UserType.ADMIN)) {
            return false;
        }
        try {
            userRepository.deleteAccount(login);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    boolean changeAccountType(User caller, String login, UserType newType) {
        if (!(authenticationService.isValid(caller) && caller.getUserType() == UserType.ADMIN)) {
            return false;
        }
        try {
            userRepository.changeAccountType(login, newType);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    boolean changeAccountGroup(User caller, String login, Integer newGroupId) {
        if (!(authenticationService.isValid(caller) && caller.getUserType() == UserType.ADMIN)) {
            return false;
        }
        try {
            userRepository.changeAccountGroup(login, newGroupId);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    boolean changeAccountPassword(User caller, String login, String newPassword) {
        if (!(authenticationService.isValid(caller) && caller.getUserType() == UserType.ADMIN)) {
            return false;
        }
        try {
            userRepository.changeAccountPassword(login, newPassword);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
