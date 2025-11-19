package com.fxdidier.trading_portal.configuration.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();

        // No spamear logs con swagger, actuator, etc.
        return uri.startsWith("/v3/api-docs")
                || uri.startsWith("/swagger-ui")
                || uri.startsWith("/actuator");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String requestId = UUID.randomUUID().toString().substring(0, 8);

        // Usuario autenticado (si lo hay)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth != null && auth.isAuthenticated())
                ? auth.getName()
                : "anonymous";

        // Log de entrada
        log.info("[{}] --> {} {} (user={})", requestId, method, uri, username);

        filterChain.doFilter(request, response);

        long duration = System.currentTimeMillis() - start;
        int status = response.getStatus();

        // Log de salida
        log.info("[{}] <-- {} {} -> {} ({} ms)", requestId, method, uri, status, duration);
    }
}