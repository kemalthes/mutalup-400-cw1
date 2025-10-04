package entity;

public class User {

    private Integer id;
    private String name;
    private String lastName;
    private String login;
    private String password;

    public User() {
    }

    public User(Integer id, String name, String lastName, String login, String password) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
