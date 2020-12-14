package domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
public class Lesson {

    private Integer lessonId;
    private LocalDateTime dateTime;
    private String description;
    private String discipline;
    private String homework;
    private Integer groupId;
    private String teacherLogin;
    private final Set<String> presents;

    public boolean isLessonTeacher(User user) {
        return user.isTeacher() && teacherLogin.equals(user.getLogin());
    }

    public boolean removePresent(String studentLogin) {
        return presents.remove(studentLogin);
    }

    public void addPresent(String studentLogin) {
        presents.add(studentLogin);
    }

    public boolean isPresent(String studentLogin) {
        return presents.contains(studentLogin);
    }

    public Set<String> getPresentStudentLogins() {
        return presents;
    }
}
