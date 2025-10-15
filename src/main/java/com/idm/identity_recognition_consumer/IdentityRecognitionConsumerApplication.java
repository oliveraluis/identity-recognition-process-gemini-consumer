package com.idm.identity_recognition_consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class IdentityRecognitionConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdentityRecognitionConsumerApplication.class, args);
	}

}
