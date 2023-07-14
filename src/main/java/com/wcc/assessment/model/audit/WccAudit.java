package com.wcc.assessment.model.audit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Document("wccAudit")
@Getter
@Setter
public class WccAudit extends AuditEvent {

    private String userId;

    public WccAudit(String principal, String type, Map<String, Object> data) {
        super(principal, type, data);
    }

    public WccAudit(String principal, String type, String... data) {
        super(principal, type, data);
    }

    public WccAudit(Instant timestamp, String principal, String type, Map<String, Object> data) {
        super(timestamp, principal, type, data);
    }

    public WccAudit(String principal, String type, Map<String, Object> data, String userId) {
        super(principal, type, data);
        this.userId = userId;
    }

    public WccAudit(String principal, String type, String userId, String... data) {
        super(principal, type, data);
        this.userId = userId;
    }

    public WccAudit(Instant timestamp, String principal, String type, Map<String, Object> data, String userId) {
        super(timestamp, principal, type, data);
        this.userId = userId;
    }
}
