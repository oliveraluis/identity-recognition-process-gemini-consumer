package com.idm.identity_recognition_consumer.router;

import com.idm.identity_recognition_consumer.handler.RegisterIdentityHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RegisterIdentityRouter {
    @Bean
    public RouterFunction<ServerResponse> identityRoutes(RegisterIdentityHandler handler) {
        return RouterFunctions.route()
                .POST("/identities", handler::handleIdentity)
                .build();
    }
}

