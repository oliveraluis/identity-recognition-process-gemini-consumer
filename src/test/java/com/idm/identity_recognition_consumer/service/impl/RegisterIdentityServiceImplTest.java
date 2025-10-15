package com.idm.identity_recognition_consumer.service.impl;

import com.idm.identity_recognition_consumer.document.SessionDocument;
import com.idm.identity_recognition_consumer.document.UserDocument;
import com.idm.identity_recognition_consumer.dto.AuditEvent;
import com.idm.identity_recognition_consumer.dto.IdentityRecognition;
import com.idm.identity_recognition_consumer.repository.SessionRepository;
import com.idm.identity_recognition_consumer.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterIdentityServiceImplTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegisterIdentityServiceImpl registerIdentityService;

    @Test
    void shouldRegisterIdentitySuccessfully() {
        // Arrange
        String sessionId = "session-123";
        String userId = "user-456";
        String eventId = "event-789";

        IdentityRecognition recognition = mock(IdentityRecognition.class);
        AuditEvent event = mock(AuditEvent.class);

        when(recognition.getEvent()).thenReturn(event);
        when(event.getSessionId()).thenReturn(sessionId);
        when(event.getEventId()).thenReturn(eventId);
        when(recognition.getSuccess()).thenReturn(true);

        SessionDocument sessionDocument = new SessionDocument();
        sessionDocument.setSessionId(sessionId);
        sessionDocument.setUserId(userId);

        UserDocument userDocument = mock(UserDocument.class);
        when(userDocument.getUserId()).thenReturn(userId);

        when(sessionRepository.findById(sessionId)).thenReturn(Mono.just(sessionDocument));
        when(userRepository.findById(userId)).thenReturn(Mono.just(userDocument));
        when(userRepository.save(userDocument)).thenReturn(Mono.just(userDocument));
        when(sessionRepository.save(sessionDocument)).thenReturn(Mono.just(sessionDocument));

        Mono<Void> result = registerIdentityService.registerIdentity(recognition);

        StepVerifier.create(result)
                .verifyComplete();

        verify(sessionRepository).findById(sessionId);
        verify(userRepository).findById(userId);
        verify(userDocument).verify(recognition);
        verify(userRepository).save(userDocument);
        verify(sessionRepository).save(sessionDocument);
    }
}
