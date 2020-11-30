package domain;

public class Group {

    private Integer id;
    private String name;
    private String groupHeadLogin;

    public Group(Integer id, String name, String groupHeadLogin) {
        this.id = id;
        this.name = name;
        this.groupHeadLogin = groupHeadLogin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupHeadLogin() {
        return groupHeadLogin;
    }

    public void setGroupHeadLogin(String groupHeadLogin) {
        this.groupHeadLogin = groupHeadLogin;
    }
}
