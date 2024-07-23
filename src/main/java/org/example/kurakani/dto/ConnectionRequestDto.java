package org.example.kurakani.dto;

public class ConnectionRequestDto {
    private String senderUsername;
    private String receiverUsername;
    private boolean accepted;

    public ConnectionRequestDto() {
    }

    public ConnectionRequestDto(String senderUsername, String receiverUsername, boolean accepted) {
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.accepted = accepted;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
