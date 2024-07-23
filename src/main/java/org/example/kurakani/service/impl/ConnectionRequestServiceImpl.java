package org.example.kurakani.service.impl;

import org.example.kurakani.dto.ConnectionRequestDto;
import org.example.kurakani.model.ConnectionRequest;
import org.example.kurakani.model.User;
import org.example.kurakani.repo.ConnectionRequestRepo;
import org.example.kurakani.repo.UserRepo;
import org.example.kurakani.service.ConnectionRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConnectionRequestServiceImpl implements ConnectionRequestService {

    @Autowired
    private ConnectionRequestRepo requestRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public ConnectionRequestDto sendRequest(ConnectionRequestDto requestDto) {
        Optional<User> senderOpt = userRepo.findByUsername(requestDto.getSenderUsername());
        Optional<User> receiverOpt = userRepo.findByUsername(requestDto.getReceiverUsername());

        if (!senderOpt.isPresent() || !receiverOpt.isPresent()) {
            throw new IllegalArgumentException("Sender or receiver not found");
        }

        User sender = senderOpt.get();
        User receiver = receiverOpt.get();

        ConnectionRequest request = new ConnectionRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setAccepted(false);

        ConnectionRequest savedRequest = requestRepo.save(request);
        return convertToDto(savedRequest);
    }

    @Override
    public ConnectionRequestDto acceptRequest(Long requestId) {
        ConnectionRequest request = requestRepo.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Connection request not found"));

        request.setAccepted(true);
        ConnectionRequest updatedRequest = requestRepo.save(request);

        User sender = updatedRequest.getSender();
        User receiver = updatedRequest.getReceiver();

        sender.getConnections().add(receiver);
        receiver.getConnections().add(sender);

        userRepo.save(sender);
        userRepo.save(receiver);

        return convertToDto(updatedRequest);
    }

    private ConnectionRequestDto convertToDto(ConnectionRequest request) {
        return new ConnectionRequestDto(
                request.getId(),
                request.getSender().getUsername(),
                request.getReceiver().getUsername(),
                request.isAccepted()
        );
    }
}
