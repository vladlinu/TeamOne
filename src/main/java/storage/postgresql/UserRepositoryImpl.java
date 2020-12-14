package storage.postgresql;

import domain.User;
import domain.UserType;
import storage.UserRepository;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    public Connector connector;
    private final HashMap<UserType, String> transformations;

    public UserRepositoryImpl(Connector connector) {
        this.connector = connector;
        transformations = new HashMap<>();
        transformations.put(UserType.STUDENT, "student");
        transformations.put(UserType.GROUP_HEAD, "group_head");
        transformations.put(UserType.TEACHER, "teacher");
        transformations.put(UserType.ADMIN, "admin");
    }

    @Override
    public User saveNewEntity(User entity) {
        return null;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public void update(User entity) {
        String newUserType = transformations.get(entity.getUserType());
        String statement = "UPDATE Users SET " + "(name, group_id, user_type, password_salt, password_hash) = " +
                "('" + entity.getName() + "', " + entity.getGroupId() + ", '" + newUserType +
                "', '" + entity.getPassword() + "', '" + entity.getPassword() + "') " +
                "WHERE login = '" + entity.getLogin() + "'";
        connector.executeStatement(statement);
    }

    @Override
    public boolean existsById(String login) {
        String statement = "EXISTS (SELECT * FROM Users WHERE login = '"
                + login + "')";
        ResultSet result = connector.executeStatement(statement);
        return result != null;
    }
}
