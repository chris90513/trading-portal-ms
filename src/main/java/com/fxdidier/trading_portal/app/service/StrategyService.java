package com.fxdidier.trading_portal.app.service;

import com.fxdidier.trading_portal.app.domain.entity.Strategy;
import com.fxdidier.trading_portal.app.domain.entity.User;
import com.fxdidier.trading_portal.app.domain.repository.StrategyRepository;
import com.fxdidier.trading_portal.app.web.mapper.StrategyMapper;
import com.fxdidier.trading_portal.app.web.model.StrategyDto;
import com.fxdidier.trading_portal.app.web.model.StrategyRequest;
import com.fxdidier.trading_portal.configuration.security.CurrentUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StrategyService {

    private final StrategyRepository repository;
    private final StrategyMapper mapper;
    private final CurrentUserService currentUserService;
    private final UserService userService;

    // -------- LISTAR --------

    /**
     * USER: ve sus estrategias + globales.
     * ADMIN: ve todas.
     */
    @Transactional(readOnly = true)
    public List<StrategyDto> findForCurrentUser() {
        Long currentUserId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        List<Strategy> strategies;
        if (isAdmin) {
            strategies = repository.findAll();
        } else {
            strategies = repository.findByGlobalTrueOrOwnerIdOrderByNameAsc(currentUserId);
        }
        return mapper.toDtoList(strategies);
    }

    /**
     * Sólo ADMIN: ver todas o por usuario específico.
     */
    @Transactional(readOnly = true)
    public List<StrategyDto> findByUser(Long userId) {
        List<Strategy> strategies = repository.findByOwnerIdOrderByNameAsc(userId);
        return mapper.toDtoList(strategies);
    }

    @Transactional(readOnly = true)
    public StrategyDto findById(Long id) {
        Strategy strategy = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Strategy not found with id: " + id));

        Long currentUserId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        if (!isAdmin && !strategy.isGlobal()
                && (strategy.getOwner() == null || !strategy.getOwner().getId().equals(currentUserId))) {
            throw new SecurityException("You are not allowed to access this strategy");
        }

        return mapper.toDto(strategy);
    }

    // -------- CREAR --------

    @Transactional
    public StrategyDto create(StrategyRequest request) {
        if (repository.existsByCode(request.code())) {
            throw new IllegalArgumentException("Strategy code already exists");
        }

        Strategy strategy = mapper.toEntity(request);

        boolean isAdmin = currentUserService.isAdmin();
        Long currentUserId = currentUserService.getId();

        boolean globalFlag = Boolean.TRUE.equals(request.global());

        if (isAdmin && globalFlag) {
            // Estrategia global (visible para todos, sin owner obligatorio)
            strategy.setGlobal(true);
            strategy.setOwner(null);
        } else {
            // Estrategia de usuario
            Long targetUserId;
            if (isAdmin && request.userId() != null) {
                targetUserId = request.userId();
            } else {
                targetUserId = currentUserId;
            }
            User owner = userService.getByIdOrThrow(targetUserId);
            strategy.setOwner(owner);
            strategy.setGlobal(false);
        }

        Strategy saved = repository.save(strategy);
        return mapper.toDto(saved);
    }

    // -------- ACTUALIZAR --------

    @Transactional
    public StrategyDto update(Long id, StrategyRequest request) {
        Strategy strategy = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Strategy not found with id: " + id));

        Long currentUserId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        // Reglas de acceso:
        // - Si es global: sólo ADMIN puede editar.
        // - Si no es global: sólo owner o ADMIN.
        if (strategy.isGlobal() && !isAdmin) {
            throw new SecurityException("Only admin can update global strategies");
        }
        if (!strategy.isGlobal()
                && !isAdmin
                && (strategy.getOwner() == null || !strategy.getOwner().getId().equals(currentUserId))) {
            throw new SecurityException("You are not allowed to update this strategy");
        }

        // Validar code único si cambia
        if (!strategy.getCode().equals(request.code())
                && repository.existsByCode(request.code())) {
            throw new IllegalArgumentException("Strategy code already exists");
        }

        mapper.updateEntityFromRequest(request, strategy);

        // Manejo de global / owner en update
        boolean globalFlag = Boolean.TRUE.equals(request.global());

        if (isAdmin && globalFlag) {
            strategy.setGlobal(true);
            strategy.setOwner(null);
        } else if (isAdmin) {
            // Admin puede reasignar owner
            Long targetUserId = (request.userId() != null) ? request.userId() : currentUserId;
            User owner = userService.getByIdOrThrow(targetUserId);
            strategy.setOwner(owner);
            strategy.setGlobal(false);
        } else {
            // Usuario normal: siempre suya, no puede hacerla global
            User owner = userService.getByIdOrThrow(currentUserId);
            strategy.setOwner(owner);
            strategy.setGlobal(false);
        }

        Strategy saved = repository.save(strategy);
        return mapper.toDto(saved);
    }

    // -------- ELIMINAR --------

    @Transactional
    public void delete(Long id) {
        Strategy strategy = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Strategy not found with id: " + id));

        Long currentUserId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        if (strategy.isGlobal() && !isAdmin) {
            throw new SecurityException("Only admin can delete global strategies");
        }
        if (!strategy.isGlobal()
                && !isAdmin
                && (strategy.getOwner() == null || !strategy.getOwner().getId().equals(currentUserId))) {
            throw new SecurityException("You are not allowed to delete this strategy");
        }

        repository.delete(strategy);
    }
}