package org.example.kurakani.dto;

import jakarta.validation.constraints.NotEmpty;

public class MessageDto {

    @NotEmpty(message = "Sender username cannot be empty.")
    private String fromUser;

    @NotEmpty(message = "Receiver username cannot be empty.")
    private String toUser;

    @NotEmpty(message = "Message text cannot be empty.")
    private String text;


    public MessageDto() {
    }

    public MessageDto(String fromUser, String toUser, String text) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.text = text;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
