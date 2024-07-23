package org.example.kurakani.util;

import org.example.kurakani.exceptions.UserNotFoundException;
import org.example.kurakani.model.User;
import org.example.kurakani.repo.UserRepo;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.security.SecureRandom;
import java.time.LocalDateTime;

public class Otp {

    private final UserRepo userRepo;
    private final JavaMailSender javaMailSender;



    public Otp(UserRepo userRepo, JavaMailSender javaMailSender) {
        this.userRepo = userRepo;
        this.javaMailSender = javaMailSender;
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




    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = random.nextInt(999999); // Generates a random number between 0 and 999999
        return String.format("%06d", otp); // Pads the number with leading zeros if necessary
    }

    private void sendOtpByEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("BlogEcho - OTP Verification");
        message.setText("Your OTP for verification is: " + otp);
        javaMailSender.send(message);
    }
}
