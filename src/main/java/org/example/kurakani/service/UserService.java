package org.example.kurakani.service;

import org.example.kurakani.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    UserDto register(UserDto userDto);
    UserDto update(Long id,UserDto userDto);
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto getUserByName(String name);
    void deleteUserById(Long id);
    void deleteUserByUsername(String username);

    UserDetails loadUserByUsername(String username);

    List<String> getAllEmails();

    boolean verifyOtp(String email, String otpCode);

    void generateOtpAndSendEmail(String email);

}
