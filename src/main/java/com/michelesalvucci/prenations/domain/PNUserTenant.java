package com.michelesalvucci.prenations.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "pren_users_tenants")
@Data
public class PNUserTenant {
    @EmbeddedId
    private PNUserTenantId id;
}
