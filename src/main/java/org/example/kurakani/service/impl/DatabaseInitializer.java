package org.example.kurakani.service.impl;


import jakarta.annotation.PostConstruct;
import org.example.kurakani.enums.Gender;
import org.example.kurakani.enums.Role;
import org.example.kurakani.model.User;
import org.example.kurakani.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DatabaseInitializer {

    @Autowired
    private UserRepo userRepository;

    @PostConstruct
    public void init() {
        if (!userRepository.existsByEmail("sumanbisunkhe304@gmail.com")) {
            User user = new User();
            user.setName("Suman Bisunkhe");
            user.setUsername("Suman");
            user.setEmail("sumanbisunkhe304@gmail.com");
            user.setPassword("$2a$12$fIWpiW3lvJzUx5xmdQY4mukZsgZFDfsQNeaFF7XaSsOhx5axBdZja");
            user.setGender(Gender.MALE);
            user.setAge(21);
            user.setRole(Role.ADMIN);
            user.setCountry("Nepal");
            user.setEnabled(true);
            user.setOtpCode(null);
            user.setOtpExpiryTime(null);
            user.setDateCreated(LocalDateTime.now());
            userRepository.save(user);
        }
    }
}