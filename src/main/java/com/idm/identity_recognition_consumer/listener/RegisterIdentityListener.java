package com.idm.identity_recognition_consumer.listener;

import com.idm.identity_recognition_consumer.dto.IdentityRecognition;
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

    @Bean
    public Consumer<Message<IdentityRecognition>> identityConsumer(){
        return (Message<IdentityRecognition> msg) -> {
            log.info("Event id: {}", msg.getPayload().getEvent().getEventId());
        };
    }

}
