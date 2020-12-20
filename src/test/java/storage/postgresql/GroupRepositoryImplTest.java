package storage.postgresql;

import domain.Group;
import domain.User;
import domain.UserType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import storage.GroupRepository;
import storage.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GroupRepositoryImplTest {

    private GroupRepository repository;
    private Connector connector;
    public UserRepository userRepository;
    private HashMap<String, User> userHashMap;

    @Before
    public void setUp() {
        Connector.Builder builder = new Connector.Builder();
        builder.setUrl("jdbc:postgresql://localhost:5432/experiment").setUser("postgres").setPassword("root");
        userHashMap = new HashMap<>();
        createHashMap();
        connector = new Connector(builder);
        connector.getConnection();
        userRepository = new UserRepositoryImpl(connector);
        repository = new GroupRepositoryImpl(connector, userRepository);
    }

    @Test
    public void saveNewEntity() {
        Group group1 = new Group(67, "IP-94", getUser("vasya092") , List.of());
        Group group2 = new Group(10, "10th Group", getUser("sssya092"), List.of());
        Integer groupId1 = group1.getId();
        Integer groupId2 = group2.getId();
        Group savedGroup1 = repository.saveNewEntity(group1);
        Group savedGroup2 = repository.saveNewEntity(group2);

        assertNotEquals(groupId1, savedGroup1.getId());
        assertNotEquals(groupId2, savedGroup2.getId());

        repository.deleteById(groupId1);
        repository.deleteById(groupId2);
    }

    @Test
    public void findAll() {
        Iterable<Group> groups = repository.findAll();
        assertNotNull(groups);
    }

    @Test
    public void findById() {
        Integer id1 = 1;
        Optional<Group> groupById1 = repository.findById(id1);
        assertTrue(groupById1.isPresent());

        Integer id2 = -3;
        Optional<Group> groupById2 = repository.findById(id2);
        assertFalse(groupById2.isPresent());
    }

    @Test
    public void deleteById() {
        Group group = repository.saveNewEntity(new Group(99, "Deleted Group", getUser("wqa092"), List.of()));
        repository.deleteById(group.getId());
        assertFalse(repository.existsById(group.getId()));
    }

    @Test
    public void existsById() {
        boolean isGroupExist1 = repository.existsById(1);
        boolean isGroupExist2 = repository.existsById(2);
        boolean isGroupExist3 = repository.existsById(3);

        assertTrue(isGroupExist1);
        assertTrue(isGroupExist2);
        assertTrue(isGroupExist3);

        boolean isGroupExist4 = repository.existsById(4);
        boolean isGroupExist5 = repository.existsById(5876);
        boolean isGroupExist6 = repository.existsById(688);

        assertFalse(isGroupExist4);
        assertFalse(isGroupExist5);
        assertFalse(isGroupExist6);
    }

    @Test
    public void findGroupByName() {
        Optional<Group> optionalGroup01 = Optional.of(new Group(1, "First Group", getUser("vasya092"),
                getListOfUsers(List.of("vasya092", "dominar3000", "wqa092", "eeesya092"))));
        Optional<Group> optionalGroup02 = Optional.of(new Group(2, "Second Group", getUser("sssya092"),
                getListOfUsers(List.of("dominat00", "wireas34", "va9rkw2", "sssya092"))));
        Optional<Group> optionalGroup03 = Optional.of(new Group(3, "Third Group", getUser("fsf1467"),
                getListOfUsers(List.of("nsddw002", "port92", "kolya073", "fsf1467"))));
        Optional<Group> optionalGroup1 = repository.findGroupByName("First Group");
        Optional<Group> optionalGroup2 = repository.findGroupByName("Second Group");
        Optional<Group> optionalGroup3 = repository.findGroupByName("Third Group");

        assertEquals(optionalGroup1, optionalGroup01);
        assertEquals(optionalGroup2, optionalGroup02);
        assertEquals(optionalGroup3, optionalGroup03);

        Optional<Group> optionalGroup4 = repository.findGroupByName("IP-99");
        Optional<Group> optionalGroup5 = repository.findGroupByName("HONOR");
        Optional<Group> optionalGroup6 = repository.findGroupByName("JustGroup");

        assertEquals(optionalGroup4, Optional.empty());
        assertEquals(optionalGroup5, Optional.empty());
        assertEquals(optionalGroup6, Optional.empty());
    }

    @Test
    public void update() {
        Group group1 = new Group(1, "First Group", getUser("vasya092"),
                getListOfUsers(List.of("vasya092", "dominar3000", "eeesya092")));
        Group group2 = new Group(2, "2 Group", getUser("sssya092"),
                getListOfUsers(List.of("sssya092", "dominat00", "wireas34", "va9rkw2")));
        Group group3 = new Group(3, "3", getUser("fsf1467"),
                getListOfUsers(List.of("fsf1467", "nsddw002", "port92", "kolya073")));

        repository.update(group1);
        repository.update(group2);
        repository.update(group3);

        Optional<Group> optionalGroup1 = repository.findGroupByName("First Group");
        Optional<Group> optionalGroup2 = repository.findGroupByName("2 Group");
        Optional<Group> optionalGroup3 = repository.findGroupByName("3");

        assertEquals(optionalGroup1, Optional.of(new Group(1, "First Group", getUser("vasya092"),
                getListOfUsers(List.of("vasya092", "dominar3000", "wqa092", "eeesya092")))));
        assertEquals(optionalGroup2, Optional.of(new Group(2, "2 Group", getUser("sssya092"),
                getListOfUsers(List.of("dominat00","wireas34","va9rkw2", "sssya092")))));
        assertEquals(optionalGroup3, Optional.of(new Group(3, "3", getUser("fsf1467"),
                getListOfUsers(List.of("nsddw002", "port92", "kolya073", "fsf1467")))));

        assertNotEquals(optionalGroup1, Optional.of(new Group(1, "Group", getUser("vasya092"),
                getListOfUsers(List.of("vasya092", "dominar3000", "wqa092", "eeesya092")))));
        assertNotEquals(optionalGroup2, Optional.of(new Group(2, "Secoup", getUser("sssya092"),
                getListOfUsers(List.of("sssya092", "dominat00", "wireas34", "va9rkw2")))));
        assertNotEquals(optionalGroup3, Optional.of(new Group(3, "Thioup", getUser("fsf1467"),
                getListOfUsers(List.of("fsf1467", "nsddw002", "port92", "kolya073")))));
    }

    @After
    public void setDown() {
        Group group1 = new Group(1, "First Group", getUser("vasya092"),
                getListOfUsers(List.of("vasya092", "dominar3000", "wqa092", "eeesya092")));
        Group group2 = new Group(2, "Second Group", getUser("sssya092"),
                getListOfUsers(List.of("sssya092", "dominat00", "wireas34", "va9rkw2")));
        Group group3 = new Group(3, "Third Group", getUser("fsf1467"),
                getListOfUsers(List.of("fsf1467", "nsddw002", "port92", "kolya073")));

        repository.update(group1);
        repository.update(group2);
        repository.update(group3);
    }

    private  void  createHashMap() {
        List<String> logins = List.of("vasya092", "dominar3000", "wqa092", "eeesya092",
                "sssya092", "dominat00", "wireas34", "va9rkw2",
                "fsf1467", "nsddw002", "port92", "kolya073");
        List<String> password = List.of("sdf323fs!F$@2f", "ssdfd23fsd@2f", "dsfhsfdh4","sdf34tgergf",
                "12QW@asd","ssdfd23fs4yd@2f","dsfhsfgfdh4","sdsdfsertgf","123sacQW@asd","#$d123SA@dA2",
                "dsfhsfdh4","sdf34tgergf");
        List<String> names = List.of("Ivaniuk V.O.", "Naluvayko R.I.", "Pokolyuk W.K.", "Ivanko K.O.",
                "NoName", "Nalutol R.I.", "Zelensky P.P.", "Ilonov K.E.", "Josepe S.A.",
                "Naluvayko R.I.", "Pokolyuk W.K.", "Ivanko K.O.");
        List<UserType> userTypes = List.of(UserType.GROUP_HEAD, UserType.STUDENT, UserType.STUDENT, UserType.STUDENT,
                UserType.GROUP_HEAD, UserType.STUDENT, UserType.STUDENT, UserType.STUDENT,
                UserType.GROUP_HEAD, UserType.STUDENT, UserType.STUDENT, UserType.STUDENT);
        List<Integer> groupIds = List.of(1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3);
        for (int i = 0; i < logins.size(); i++) {
            userHashMap.put(logins.get(i), new User(logins.get(i), password.get(i), names.get(i), userTypes.get(i), groupIds.get(i)));
        }
    }
    private User getUser(String login){
        return userHashMap.get(login);
    }

    private List<User> getListOfUsers(List<String> logins) {
        List<User> users = new ArrayList<>();
        for (String login : logins) {
            users.add(getUser(login));
        }
        return users;
    }
}
