package com.michelesalvucci.prenations.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PNUserTenantId {

    public PNUserTenantId(String username, String tenantId) {
        this.username = username;
        this.tenantId = tenantId;
    }

    @Column(name = "username")
    private String username;

    @Column(name = "tenant_id")
    private String tenantId;
}
