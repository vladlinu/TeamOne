package services;

import domain.Group;
import domain.User;
import domain.UserType;
import exceptions.EntityExistException;
import exceptions.PermissionException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import storage.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService service;
    private UserRepository mockedRepo;
    private User student;
    private User admin;
    private Group group;

    @Before
    public void setUp() {
        mockedRepo = mock(UserRepository.class);
        service = new UserService(mockedRepo);
        student = new User("max", "1234", "Maxim Perevalov", UserType.STUDENT, 0);
        group = new Group(1, "IP-94", student, List.of(
                new User("vania", "1234", "Vania", UserType.STUDENT, 0),
                new User("oleg", "1234", "Oleg", UserType.STUDENT, 0),
                new User("vladimir", "1234", "Vladimir", UserType.STUDENT, 0)
        ));

        admin = new User("vova", "1234", "Vova Pomidor", UserType.ADMIN, null);
    }

    @Test(expected = PermissionException.class)
    public void saveNewUserByNotAdmin() throws PermissionException {
        User caller = student;
        service.saveNewUser(caller, student);
    }

    @Test(expected = EntityExistException.class)
    public void saveExistsUser() throws PermissionException {
        User caller = admin;
        when(mockedRepo.existsById(student.getLogin())).thenReturn(true);
        service.saveNewUser(caller, student);
    }

    @Test
    public void deleteUser() throws PermissionException {
        User caller = admin;
        when(mockedRepo.existsById(student.getLogin())).thenReturn(true);
        service.deleteUser(caller, student.getLogin());
        verify(mockedRepo, times(1)).deleteById(student.getLogin());
    }

    @Test(expected = PermissionException.class)
    public void editUserTypeByNotAdmin() throws PermissionException {
        User caller = student;
        User user = new User("kola", "1234", "Lexa", UserType.TEACHER, 0);
        User newUser = new User("kola", "1234", "Lexa", UserType.GROUP_HEAD, 0);
        when(mockedRepo.findById(user.getLogin())).thenReturn(Optional.of(user));
        service.editUser(caller, newUser);
    }

    @Test
    public void getUserByLogin() {
        when(mockedRepo.findById(student.getLogin())).thenReturn(Optional.of(student));
        Optional<User> user = service.getUserByLogin(student.getLogin());
        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(student, user.get());
    }
}
