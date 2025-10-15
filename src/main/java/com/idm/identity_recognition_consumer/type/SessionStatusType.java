package com.idm.identity_recognition_consumer.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SessionStatusType {
    ACTIVE("Activo"),
    INACTIVE("Inactivo"),
    USED("Usado");
    private final String description;
}
