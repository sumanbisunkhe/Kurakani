package org.example.kurakani.repo;

import org.example.kurakani.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> findMessagesByFromUserAndToUserOrToUserAndFromUser(String fromUser, String toUser, String toUser2, String fromUser2);


    // Method to find messages by fromUser or toUser
    List<Message> findByFromUserOrToUser(String fromUser, String toUser);
}

