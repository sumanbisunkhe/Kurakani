package org.example.kurakani.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.kurakani.enums.Gender;
import org.example.kurakani.enums.Role;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String name;
    private String username;
    private String email;
    private String password;

    private Gender gender;
    private Integer age;
    private String Country;
    private Role role;
    private boolean enabled;
    private String otpCode;
    @JsonFormat(pattern = "EEEE MMMM dd, yyyy HH:mm")
    private LocalDateTime otpExpiryTime;
    @JsonFormat(pattern = "EEEE MMMM dd, yyyy HH:mm")
    private LocalDateTime dateCreated;



    public UserDto() {
    }

    public UserDto(String name, String username, String email, String password, Gender gender, Integer age, String country, Role role, boolean enabled, String otpCode, LocalDateTime otpExpiryTime,LocalDateTime dateCreated) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.age = age;
        Country = country;
        this.role = role;
        this.enabled = enabled;
        this.otpCode = otpCode;
        this.otpExpiryTime = otpExpiryTime;
        this.dateCreated=dateCreated;

    }

    public UserDto(String name, String username, String email, Gender gender, Integer age, String country, Role role, boolean enabled, String otpCode, LocalDateTime otpExpiryTime,LocalDateTime dateCreated) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.age = age;
        Country = country;
        this.role = role;
        this.enabled = enabled;
        this.otpCode = otpCode;
        this.otpExpiryTime = otpExpiryTime;
        this.dateCreated=dateCreated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public LocalDateTime getOtpExpiryTime() {
        return otpExpiryTime;
    }

    public void setOtpExpiryTime(LocalDateTime otpExpiryTime) {
        this.otpExpiryTime = otpExpiryTime;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
