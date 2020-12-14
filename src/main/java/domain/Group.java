package domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Group {

    private Integer id;
    private String name;
    private String groupHeadLogin;

    public boolean isGrouphead(User user) {
        return user.isGrouphead() && user.getLogin().equals(groupHeadLogin);
    }
}
