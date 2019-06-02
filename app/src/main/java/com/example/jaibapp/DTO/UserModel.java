package com.example.jaibapp.DTO;

public class UserModel {
    private String email;
    private String fullName;
    private String gender;

    public UserModel() {
        this.email = null;
        this.fullName = null;
        this.gender = null;
    }
    public UserModel(String email, String fullName, String gender) {
        this.email = email;
        this.fullName = fullName;
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
