package com.idm.identity_recognition_consumer.repository;

import com.idm.identity_recognition_consumer.document.SessionDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SessionRepository extends ReactiveMongoRepository<SessionDocument, String> {
}
