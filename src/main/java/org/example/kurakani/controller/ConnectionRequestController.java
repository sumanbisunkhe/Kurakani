package org.example.kurakani.controller;

import org.example.kurakani.dto.ConnectionRequestDto;
import org.example.kurakani.service.ConnectionRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/connection-requests")
public class ConnectionRequestController {

    @Autowired
    private ConnectionRequestService connectionRequestService;

    @PostMapping("/send")
    public ResponseEntity<ConnectionRequestDto> sendRequest(@RequestBody ConnectionRequestDto requestDto) {
        try {
            ConnectionRequestDto request = connectionRequestService.sendRequest(requestDto);
            return ResponseEntity.ok(request);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/accept/{requestId}")
    public ResponseEntity<ConnectionRequestDto> acceptRequest(@PathVariable Long requestId) {
        try {
            ConnectionRequestDto request = connectionRequestService.acceptRequest(requestId);
            return ResponseEntity.ok(request);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
