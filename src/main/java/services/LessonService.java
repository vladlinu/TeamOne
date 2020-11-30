package services;

import domain.Lesson;
import domain.User;
import domain.UserType;
import storage.LessonRepository;
import storage.UserRepository;

import java.time.LocalDateTime;

public class LessonService {

    private final AuthenticationService authenticationService;
    private final LessonRepository lessonRepository;
    private UserRepository userRepository;

    public LessonService(AuthenticationService authenticationService, LessonRepository lessonRepository) {
        this.authenticationService = authenticationService;
        this.lessonRepository = lessonRepository;
    }

    public boolean setLessonHomework(User user, Integer lessonId, String homework) {
        Lesson lesson = lessonRepository.getLessonById(lessonId);
        if (!(authenticationService.isValid(user) ||
                user.getUserType().equals(UserType.TEACHER) ||
                user.getUserType().equals(UserType.ADMIN) ||
                (user.getUserType().equals(UserType.GROUP_HEAD) && lesson.getGroupId().equals(user.getGroupId())))) {
            return false;
        }
        lesson.setHomework(homework);
        return true;
    }

    public String getLessonHomework(User user, Integer lessonId) {
        if (!authenticationService.isValid(user)) {
            return null;
        }
        Lesson lesson = lessonRepository.getLessonById(lessonId);
        return lesson.getHomework();
    }

    public boolean createLesson(User user, Integer lessonId, LocalDateTime dateTime, String description, String discipline, Integer groupId, String teacherLogin) {
        if (!(authenticationService.isValid(user) ||
                user.getUserType().equals(UserType.TEACHER) ||
                user.getUserType().equals(UserType.ADMIN) ||
                (user.getUserType().equals(UserType.GROUP_HEAD) && groupId.equals(user.getGroupId())))) {
            return false;
        }
        lessonRepository.createLesson(lessonId, dateTime, description, discipline, groupId, teacherLogin);
        return true;
    }

    public boolean deleteLesson(User user, Integer lessonId) {
        if (!(authenticationService.isValid(user) || user.getUserType().equals(UserType.ADMIN))) {
            return false;
        }
        lessonRepository.deleteLesson(lessonId);
        return true;
    }

    public boolean editLesson(User user, Integer lessonId, LocalDateTime newDateTime, String newDescription, String newDiscipline, Integer newGroupId, String newTeacherLogin) {
        if (!(authenticationService.isValid(user) || user.getUserType().equals(UserType.ADMIN))) {
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
}
