package com.idm.identity_recognition_consumer.service.impl;

import com.idm.identity_recognition_consumer.document.SessionDocument;
import com.idm.identity_recognition_consumer.document.UserDocument;
import com.idm.identity_recognition_consumer.dto.IdentityRecognition;
import com.idm.identity_recognition_consumer.repository.SessionRepository;
import com.idm.identity_recognition_consumer.repository.UserRepository;
import com.idm.identity_recognition_consumer.service.RegisterIdentityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterIdentityServiceImpl implements RegisterIdentityService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    @Override
    public Mono<Void> registerIdentity(IdentityRecognition recognition) {
        String eventId = recognition.getEvent().getEventId();
        String sessionId = recognition.getEvent().getSessionId();

        log.info("Starting identity verification for eventId={}, sessionId={}", eventId, sessionId);

        return findSession(sessionId)
                .flatMap(session ->
                        findUser(session.getUserId())
                                .flatMap(user -> verifyAndSaveUser(user, recognition)
                                        .then(updateSessionStatus(session))
                                        .doOnSuccess(v -> log.info("Identity verification completed for eventId={}, sessionId={}, userId={}, success={}",
                                                eventId, sessionId, user.getUserId(), recognition.getSuccess()))
                                )
                )
                .doOnError(e -> log.error("Identity verification failed for eventId={}, sessionId={}. Reason: {}", eventId, sessionId, e.getMessage()))
                .then();
    }

    private Mono<SessionDocument> findSession(String sessionId) {
        return sessionRepository.findById(sessionId)
                .switchIfEmpty(Mono.error(new RuntimeException("Session not found")));
    }

    private Mono<UserDocument> findUser(String userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }

    private Mono<Void> verifyAndSaveUser(UserDocument user, IdentityRecognition recognition) {
        user.verify(recognition);
        return userRepository.save(user)
                .doOnSuccess(saved -> log.debug("User document updated for userId={}", saved.getUserId()))
                .then();
    }

    private Mono<Void> updateSessionStatus(SessionDocument session) {
        session.verify();
        return sessionRepository.save(session)
                .doOnSuccess(saved -> log.debug("Session status updated for sessionId={}", saved.getSessionId()))
                .then();
    }
}


