package storage.postgresql;

import domain.Group;
import domain.User;
import lombok.AllArgsConstructor;
import storage.GroupRepository;
import storage.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

@AllArgsConstructor
public class GroupRepositoryImpl implements GroupRepository {

    private Connector connector;
    private UserRepository userRepository;

    @Override
    public Group saveNewEntity(Group entity) {
        String name = entity.getName();
        User grouphead = entity.getGroupHead();
        String statement = "INSERT INTO Groups(name, grouphead_login) " +
                "VALUES('" + name + "', '" + grouphead.getLogin() + "') RETURNING id";
        ResultSet result = connector.executeStatement(statement);
        try {
            while(result.next()) {
                Integer id = result.getInt(1);
                entity.setId(id);
            }
            return entity;
        } catch (SQLException exception) {
            return null;
        }
    }

    @Override
    public Optional<Group> findById(Integer id) {
        String statement = "SELECT * FROM Groups WHERE id = " + id;
        ResultSet result = connector.executeStatement(statement);
        if (result != null) {
            try {
                while (result.next()) {
                    String name = result.getString("name");
                    return getGroup(id, result, name);
                }
            } catch (SQLException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private Optional<Group> getGroup(Integer id, ResultSet result, String name) throws SQLException {
        String groupHeadLogin = result.getString("grouphead_login");
        String getLoginsStatement = "SELECT login FROM Users WHERE group_id = " + id +
                " AND (user_type = 'student' OR user_type = 'group_head')";
        ResultSet resultMembers = connector.executeStatement(getLoginsStatement);
        ArrayList<User> members = new ArrayList<>();
        while(resultMembers.next()) {
            Optional<User> member = userRepository.findById(resultMembers.getString(1));
            member.ifPresent(members::add);
        }
        User groupHead = null;
        Optional<User> groupHeadOptional = userRepository.findById(groupHeadLogin);
        if (groupHeadOptional.isPresent()) {
            groupHead = groupHeadOptional.get();
        }
        return Optional.of(new Group(id, name, groupHead, members));
    }

    @Override
    public Iterable<Group> findAll() {
        String statement = "SELECT * FROM Groups";
        ResultSet result = connector.executeStatement(statement);
        ArrayList<Group> groupList = new ArrayList<>();
        try {
            while (result.next()) {
                Integer id = result.getInt(1);
                String name = result.getString(2);
                String getLoginsStatement = "SELECT login FROM Users WHERE group_id = " + id +
                        " AND (user_type = 'student' OR user_type = 'group_head')";
                ResultSet resultLogins = connector.executeStatement(getLoginsStatement);
                Optional<Group> group = getGroup(id, resultLogins, name);
                group.ifPresent(groupList::add);
            }
        } catch (SQLException exception) {
            return null;
        }
        return groupList;
    }

    @Override
    public void deleteById(Integer id) {
        String statement = "WITH delete_presents as (DELETE FROM Presents " +
                "WHERE lesson_id = (SELECT id FROM Lessons WHERE group_id = " + id + "))," +
                "delete_members as (DELETE FROM Users WHERE group_id = " + id + "), " +
                "delete_lessons as (DELETE FROM Lessons WHERE group_id = " + id + ") " +
                "DELETE FROM Groups WHERE id = " + id;
        connector.executeStatement(statement);
    }

    @Override
    public void update(Group entity) {
        User groupHead = entity.getGroupHead();
        String statement = "UPDATE Groups SET " + "(name, grouphead_login) = " +
                "('" + entity.getName() + "', '" + groupHead.getLogin()+"') " +
                "WHERE id = " + entity.getId();
        connector.executeStatement(statement);
    }

    @Override
    public boolean existsById(Integer id) {
        String statement = "SELECT * FROM Groups WHERE id = " + id;
        ResultSet result = connector.executeStatement(statement);
        try {
            return result.next();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Optional<Group> findGroupByName(String name) {
        String statement = "SELECT * FROM Groups WHERE name = '"
                + name + "'";
        ResultSet result = connector.executeStatement(statement);
        if (result == null) {
            return Optional.empty();
        } else {
            try {
                while (result.next()) {
                    Integer id = (int) result.getLong("id");
                    return getGroup(id, result, name);
                }
            } catch (SQLException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
