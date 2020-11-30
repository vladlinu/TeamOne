package storage;

import domain.Lesson;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface LessonRepository {

    List<Lesson> getLessonsByGroupIdFromInterval(String groupId, LocalDate from, LocalDate to);
    List<Lesson> getLessonsByTeacherIdFromInterval(String teacherLogin, LocalDate from, LocalDate to);
    Lesson getLessonById(Integer lessonId);
    void createLesson(Integer lessonId, LocalDateTime dateTime, String description, String discipline, Integer groupId, String teacherLogin);
    void deleteLesson(Integer lessonId);
    void updateLessonInfo(Integer lessonId, LocalDateTime newDateTime, String newDescription, String newDiscipline, Integer newGroupId, String newTeacherLogin);
}
