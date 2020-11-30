package domain;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public class Lesson {

    private Integer lessonId;
    private LocalDateTime dateTime;
    private String description;
    private String discipline;
    private String homework;
    private Integer groupId;
    private String teacherLogin;
    private final Map<String, Boolean> isPresent;

    public Lesson(Integer lessonId, LocalDateTime dateTime, String description, String discipline, String homework, Integer groupId, String teacherLogin, Map<String, Boolean> isPresent) {
        this.lessonId = lessonId;
        this.dateTime = dateTime;
        this.description = description;
        this.discipline = discipline;
        this.homework = homework;
        this.groupId = groupId;
        this.teacherLogin = teacherLogin;
        this.isPresent = isPresent;
    }

    public boolean removePresent(String studentLogin) {
        return isPresent.remove(studentLogin);
    }

    public void addPresent(String studentLogin) {
        isPresent.put(studentLogin, true);
    }

    public boolean isPresent(String studentLogin) {
        return isPresent.getOrDefault(studentLogin, false);
    }

    public Set<String> getPresentStudentLogins() {
        return isPresent.keySet();
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getTeacherLogin() {
        return teacherLogin;
    }

    public void setTeacherLogin(String teacherLogin) {
        this.teacherLogin = teacherLogin;
    }
}
