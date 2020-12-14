package services;

import domain.Group;
import domain.User;
import domain.UserType;
import exceptions.EntityExistException;
import exceptions.EntityNotExistException;
import exceptions.PermissionException;
import org.junit.Before;
import org.junit.Test;
import storage.GroupRepository;

import static org.mockito.Mockito.*;

public class GroupServiceTest {

    private GroupService service;
    private GroupRepository mockedRepo;
    private User student;
    private User admin;
    private Group group;

    @Before
    public void initService() {
        mockedRepo = mock(GroupRepository.class);
        service = new GroupService(mockedRepo);
        student = new User("max", "1234", "Maxim Perevalov", UserType.STUDENT, 0);
        admin = new User("vova", "1234", "Vova Pomidor", UserType.ADMIN, 0);
        group = new Group(1, "IP-94", "max");
    }

    @Test(expected = PermissionException.class)
    public void saveNewGroupByNotAdmin() throws PermissionException {
        User caller = student;
        service.saveNewGroup(caller, group);
    }

    @Test(expected = EntityExistException.class)
    public void saveAlreadyExistsGroup() throws PermissionException {
        User caller = admin;
        when(mockedRepo.existsById(group.getId())).thenReturn(true);
        service.saveNewGroup(caller, group);
    }

    @Test
    public void saveNewGroup() throws PermissionException {
        User caller = admin;
        when(mockedRepo.existsById(group.getId())).thenReturn(false);
        service.saveNewGroup(caller, group);
        verify(mockedRepo, times(1)).saveNewEntity(group);
    }

    @Test(expected = PermissionException.class)
    public void editGroupByNotAdmin() throws PermissionException {
        User caller = student;
        service.editGroup(caller, group);
    }

    @Test(expected = EntityNotExistException.class)
    public void editGroupNotExistsGroup() throws PermissionException {
        User caller = admin;
        when(mockedRepo.existsById(group.getId())).thenReturn(false);
        service.editGroup(caller, group);
    }

    @Test
    public void editGroup() throws PermissionException {
        User caller = admin;
        when(mockedRepo.existsById(group.getId())).thenReturn(true);
        service.editGroup(caller, group);
        verify(mockedRepo, times(1)).update(group);
    }


    @Test(expected = PermissionException.class)
    public void deleteGroupByNotAdmit() throws PermissionException {
        User caller = student;
        when(mockedRepo.existsById(group.getId())).thenReturn(true);
        service.deleteGroup(caller, group.getId());
    }


    @Test(expected = EntityNotExistException.class)
    public void deleteNotExistsGroup() throws PermissionException {
        User caller = admin;
        when(mockedRepo.existsById(group.getId())).thenReturn(false);
        service.deleteGroup(caller, group.getId());
    }

    @Test
    public void deleteGroup() throws PermissionException {
        User caller = admin;
        when(mockedRepo.existsById(group.getId())).thenReturn(true);
        service.deleteGroup(caller, group.getId());
        verify(mockedRepo, times(1)).deleteById(group.getId());
    }

    @Test
    public void findGroupByName() {
    }
}
