package com.idm.identity_recognition_consumer.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuditEvent {
    private String eventId;
    private String sessionId;
    private LocalDateTime createdAt;
}
