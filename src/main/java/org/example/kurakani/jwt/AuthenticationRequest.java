package org.example.kurakani.jwt;

public class AuthenticationRequest {
    private String username;
    private String password;
    private String email;

    // Constructors
    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public AuthenticationRequest(String password, String email) {
        this.password = password;
        this.email = email;
    }

    // Getters and Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}