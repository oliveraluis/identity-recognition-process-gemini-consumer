package com.idm.identity_recognition_consumer.document;

import com.idm.identity_recognition_consumer.type.SessionStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "sessions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SessionDocument {
    @Id
    private String sessionId;
    private String userId;
    private SessionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    public void verify(){
        this.status = SessionStatus.from(SessionStatusType.USED);
    }


    @AllArgsConstructor
    @Getter
    public static class SessionStatus{
        private String code;
        private String description;

        public static SessionStatus from(SessionStatusType statusType){
           return new SessionStatus(statusType.name(), statusType.getDescription());
        }
    }
}
