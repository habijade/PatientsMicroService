package com.nnk.springboot.dto;

import com.nnk.springboot.validator.ValidPassword;
import jakarta.validation.constraints.NotBlank;

public class UserDto {

    private String id;

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "fullName is required")
    private String fullName;

    @ValidPassword
    private String password;

    @NotBlank(message = "role is required")
    private String role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
