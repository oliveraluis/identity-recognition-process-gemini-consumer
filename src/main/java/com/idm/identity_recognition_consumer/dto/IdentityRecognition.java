package com.idm.identity_recognition_consumer.dto;

import lombok.Data;

@Data
public class IdentityRecognition {
    private String documentNumber;
    private String issueDate;
    private String fullName;
    private String address;
    private Boolean success;
    private AuditEvent event;
}
