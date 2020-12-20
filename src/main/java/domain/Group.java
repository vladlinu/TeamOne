package domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Group {

    private Integer id;
    private String name;
    private User groupHead;
    private List<User> members;

    public boolean isGrouphead(User user) {
        return user.isGrouphead() && user.equals(groupHead);
    }
}
