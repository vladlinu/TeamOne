package domain;

public class User {

    private String login;
    private String password;
    private UserType userType;
    private Integer groupId;

    public User(String login, String password, UserType userType, Integer groupId) {
        this.login = login;
        this.password = password;
        this.userType = userType;
        this.groupId = groupId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
