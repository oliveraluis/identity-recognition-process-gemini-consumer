package com.idm.identity_recognition_consumer.service;

import com.idm.identity_recognition_consumer.dto.IdentityRecognition;
import reactor.core.publisher.Mono;

public interface RegisterIdentityService {
    Mono<Void> registerIdentity(IdentityRecognition recognition);
}
