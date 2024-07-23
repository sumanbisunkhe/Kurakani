package org.example.kurakani.service;

import org.example.kurakani.dto.ConnectionRequestDto;

public interface ConnectionRequestService {
    ConnectionRequestDto sendRequest(ConnectionRequestDto requestDto);
    ConnectionRequestDto acceptRequest(Long requestId);
}
