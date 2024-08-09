package com.michelesalvucci.prenations.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.michelesalvucci.prenations.config.PNConstants;
import com.michelesalvucci.prenations.domain.PNUserTenantId;
import com.michelesalvucci.prenations.repository.PNUserTenantRepository;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Component
@Order(1)
@Slf4j
class TenantFilter implements Filter {

    @Autowired
    private PNUserTenantRepository userTenantRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String tenantName = req.getHeader("X-TenantID");
        String user = SecurityUtils.getCurrentUserLogin().orElse(PNConstants.SYSTEM);
        if (userTenantRepository.existsById(new PNUserTenantId(user, tenantName))) {
            TenantContext.setCurrentTenant(tenantName);
        } else {
            log.warn("VIOLATION: user {} tried to access tenant {}", user, tenantName);
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            chain.doFilter(request, response);
        } finally {
            TenantContext.setCurrentTenant("");
        }
    }
}