package services;

import domain.Lesson;
import domain.User;
import domain.UserType;
import storage.LessonRepository;
import storage.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

public class LessonService {

    private final AuthenticationService authenticationService;
    private final LessonRepository lessonRepository;
    private UserRepository userRepository;

    LessonService(AuthenticationService authenticationService, LessonRepository lessonRepository) {
        this.authenticationService = authenticationService;
        this.lessonRepository = lessonRepository;
    }

    boolean setLessonHomework(User user, Integer lessonId, String homework) {
        Lesson lesson = lessonRepository.getLessonById(lessonId);
        if (!(authenticationService.isValid(user) &&
                (user.getUserType().equals(UserType.TEACHER) ||
                        user.getUserType().equals(UserType.ADMIN) ||
                        (user.getUserType().equals(UserType.GROUP_HEAD) && lesson.getGroupId().equals(user.getGroupId()))))) {
            return false;
        }
        lesson.setHomework(homework);
        return true;
    }

    String getLessonHomework(User user, Integer lessonId) {
        if (!authenticationService.isValid(user)) {
            return null;
        }
        Lesson lesson = lessonRepository.getLessonById(lessonId);
        return lesson.getHomework();
    }

    boolean createLesson(User user, Integer lessonId, LocalDateTime dateTime, String description, String discipline, Integer groupId, String teacherLogin) {
        if (!(authenticationService.isValid(user) &&
                (user.getUserType().equals(UserType.TEACHER) ||
                        user.getUserType().equals(UserType.ADMIN) ||
                        (user.getUserType().equals(UserType.GROUP_HEAD) && groupId.equals(user.getGroupId()))))) {
            return false;
        }
        lessonRepository.createLesson(lessonId, dateTime, description, discipline, groupId, teacherLogin);
        return true;
    }

    boolean deleteLesson(User user, Integer lessonId) {
        if (!(authenticationService.isValid(user) && user.getUserType().equals(UserType.ADMIN))) {
            return false;
        }
        lessonRepository.deleteLesson(lessonId);
        return true;
    }

    boolean editLesson(User user, Integer lessonId, LocalDateTime newDateTime, String newDescription, String newDiscipline, Integer newGroupId, String newTeacherLogin) {
        if (!(authenticationService.isValid(user) && user.getUserType().equals(UserType.ADMIN))) {
            return false;
        }
        Lesson lesson = lessonRepository.getLessonById(lessonId);
        if (newDateTime == null) {
            newDateTime = lesson.getDateTime();
        }
        if (newDescription.equals("")) {
            newDescription = lesson.getDescription();
        }
        if (newDiscipline.equals("")) {
            newDiscipline = lesson.getDiscipline();
        }
        if (newGroupId == null) {
            newGroupId = lesson.getGroupId();
        }
        if (newTeacherLogin.equals("")) {
            newTeacherLogin = lesson.getTeacherLogin();
        }
        lessonRepository.updateLessonInfo(lessonId, newDateTime, newDescription, newDiscipline, newGroupId, newTeacherLogin);
        return true;
    }

    boolean removePresence(User user, Integer lessonId, String studentLogin) {
        Lesson lesson = lessonRepository.getLessonById(lessonId);
        if (!(authenticationService.isValid(user) && user.getUserType().equals(UserType.ADMIN))) {
            return false;
        }
        lesson.removePresent(studentLogin);
        return true;
    }

    boolean addPresence(User user, Integer lessonId, String studentLogin) {
        Lesson lesson = lessonRepository.getLessonById(lessonId);
        if (!(authenticationService.isValid(user) && user.getUserType().equals(UserType.ADMIN)
                && user.getUserType().equals(UserType.TEACHER))) {
            return false;
        }
        lesson.addPresent(studentLogin);
        return true;
    }

    Set<String> getPresence(User user, Integer lessonId) {
        Lesson lesson = lessonRepository.getLessonById(lessonId);
        if (authenticationService.isValid(user)) {
            return lesson.getPresentStudentLogins();
        }
        return Collections.emptySet();
    }
}
