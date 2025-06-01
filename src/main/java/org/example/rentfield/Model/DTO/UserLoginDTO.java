package org.example.rentfield.Model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.rentfield.Model.Enums.Role;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLoginDTO {
    private String email;
    private String password;
    private Role role;

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private int userID;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public int getUserID() {
        return userID;
    }
}
