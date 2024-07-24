package org.example.kurakani.service;

import org.example.kurakani.dto.MessageDto;
import org.example.kurakani.model.Message;

import java.util.List;

public interface MessageService {
    Message saveMessage(MessageDto messageDto);

    Message editMessage(Long id, MessageDto messageDto);

    List<Message> getAllMessages();

    Message getMessageById(Long id);

    List<Message> getMessagesByUsername(String username);

    void deleteMessageById(Long id);

    void deleteMessagesByUsername(String username);

    List<Message> getMessagesBetweenUsers(String fromUser, String toUser);
}
