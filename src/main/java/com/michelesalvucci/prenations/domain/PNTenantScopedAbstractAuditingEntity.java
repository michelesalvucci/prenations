package com.michelesalvucci.prenations.domain;

import java.util.Objects;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import com.michelesalvucci.prenations.security.TenantContext;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.EqualsAndHashCode;

@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "tenantId", type = String.class))
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class PNTenantScopedAbstractAuditingEntity<T> extends AbstractAuditingEntity<T> {

    @Column(name = "tenant_id")
    private String tenantId;

    @PrePersist
    public void prePersist() {
        if (Objects.isNull(this.getTenantId())) {
            this.setTenantId(TenantContext.getCurrentTenant());
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (Objects.isNull(this.getTenantId())) {
            this.setTenantId(TenantContext.getCurrentTenant());
        }
    }
}
