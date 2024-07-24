package org.example.kurakani.service.impl;

import org.example.kurakani.dto.MessageDto;
import org.example.kurakani.model.Message;
import org.example.kurakani.repo.MessageRepo;

import org.example.kurakani.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepo messageRepo;

    @Override
    public Message saveMessage(MessageDto messageDto) {
        Message message = new Message();
        message.setFromUser(messageDto.getFromUser());
        message.setToUser(messageDto.getToUser());
        message.setText(messageDto.getText());
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        message.setTimestamp(time);
        return messageRepo.save(message);
    }

    @Override
    public Message editMessage(Long id, MessageDto messageDto) {
        Optional<Message> optionalMessage = messageRepo.findById(id);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setText(messageDto.getText());
            return messageRepo.save(message);
        } else {
            throw new RuntimeException("Message not found");
        }
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }

    @Override
    public Message getMessageById(Long id) {
        return messageRepo.findById(id).orElseThrow(() -> new RuntimeException("Message not found"));
    }

    @Override
    public List<Message> getMessagesByUsername(String username) {
        return messageRepo.findByFromUserOrToUser(username, username);
    }


    @Override
    public void deleteMessageById(Long id) {
        if (!messageRepo.existsById(id)) {
            throw new RuntimeException("Message with ID " + id + " not found");
        }
        messageRepo.deleteById(id);
    }

    @Override
    public void deleteMessagesByUsername(String username) {
        List<Message> messages = messageRepo.findByFromUserOrToUser(username, username);
        if (messages.isEmpty()) {
            throw new RuntimeException("No messages found for user " + username);
        }
        messageRepo.deleteAll(messages);
    }
    @Override
    public List<Message> getMessagesBetweenUsers(String fromUser, String toUser) {
        return messageRepo.findMessagesByFromUserAndToUserOrToUserAndFromUser(fromUser, toUser, toUser, fromUser);
    }
}
