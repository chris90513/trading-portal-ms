package com.fxdidier.trading_portal.configuration.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    private static final String MDC_CORRELATION_ID = "correlationId";

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
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

        // 1️⃣ Obtener o generar correlationId
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);

        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString().substring(0, 8);
        }

        // 2️⃣ Guardar en MDC (para que salga en TODOS los logs)
        MDC.put(MDC_CORRELATION_ID, correlationId);

        // 3️⃣ Añadirlo a la respuesta
        response.setHeader(CORRELATION_ID_HEADER, correlationId);

        String method = request.getMethod();
        String uri = request.getRequestURI();

        // Usuario ANTES del JWT (suele ser anonymous)
        Authentication authBefore = SecurityContextHolder.getContext().getAuthentication();
        String usernameBefore = (authBefore != null && authBefore.isAuthenticated())
                ? authBefore.getName()
                : "anonymous";

        log.info("[{}] --> {} {} (user={})",
                correlationId, method, uri, usernameBefore);

        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - start;
            int status = response.getStatus();

            // Usuario DESPUÉS del JWT (ya autenticado)
            Authentication authAfter = SecurityContextHolder.getContext().getAuthentication();
            String usernameAfter = (authAfter != null && authAfter.isAuthenticated())
                    ? authAfter.getName()
                    : usernameBefore;

            log.info("[{}] <-- {} {} -> {} ({} ms) (user={})",
                    correlationId, method, uri, status, duration, usernameAfter);

            // Importante: limpiar MDC al final
            MDC.clear();
        }
    }
}
