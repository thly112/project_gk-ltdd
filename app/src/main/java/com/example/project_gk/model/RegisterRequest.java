package com.example.project_gk.model;

public class RegisterRequest {
    private String email;
    private String uname;
    private String name;
    private String password;
    private int type;
    private boolean gender;

    public RegisterRequest(String uname, String email, String password, boolean gender) {
        this.email = email;
        this.uname = uname;
        this.password = password;
        this.type = 1;
        this.gender = gender;
    }

    // Getter và Setter
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getType() {
        return type;
    }

    public boolean isGender() {
        return gender;
    }
}
