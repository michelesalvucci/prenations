package com.michelesalvucci.prenations.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import com.michelesalvucci.prenations.security.TenantContext;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class PNTenantAspect {

    @PersistenceContext
    private EntityManager entityManager;

    @Pointcut("execution(* com.michelesalvucci.prenations.repository.*.*(..))")
    public void pointcutRepository() {}

    @Around("pointcutRepository()")
    public Object beforeRepository(ProceedingJoinPoint joinPoint) throws Throwable {
        if (Objects.nonNull(entityManager)) {
            Session session = null;
            try {
                session = entityManager.unwrap(Session.class);
                Filter filter = session.enableFilter("tenantFilter");
                filter.setParameter("tenantId", TenantContext.getCurrentTenant());
                filter.validate();
            } catch (Throwable e) {
                log.debug("Error in setting the tenant", e);
                if (session != null) {
                    session.disableFilter("tenantFilter");
                }
            }
        }

        return joinPoint.proceed();
    }
}