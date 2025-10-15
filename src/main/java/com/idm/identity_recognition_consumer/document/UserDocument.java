package com.idm.identity_recognition_consumer.document;

import com.idm.identity_recognition_consumer.dto.IdentityRecognition;
import com.idm.identity_recognition_consumer.type.UserStatusType;
import com.idm.identity_recognition_consumer.util.LocalDateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDocument {
    @Id
    private String userId;
    private String documentNumber;
    private String issueDate;
    private String fullName;
    private String address;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public void verify(IdentityRecognition identityRecognition) {
        this.status = UserStatus.from(UserStatusType.VERIFIED);
        this.documentNumber = identityRecognition.getDocumentNumber();
        this.issueDate = identityRecognition.getIssueDate();
        this.fullName = identityRecognition.getFullName();
        this.address = identityRecognition.getAddress();
        this.updateAt = LocalDateTimeUtil.getLocalDateTimeByZoneId(LocalDateTimeUtil.LIMA_ZONE);
    }

    @AllArgsConstructor
    @Getter
    public static class UserStatus {
        private String code;
        private String description;

        public static UserStatus from(UserStatusType statusType) {
            return new UserStatus(statusType.name(), statusType.getDescription());
        }
    }
}
