package com.fxdidier.trading_portal.app.service;

import com.fxdidier.trading_portal.app.domain.entity.*;
import com.fxdidier.trading_portal.app.domain.repository.StrategyConfirmationRepository;
import com.fxdidier.trading_portal.app.domain.repository.StrategyRepository;
import com.fxdidier.trading_portal.app.web.mapper.StrategyConfirmationMapper;
import com.fxdidier.trading_portal.app.web.model.StrategyConfirmationDto;
import com.fxdidier.trading_portal.app.web.model.StrategyConfirmationRequest;
import com.fxdidier.trading_portal.configuration.security.CurrentUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StrategyConfirmationService {

    private final StrategyConfirmationRepository repository;
    private final StrategyRepository strategyRepository;
    private final StrategyConfirmationMapper mapper;
    private final CurrentUserService currentUserService;
    private final UserService userService;

    // LISTAR
    @Transactional(readOnly = true)
    public List<StrategyConfirmationDto> findForCurrentUser() {

        Long userId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        List<StrategyConfirmation> list;

        if (isAdmin) {
            list = repository.findAll();
        } else {
            list = repository.findByGlobalTrueOrOwnerIdOrderByNameAsc(userId);
        }

        return mapper.toDtoList(list);
    }

    @Transactional(readOnly = true)
    public List<StrategyConfirmationDto> findByStrategy(Long strategyId) {

        List<StrategyConfirmation> list = repository.findByStrategyId(strategyId);
        return mapper.toDtoList(list);
    }

    @Transactional(readOnly = true)
    public StrategyConfirmationDto findById(Long id) {

        StrategyConfirmation entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StrategyConfirmation not found"));

        Long userId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        if (!entity.isGlobal()
                && !isAdmin
                && (entity.getOwner() == null || !entity.getOwner().getId().equals(userId))) {
            throw new SecurityException("You are not allowed to access this confirmation");
        }

        return mapper.toDto(entity);
    }

    // CREAR
    @Transactional
    public StrategyConfirmationDto create(StrategyConfirmationRequest request) {

        Strategy strategy = strategyRepository.findById(request.strategyId())
                .orElseThrow(() -> new EntityNotFoundException("Strategy not found"));

        if (repository.existsByCodeAndStrategyId(request.code(), request.strategyId())) {
            throw new IllegalArgumentException("Confirmation code already exists for this strategy");
        }

        StrategyConfirmation entity = mapper.toEntity(request);

        boolean isAdmin = currentUserService.isAdmin();
        Long currentUserId = currentUserService.getId();

        if (isAdmin && Boolean.TRUE.equals(request.global())) {
            entity.setGlobal(true);
            entity.setOwner(null);
        } else {
            Long targetUserId = (isAdmin && request.userId() != null)
                    ? request.userId()
                    : currentUserId;

            User owner = userService.getByIdOrThrow(targetUserId);

            entity.setGlobal(false);
            entity.setOwner(owner);
        }

        entity.setStrategy(strategy);

        return mapper.toDto(repository.save(entity));
    }

    // UPDATE
    @Transactional
    public StrategyConfirmationDto update(Long id, StrategyConfirmationRequest request) {

        StrategyConfirmation entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Confirmation not found"));

        Long userId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        // Reglas de acceso
        if (entity.isGlobal() && !isAdmin) {
            throw new SecurityException("Only admin can update global confirmations");
        }
        if (!entity.isGlobal()
                && !isAdmin
                && (entity.getOwner() == null || !entity.getOwner().getId().equals(userId))) {
            throw new SecurityException("You cannot update this confirmation");
        }

        mapper.updateFromRequest(request, entity);

        // asignación de owner / global
        if (isAdmin && Boolean.TRUE.equals(request.global())) {
            entity.setGlobal(true);
            entity.setOwner(null);
        } else {
            Long targetUserId = isAdmin && request.userId() != null
                    ? request.userId()
                    : userId;

            User owner = userService.getByIdOrThrow(targetUserId);
            entity.setOwner(owner);
            entity.setGlobal(false);
        }

        // si cambia de estrategia
        Strategy strategy = strategyRepository.findById(request.strategyId())
                .orElseThrow(() -> new EntityNotFoundException("Strategy not found"));
        entity.setStrategy(strategy);

        return mapper.toDto(repository.save(entity));
    }

    // DELETE
    @Transactional
    public void delete(Long id) {

        StrategyConfirmation entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Confirmation not found"));

        Long userId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        if (entity.isGlobal() && !isAdmin) {
            throw new SecurityException("Only admin can delete global confirmations");
        }
        if (!entity.isGlobal()
                && !isAdmin
                && (entity.getOwner() == null || !entity.getOwner().getId().equals(userId))) {
            throw new SecurityException("You cannot delete this confirmation");
        }

        repository.delete(entity);
    }
}
