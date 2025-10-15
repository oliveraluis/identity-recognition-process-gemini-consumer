package com.idm.identity_recognition_consumer.handler;

import com.idm.identity_recognition_consumer.dto.AuditEvent;
import com.idm.identity_recognition_consumer.dto.IdentityRecognition;
import com.idm.identity_recognition_consumer.service.RegisterIdentityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterIdentityHandlerTest {

    @Mock
    private RegisterIdentityService registerIdentityService;

    private WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        RegisterIdentityHandler handler = new RegisterIdentityHandler(registerIdentityService);

        RouterFunction<ServerResponse> route = RouterFunctions.route()
                .POST("/identities", handler::handleIdentity)
                .build();

        webTestClient = WebTestClient.bindToRouterFunction(route).build();
    }

    @Test
    void shouldHandleIdentityRequestSuccessfully() {
        IdentityRecognition recognition = new IdentityRecognition();
        AuditEvent event = new AuditEvent();
        event.setEventId("event-123");
        event.setSessionId("session-456");
        recognition.setEvent(event);
        recognition.setSuccess(true);

        when(registerIdentityService.registerIdentity(any())).thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/identities")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(recognition)
                .exchange()
                .expectStatus().isOk();

        verify(registerIdentityService).registerIdentity(any());
    }
}
