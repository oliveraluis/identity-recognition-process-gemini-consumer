package com.idm.identity_recognition_consumer.listener;

import com.idm.identity_recognition_consumer.dto.IdentityRecognition;
import com.idm.identity_recognition_consumer.service.RegisterIdentityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RegisterIdentityListener {

    private final RegisterIdentityService registerIdentityService;

    @Bean
    public Consumer<Message<IdentityRecognition>> identityConsumer() {
        return msg -> {
            IdentityRecognition recognition = msg.getPayload();
            log.info("Event received with id: {}", recognition.getEvent().getEventId());
            registerIdentityService.registerIdentity(recognition)
                    .doOnError(error -> log.error("Event with error: {}", error.getMessage()))
                    .subscribe();
        };
    }
}

