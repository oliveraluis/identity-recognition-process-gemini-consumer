package com.idm.identity_recognition_consumer.listener;

import com.idm.identity_recognition_consumer.dto.AuditEvent;
import com.idm.identity_recognition_consumer.dto.IdentityRecognition;
import com.idm.identity_recognition_consumer.service.RegisterIdentityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterIdentityListenerTest {

    @Mock
    private RegisterIdentityService registerIdentityService;

    @InjectMocks
    private RegisterIdentityListener listener;

    @Test
    void shouldConsumeIdentityRecognitionEvent() {
        IdentityRecognition recognition = mock(IdentityRecognition.class);
        AuditEvent event = mock(AuditEvent.class);
        when(recognition.getEvent()).thenReturn(event);
        when(event.getEventId()).thenReturn("event-123");

        Message<IdentityRecognition> message = MessageBuilder.withPayload(recognition).build();

        when(registerIdentityService.registerIdentity(recognition)).thenReturn(Mono.empty());

        Consumer<Message<IdentityRecognition>> consumer = listener.identityConsumer();

        consumer.accept(message);

        verify(registerIdentityService).registerIdentity(recognition);
        verify(event).getEventId();
    }
}
