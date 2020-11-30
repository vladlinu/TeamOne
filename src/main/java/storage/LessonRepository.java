package storage;

import domain.Lesson;

import java.time.LocalDate;
import java.util.List;

public interface LessonRepository {

    List<Lesson> getLessonsByGroupIdFromInterval(String groupId, LocalDate from, LocalDate to);
    List<Lesson> getLessonsByTeacherIdFromInterval(String teacherLogin, LocalDate from, LocalDate to);

}
