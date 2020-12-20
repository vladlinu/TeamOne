package storage.postgresql;

import domain.User;
import domain.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import storage.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Data
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private Connector connector;
	private final HashMap<UserType, String> convertUserTypeForm = init();

	@Override
	public User saveNewEntity(User entity) {
		String statement = "INSERT INTO Users(login, name, group_id, user_type, password_salt, password_hash) VALUES('"
				+ entity.getLogin() + "', '" + entity.getName() + "', '" + entity.getGroupId() +
				"', '" + entity.getUserType().toString().toLowerCase() + "', '" + entity.getPassword() + "', '" + entity.getPassword() + "')";
		connector.executeStatement(statement);
		return entity;
	}

	@Override
	public Optional<User> findById(String login) {
		String statement = "SELECT * FROM Users WHERE login = '" + login + "'";
		ResultSet result = connector.executeStatement(statement);
		if (result != null) {
			try {
				result.next();
				String name = result.getString(2);
				String userTypeString = result.getString(4);
				UserType userType = null;
				for (Map.Entry entry : convertUserTypeForm.entrySet()) {
					if (entry.getValue().equals(userTypeString)) {
						userType = (UserType) entry.getKey();
					}
				}
				Integer groupId = result.getInt(3);
				String password = result.getString(5);
				User user = new User(login, password, name, userType, groupId);
				return Optional.of(user);
			} catch (SQLException e) {
				return Optional.empty();
			}
		}
		else {
			return Optional.empty();
		}
		return Optional.empty();
	}

	@Override
	public Iterable<User> findAll() {
		String statement = "SELECT * FROM Users";
		ResultSet result = connector.executeStatement(statement);
		ArrayList<User> userList = new ArrayList<>();
		try {
			while (result.next()) {
				String login = result.getString(1);
				String name = result.getString(2);
				String userTypeString = result.getString(3);
				UserType userType = null;
				for (Map.Entry entry : convertUserTypeForm.entrySet()) {
					if (entry.getValue().equals(userTypeString)) {
						userType = (UserType) entry.getKey();
					}
				}
				Integer groupId = result.getInt(4);
				String password = result.getString(5);
				User user = new User(login, password, name, userType, groupId);
				userList.add(user);
			}
			return userList;
		} catch (SQLException exception) {
			return null;
		}
	}

	@Override
	public void deleteById(String id) {
		String statement = "DELETE FROM Users WHERE login = '" + id + "'";
		connector.executeStatement(statement);
	}

	@Override
	public void update(User entity) {
		String newUserType = convertUserTypeForm.get(entity.getUserType());
		String statement = "UPDATE Users SET " + "(name, group_id, user_type, password_salt, password_hash) = " +
				"('" + entity.getName() + "', " + entity.getGroupId() + ", '" + newUserType +
				"', '" + entity.getPassword() + "', '" + entity.getPassword() + "') " +
				"WHERE login = '" + entity.getLogin() + "'";
		connector.executeStatement(statement);
	}

	@Override
	public boolean existsById(String login) {
		String statement = "SELECT * FROM Users WHERE login = '"
				+ login + "'";
		ResultSet result = connector.executeStatement(statement);
		try {
			return result.next();
		} catch (SQLException e) {
			return false;
		}
	}

	private HashMap<UserType, String> init() {
		HashMap<UserType, String> convertHashMap = new HashMap<>();
		convertHashMap.put(UserType.STUDENT, "student");
		convertHashMap.put(UserType.GROUP_HEAD, "group_head");
		convertHashMap.put(UserType.TEACHER, "teacher");
		convertHashMap.put(UserType.ADMIN, "admin");
		return convertHashMap;
	}
}
