package org.example.kurakani.controller;

import jakarta.validation.Valid;
import org.example.kurakani.dto.MessageDto;
import org.example.kurakani.model.Message;
import org.example.kurakani.model.OutputMessage;
import org.example.kurakani.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createMessage(
            @Valid @RequestBody MessageDto messageDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Validation error");
            response.put("errors", errors);
            return ResponseEntity.badRequest().body(response);
        }

        Message createdMessage = messageService.saveMessage(messageDto);

        Map<String, Object> response = Map.of(
                "message", "Message sent successfully.",
                "data", createdMessage
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage sendMessage(MessageDto messageDto) throws Exception {
        Message message = messageService.saveMessage(messageDto);
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFromUser(), message.getText(), time);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateMessage(
            @PathVariable Long id,
            @Valid @RequestBody MessageDto messageDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Validation error");
            response.put("errors", errors);
            return ResponseEntity.badRequest().body(response);
        }

        Message updatedMessage = messageService.editMessage(id, messageDto);

        Map<String, Object> response = Map.of(
                "message", "Message updated successfully.",
                "data", updatedMessage
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        Map<String, Object> response = Map.of(
                "message", "Messages fetched successfully.",
                "data", messages
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getMessageById(@PathVariable Long id) {
        Message message = messageService.getMessageById(id);
        Map<String, Object> response = Map.of(
                "message", "Message with ID " + id + " fetched successfully.",
                "data", message
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Map<String, Object>> getMessagesByUsername(@PathVariable String username) {
        List<Message> messages = messageService.getMessagesByUsername(username);
        Map<String, Object> response = Map.of(
                "message", "Messages for user " + username + " fetched successfully.",
                "data", messages
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/between")
    public ResponseEntity<Map<String, Object>> getMessagesBetweenUsers(
            @RequestParam String fromUser, @RequestParam String toUser) {
        List<Message> messages = messageService.getMessagesBetweenUsers(fromUser, toUser);
        Map<String, Object> response = Map.of(
                "message", "Messages between " + fromUser + " and " + toUser + " fetched successfully.",
                "data", messages
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteMessageById(@PathVariable Long id) {
        try {
            messageService.deleteMessageById(id);
            Map<String, String> response = Map.of("message", "Message with ID " + id + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = Map.of("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<Map<String, String>> deleteMessagesByUsername(@PathVariable String username) {
        try {
            messageService.deleteMessagesByUsername(username);
            Map<String, String> response = Map.of("message", "Messages for user " + username + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = Map.of("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Validation error");
        response.put("errors", errors);
        return ResponseEntity.badRequest().body(response);
    }
}
