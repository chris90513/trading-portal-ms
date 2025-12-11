package com.fxdidier.trading_portal.app.service;

import com.fxdidier.trading_portal.app.domain.entity.Strategy;
import com.fxdidier.trading_portal.app.domain.entity.StrategyDirection;
import com.fxdidier.trading_portal.app.domain.entity.User;
import com.fxdidier.trading_portal.app.domain.repository.StrategyDirectionRepository;
import com.fxdidier.trading_portal.app.domain.repository.StrategyRepository;
import com.fxdidier.trading_portal.app.web.mapper.StrategyDirectionMapper;
import com.fxdidier.trading_portal.app.web.model.StrategyDirectionDto;
import com.fxdidier.trading_portal.app.web.model.StrategyDirectionRequest;
import com.fxdidier.trading_portal.configuration.security.CurrentUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StrategyDirectionService {

    private final StrategyDirectionRepository repository;
    private final StrategyRepository strategyRepository;
    private final StrategyDirectionMapper mapper;
    private final CurrentUserService currentUserService;
    private final UserService userService;

    // LISTAR para usuario actual
    @Transactional(readOnly = true)
    public List<StrategyDirectionDto> findForCurrentUser() {
        Long userId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        List<StrategyDirection> list;
        if (isAdmin) {
            list = repository.findAll();
        } else {
            list = repository.findByGlobalTrueOrOwnerIdOrderByNameAsc(userId);
        }

        return mapper.toDtoList(list);
    }

    @Transactional(readOnly = true)
    public List<StrategyDirectionDto> findByStrategy(Long strategyId) {
        List<StrategyDirection> list = repository.findByStrategyId(strategyId);
        return mapper.toDtoList(list);
    }

    @Transactional(readOnly = true)
    public StrategyDirectionDto findById(Long id) {

        StrategyDirection entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StrategyDirection not found"));

        Long userId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        if (!entity.isGlobal()
                && !isAdmin
                && (entity.getOwner() == null || !entity.getOwner().getId().equals(userId))) {
            throw new SecurityException("You are not allowed to access this direction");
        }

        return mapper.toDto(entity);
    }

    // CREAR
    @Transactional
    public StrategyDirectionDto create(StrategyDirectionRequest request) {

        Strategy strategy = strategyRepository.findById(request.strategyId())
                .orElseThrow(() -> new EntityNotFoundException("Strategy not found"));

        if (repository.existsByCodeAndStrategyId(request.code(), request.strategyId())) {
            throw new IllegalArgumentException("Direction code already exists for this strategy");
        }

        StrategyDirection entity = mapper.toEntity(request);

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
    public StrategyDirectionDto update(Long id, StrategyDirectionRequest request) {

        StrategyDirection entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Direction not found"));

        Long userId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        // Reglas de acceso
        if (entity.isGlobal() && !isAdmin) {
            throw new SecurityException("Only admin can update global directions");
        }
        if (!entity.isGlobal()
                && !isAdmin
                && (entity.getOwner() == null || !entity.getOwner().getId().equals(userId))) {
            throw new SecurityException("You cannot update this direction");
        }


        mapper.updateFromRequest(request, entity);

        // owner / global
        if (isAdmin && Boolean.TRUE.equals(request.global())) {
            entity.setGlobal(true);
            entity.setOwner(null);
        } else {
            Long targetUserId = (isAdmin && request.userId() != null)
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

        StrategyDirection entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Direction not found"));

        Long userId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        if (entity.isGlobal() && !isAdmin) {
            throw new SecurityException("Only admin can delete global directions");
        }
        if (!entity.isGlobal()
                && !isAdmin
                && (entity.getOwner() == null || !entity.getOwner().getId().equals(userId))) {
            throw new SecurityException("You cannot delete this direction");
        }

        repository.delete(entity);
    }
}
