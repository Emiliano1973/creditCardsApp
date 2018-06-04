package ie.myinfinity.demo.creditcard.dtos;

import java.io.Serializable;

// dto for  external interation for User
public class UserDto implements Serializable {

    private String username;
    private String password;

    private String role;

    public UserDto() {
        super();
    }

    public UserDto(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    public UserDto(String username, String password, String role) {
        this(username, password);
        this.role=role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
