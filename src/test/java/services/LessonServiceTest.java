package services;

import domain.Group;
import domain.Lesson;
import domain.User;
import domain.UserType;
import exceptions.EntityNotExistException;
import exceptions.PermissionException;
import org.junit.Before;
import org.junit.Test;
import storage.GroupRepository;
import storage.LessonRepository;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LessonServiceTest {

    private LessonRepository mockedLessonRepo;
    private GroupRepository mockedGroupRepo;
    private LessonService service;
    private Lesson lesson;
    private User student;
    private User admin;
    private User teacher;
    private Group group;

    @Before
    public void setUp() {
        mockedLessonRepo = mock(LessonRepository.class);
        mockedGroupRepo = mock(GroupRepository.class);
        student = new User("max", "1234", "Maxim Perevalov", UserType.STUDENT, 0);
        group = new Group(1, "IP-94", student, List.of(
                new User("vania", "1234", "Vania", UserType.STUDENT, 0),
                new User("oleg", "1234", "Oleg", UserType.STUDENT, 0),
                new User("vladimir", "1234", "Vladimir", UserType.STUDENT, 0)
        ));
        service = new LessonService(mockedLessonRepo, mockedGroupRepo);
        admin = new User("vova", "1234", "Vova Pomidor", UserType.ADMIN, null);
        teacher = new User("Olesnadr", "1234", "Саша", UserType.TEACHER, null);
        lesson = new Lesson(0, LocalDateTime.now(), "Good lesson", "English", "do nothing", group, teacher, new HashMap<>(Map.of("max", true)));
    }

    @Test(expected = EntityNotExistException.class)
    public void setNotExistsLessonHomework() throws PermissionException {
        User caller = admin;
        when(mockedLessonRepo.findById(lesson.getLessonId())).thenReturn(Optional.empty());
        service.setLessonHomework(caller, 0, "make presentation");
    }

    @Test
    public void setLessonHomework() throws PermissionException {
        User caller = student;
        when(mockedLessonRepo.findById(lesson.getLessonId())).thenReturn(Optional.of(lesson));
        when(mockedGroupRepo.findById(lesson.getGroup().getId())).thenReturn(Optional.of(group));
        String homework = "make presentation";
        service.setLessonHomework(caller, 0, homework);
        verify(mockedLessonRepo, times(1)).update(lesson);
        assertEquals(lesson.getHomework(), homework);
    }

    @Test(expected = EntityNotExistException.class)
    public void saveLessonWithWrongGroup() throws PermissionException {
        User caller = admin;
        when(mockedGroupRepo.findById(lesson.getLessonId())).thenReturn(Optional.empty());
        service.saveLesson(caller, lesson);
    }

    @Test
    public void saveLesson() throws PermissionException {
        User caller = admin;
        when(mockedGroupRepo.findById(lesson.getLessonId())).thenReturn(Optional.of(group));
        service.saveLesson(caller, lesson);
        verify(mockedLessonRepo, times(1)).saveNewEntity(lesson);
    }

    @Test(expected = PermissionException.class)
    public void deleteLessonByNotAdmit() throws PermissionException {
        User caller = student;
        when(mockedLessonRepo.existsById(lesson.getLessonId())).thenReturn(true);
        service.deleteLesson(caller, lesson.getLessonId());
    }

    @Test(expected = EntityNotExistException.class)
    public void deleteNotExistsLesson() throws PermissionException {
        User caller = admin;
        when(mockedLessonRepo.existsById(lesson.getLessonId())).thenReturn(false);
        service.deleteLesson(caller, lesson.getLessonId());
    }

    @Test
    public void deleteLesson() throws PermissionException {
        User caller = admin;
        when(mockedLessonRepo.existsById(lesson.getLessonId())).thenReturn(true);
        service.deleteLesson(caller, lesson.getLessonId());
        verify(mockedLessonRepo, times(1)).deleteById(lesson.getLessonId());
    }

    @Test
    public void editLesson() throws PermissionException {
        User caller = admin;
        when(mockedLessonRepo.existsById(lesson.getLessonId())).thenReturn(true);
        service.editLesson(caller, lesson);
        verify(mockedLessonRepo, times(1)).update(lesson);
    }

    @Test(expected = EntityNotExistException.class)
    public void editNotExistsLesson() throws PermissionException {
        User caller = admin;
        when(mockedLessonRepo.existsById(lesson.getLessonId())).thenReturn(false);
        service.editLesson(caller, lesson);
    }

    @Test(expected = PermissionException.class)
    public void editLessonByNotAdmin() throws PermissionException {
        User caller = student;
        service.editLesson(caller, lesson);
    }

    @Test
    public void removeStudentPresence() throws PermissionException {
        User caller = admin;
        when(mockedGroupRepo.findById(lesson.getGroup().getId())).thenReturn(Optional.of(group));
        when(mockedLessonRepo.findById(lesson.getLessonId())).thenReturn(Optional.of(lesson));
        service.removeStudentPresence(caller, lesson.getLessonId(), student.getLogin());
        assertEquals(Collections.emptySet(), lesson.getPresentStudentLogins());
        verify(mockedLessonRepo, times(1)).update(lesson);
    }

    @Test
    public void addStudentPresence() throws PermissionException {
        User caller = admin;
        lesson.removePresent(student.getLogin());
        when(mockedGroupRepo.findById(lesson.getGroup().getId())).thenReturn(Optional.of(group));
        when(mockedLessonRepo.findById(lesson.getLessonId())).thenReturn(Optional.of(lesson));
        service.addStudentPresence(caller, lesson.getLessonId(), student.getLogin());
        assertEquals(Set.of(student.getLogin()), lesson.getPresentStudentLogins());
        verify(mockedLessonRepo, times(1)).update(lesson);
    }

    @Test
    public void getPresence() {
        User caller = admin;
        lesson.removePresent(student.getLogin());
        when(mockedGroupRepo.findById(lesson.getGroup().getId())).thenReturn(Optional.of(group));
        when(mockedLessonRepo.findById(lesson.getLessonId())).thenReturn(Optional.of(lesson));
        Set<String> presence = service.getPresence(caller, lesson.getLessonId());
        assertEquals(lesson.getPresentStudentLogins(), presence);
    }
}
