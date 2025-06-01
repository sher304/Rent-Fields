package org.example.rentfield.Model.DTO;

public class UserResponseDTO {
    private String token;

    public UserResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
