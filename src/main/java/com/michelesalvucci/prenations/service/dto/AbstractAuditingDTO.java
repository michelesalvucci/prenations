package com.michelesalvucci.prenations.service.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class AbstractAuditingDTO implements Serializable {
    private String createdBy;
    private String lastModifiedBy;
    private Instant createdDate;
    private Instant lastModifiedDate;
}
