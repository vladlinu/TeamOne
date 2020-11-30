package services;

import domain.User;
import storage.UserRepository;

public class AuthenticationService {

    UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    boolean isValid(User user) {
        User validUser = userRepository.getUserByLogin(user.getLogin());
        return validUser != null && validUser.getPassword().equals(user.getPassword());
    }
}
