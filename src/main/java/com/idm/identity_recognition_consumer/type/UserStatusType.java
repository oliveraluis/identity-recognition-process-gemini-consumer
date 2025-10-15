package com.idm.identity_recognition_consumer.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserStatusType {
    PENDING("Pendiente"),
    VERIFIED("Verificado"),
    BLOCKED("Bloqueado");
    private final String description;
}
