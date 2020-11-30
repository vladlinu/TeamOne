package storage;

import domain.User;

public interface UserRepository {

    User getUserByLogin(String login);
}
