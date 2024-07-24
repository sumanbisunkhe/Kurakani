package org.example.kurakani.model;

public class OutputMessage {

    private String fromUser;

    private String text;
    private String time;

    public OutputMessage() {
    }

    public OutputMessage(String fromUser, String text, String time) {
        this.fromUser = fromUser;
        this.text = text;
        this.time = time;
    }

    // Getters and Setters
    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}