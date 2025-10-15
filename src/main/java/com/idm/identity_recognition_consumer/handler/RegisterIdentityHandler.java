package com.idm.identity_recognition_consumer.handler;

import com.idm.identity_recognition_consumer.dto.IdentityRecognition;
import com.idm.identity_recognition_consumer.service.RegisterIdentityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegisterIdentityHandler {

    private final RegisterIdentityService registerIdentityService;

    public Mono<ServerResponse> handleIdentity(ServerRequest request) {
        return request.bodyToMono(IdentityRecognition.class)
                .flatMap(recognition -> {
                    log.info("HTTP request received for identity verification. eventId={}, sessionId={}",
                            recognition.getEvent().getEventId(),
                            recognition.getEvent().getSessionId());

                    return registerIdentityService.registerIdentity(recognition)
                            .doOnError(error -> log.error("Error during identity verification. eventId={}, reason={}",
                                    recognition.getEvent().getEventId(),
                                    error.getMessage()))
                            .then(ServerResponse.ok().build());
                });
    }
}

