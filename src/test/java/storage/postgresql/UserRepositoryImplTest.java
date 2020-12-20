package storage.postgresql;

import domain.User;
import domain.UserType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import storage.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;

public class UserRepositoryImplTest {

	private UserRepository repository;
	private Connector connector;

	@Before
	public void setUp() {
		Connector.Builder builder = new Connector.Builder();
		builder.setUrl("jdbc:postgresql://localhost:5433/experiment").setUser("postgres").setPassword("root");

		connector = new Connector(builder);
		connector.getConnection();
		repository = new UserRepositoryImpl(connector);
	}

	@Test
	public void saveNewEntity() {
		User user1 = new User("login1", "12345", "Lasfasc", UserType.STUDENT, 1);
		User user2 = new User("login1234", "12345", "Aafasdc", UserType.TEACHER, 2);
		repository.saveNewEntity(user1);
		repository.saveNewEntity(user2);
		assertTrue(repository.existsById(user1.getLogin()));
		assertTrue(repository.existsById(user2.getLogin()));
	}

	@Test
	public void findById() {
		Optional<User> user1 = repository.findById("vasya092");
		assertTrue(user1.isPresent());

		Optional<User> user2 = repository.findById("asdasdasdasd");
		assertFalse(user2.isPresent());
	}

	@Test
	public void findAll() {
		ArrayList<User> findAll = (ArrayList<User>) repository.findAll();
		User user1 = new User("vasya092", "dssdfds1124", "Ivaniuk V.O.", UserType.GROUP_HEAD, 1);
		User user2 = new User("dominar3000", "d23rfw124", "Naluvayko R.I.", UserType.STUDENT, 1);
		User user3 = new User("wqa092", "sdfhsdfdsf124", "Pokolyuk W.K.", UserType.STUDENT, 1);
		User user4 = new User("eeesya092", "ds34tertd4", "Ivanko K.O.", UserType.STUDENT, 1);
		User user5 = new User("12edasfaa", "12345", "12345", UserType.ADMIN, null);

		assertTrue(findAll.contains(user1));
		assertTrue(findAll.contains(user2));
		assertTrue(findAll.contains(user3));
		assertTrue(findAll.contains(user4));
		assertFalse(findAll.contains(user5));
	}

	@Test
	public void deleteById() {
		User user1 = new User("test", "123", "1 2 3", UserType.STUDENT, 1);
		repository.saveNewEntity(user1);
		repository.deleteById(user1.getLogin());
		assertFalse(repository.existsById(user1.getLogin()));
	}

	@Test
	public void existsById() {
		boolean isGroupExist1 = repository.existsById("vasya092");
		boolean isGroupExist2 = repository.existsById("sssya092");
		boolean isGroupExist3 = repository.existsById("directoria1");

		assertTrue(isGroupExist1);
		assertTrue(isGroupExist2);
		assertTrue(isGroupExist3);

		boolean isGroupExist4 = repository.existsById("NotExitt");
		boolean isGroupExist5 = repository.existsById("Popopo");
		boolean isGroupExist6 = repository.existsById("Lalalal");

		assertFalse(isGroupExist4);
		assertFalse(isGroupExist5);
		assertFalse(isGroupExist6);
	}

	@Test
	public void update() throws SQLException {
		User user1 = new User("fsf1467", "123sacQW@asd", "Josepe S.A.", UserType.GROUP_HEAD, 3);
		repository.update(user1);
		String statement1 = "SELECT * FROM Users WHERE name = 'Josepe S.A.'";
		ResultSet result1 = connector.executeStatement(statement1);
		assertTrue(result1.next());
		assertEquals(result1.getString("login"), "fsf1467");
		assertEquals(result1.getString("name"), "Josepe S.A.");
		assertEquals(result1.getInt("group_id"), 3);
		assertEquals(result1.getString("user_type"), "group_head");
		assertEquals(result1.getString("password_hash"), "123sacQW@asd");
		assertEquals(result1.getString("password_salt"), "123sacQW@asd");

		User user2 = new User("sssya092", "12QW@asd", "NoName", UserType.GROUP_HEAD, 2);
		repository.update(user2);
		String statement2 = "SELECT * FROM Users WHERE name = 'NoName'";
		ResultSet result2 = connector.executeStatement(statement2);
		assertTrue(result2.next());
		assertEquals(result2.getString("login"), "sssya092");
		assertEquals(result2.getString("name"), "NoName");
		assertEquals(result2.getInt("group_id"), 2);
		assertEquals(result2.getString("user_type"), "group_head");
		assertEquals(result2.getString("password_hash"), "12QW@asd");
		assertEquals(result2.getString("password_salt"), "12QW@asd");
	}

	@After
	public void setDown() {
		String statement1 = "UPDATE Users SET (login, name, group_id, user_type, password_salt, password_hash)" +
				" VALUES('fsf1467', 'Josepe S.A.', 3, 'group_head', '123sacQW@asd', '123456789');";
		String statement2 = "UPDATE Users SET (login, name, user_type, password_salt, password_hash)" +
				" VALUES('julius44', 'Julius Caesar', 'admin', 'rome3000', 'ADAsdas!3!@!#asd');";
		connector.executeStatement(statement1);
		connector.executeStatement(statement2);
	}
}
