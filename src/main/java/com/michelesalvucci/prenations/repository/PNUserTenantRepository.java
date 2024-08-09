package com.michelesalvucci.prenations.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.michelesalvucci.prenations.domain.PNUserTenant;
import com.michelesalvucci.prenations.domain.PNUserTenantId;

@SuppressWarnings("unused")
@Repository
public interface PNUserTenantRepository extends JpaRepository<PNUserTenant, PNUserTenantId> {
    
}
