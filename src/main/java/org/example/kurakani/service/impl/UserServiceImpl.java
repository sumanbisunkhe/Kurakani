package org.example.kurakani.service.impl;

import org.example.kurakani.dto.UserDto;
import org.example.kurakani.enums.Role;
import org.example.kurakani.exceptions.UserNotFoundException;
import org.example.kurakani.model.User;
import org.example.kurakani.repo.UserRepo;
import org.example.kurakani.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    private final JavaMailSender javaMailSender;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, JavaMailSender javaMailSender) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public UserDto register(UserDto userDto) {

        // Check if a user with the same email exists and is not enabled
        User existingUserByEmail = userRepo.findByEmail(userDto.getEmail()).orElse(null);
        User existingUserByUsername = userRepo.findByUsername(userDto.getUsername()).orElse(null);

        if (existingUserByEmail != null && !existingUserByEmail.isEnabled()) {
            // Update existing user with new details
            existingUserByEmail.setName(userDto.getName());
            existingUserByEmail.setUsername(userDto.getUsername());
            existingUserByEmail.setGender(userDto.getGender());
            existingUserByEmail.setAge(userDto.getAge());
            existingUserByEmail.setCountry(userDto.getCountry());
            existingUserByEmail.setPassword(passwordEncoder.encode(userDto.getPassword()));
            existingUserByEmail.setOtpCode(generateOtp());
            existingUserByEmail.setOtpExpiryTime(LocalDateTime.now().plusMinutes(5));

            User updatedUser = userRepo.save(existingUserByEmail);
            sendOtpByEmail(updatedUser.getEmail(), updatedUser.getOtpCode());
            return ConvertToDto(updatedUser);
        } else if (existingUserByUsername != null && !existingUserByUsername.isEnabled()) {
            // Update existing user with new details
            existingUserByUsername.setName(userDto.getName());
            existingUserByUsername.setEmail(userDto.getEmail());
            existingUserByUsername.setGender(userDto.getGender());
            existingUserByUsername.setAge(userDto.getAge());
            existingUserByUsername.setCountry(userDto.getCountry());
            existingUserByUsername.setPassword(passwordEncoder.encode(userDto.getPassword()));
            existingUserByUsername.setOtpCode(generateOtp());
            existingUserByUsername.setOtpExpiryTime(LocalDateTime.now().plusMinutes(5));

            User updatedUser = userRepo.save(existingUserByUsername);
            sendOtpByEmail(updatedUser.getEmail(), updatedUser.getOtpCode());
            return ConvertToDto(updatedUser);
        } else {
            // Create a new user if no existing user with the same email or username is found
            User user = new User();
            user.setName(userDto.getName());
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            String rawPassword = userDto.getPassword();
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setGender(userDto.getGender());
            user.setAge(userDto.getAge());
            user.setCountry(userDto.getCountry());
            user.setRole(Role.USER);
            user.setEnabled(false);


            String otp = generateOtp();
            user.setOtpCode(otp);
            user.setOtpExpiryTime(LocalDateTime.now().plusMinutes(5));
            user.setDateCreated(LocalDateTime.now());
            User savedUser = userRepo.save(user);
            sendOtpByEmail(user.getEmail(), user.getOtpCode());

            return ConvertToDto(savedUser);
        }
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        // Fetch the existing user by ID
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        // Generate a new OTP
        String otp = generateOtp();

        // Update existing user details
        existingUser.setName(userDto.getName());
        existingUser.setUsername(userDto.getUsername());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setGender(userDto.getGender());
        existingUser.setAge(userDto.getAge());
        existingUser.setCountry(userDto.getCountry());
        existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Set OTP details
        existingUser.setOtpCode(otp);
        existingUser.setOtpExpiryTime(LocalDateTime.now().plusMinutes(5));
        existingUser.setEnabled(false);

        // Save user with OTP and disabled status
        User updatedUser = userRepo.save(existingUser);

        // Send OTP to the existing email address
        sendOtpByEmail(existingUser.getEmail(), otp);

        return ConvertToDto(updatedUser);
    }



    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = userRepo.findAll();
        return userList.stream().map(this::ConvertToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        User user= userRepo.findById(id)
                .orElseThrow(()->new UserNotFoundException(("User not found with ID: "+id)));
        return ConvertToDto(user);
    }

    @Override
    public UserDto getUserByName(String username) {
        User user = userRepo.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return ConvertToDto(user);
    }


    @Override
    public void deleteUserById(Long id) {
        if (!userRepo.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userRepo.deleteById(id);
    }

    @Override
    public void deleteUserByUsername(String username) {
        User user = userRepo.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        userRepo.delete(user);
    }



    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException("User not found with username: "+username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    @Override
    public List<String> getAllEmails() {
        List<User> users = userRepo.findAll();
        return users.stream().map(User::getEmail).collect(Collectors.toList());
    }

    private UserDto ConvertToDto(User user){
        if(user==null){
            return null;
        }
        return new UserDto(
        user.getName(),
        user.getUsername(),
        user.getEmail(),
        user.getGender(),
        user.getAge(),
        user.getCountry(),
        user.getRole(),
        user.isEnabled(),
        user.getOtpCode(),
        user.getOtpExpiryTime(),
        user.getDateCreated()
        );

    }

    //Generate Otp
    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = random.nextInt(999999); // Generates a random number between 0 and 999999
        return String.format("%06d", otp); // Pads the number with leading zeros if necessary
    }
    public void generateOtpAndSendEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        String otp = generateOtp();
        user.setOtpCode(otp);
        user.setOtpExpiryTime(LocalDateTime.now().plusMinutes(5)); // OTP expiry in 5 minutes
        userRepo.save(user);

        sendOtpByEmail(email, otp);
    }


    public boolean verifyOtp(String email, String otp) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        // Check if OTP is expired or not set
        if (user.getOtpCode() == null || user.getOtpExpiryTime().isBefore(LocalDateTime.now())) {
            return false;
        }

        // Check if OTP matches
        boolean isValid = user.getOtpCode().equals(otp);
        if (isValid) {
            // Clear OTP fields after successful verification
            user.setOtpCode(null);
            user.setOtpExpiryTime(null);
            user.setEnabled(true);
            userRepo.save(user);
        }
        return isValid;
    }
    private void sendOtpByEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("BlogEcho - OTP Verification");
        message.setText("Your OTP for verification is: " + otp);
        javaMailSender.send(message);
    }
}
