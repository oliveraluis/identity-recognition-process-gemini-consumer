package com.idm.identity_recognition_consumer.repository;


import com.idm.identity_recognition_consumer.document.UserDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<UserDocument, String> {
    Mono<UserDocument> findByDocumentNumber(String documentNumber);
}
