package com.fxdidier.trading_portal.app.service;

import com.fxdidier.trading_portal.app.domain.entity.*;
import com.fxdidier.trading_portal.app.domain.repository.*;
import com.fxdidier.trading_portal.app.web.mapper.TradeMapper;
import com.fxdidier.trading_portal.app.web.model.PageResponse;
import com.fxdidier.trading_portal.app.web.model.TradeDto;
import com.fxdidier.trading_portal.app.web.model.TradeRequest;
import com.fxdidier.trading_portal.configuration.security.CurrentUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;
    private final AccountRepository accountRepository;
    private final StrategyRepository strategyRepository;
    private final StrategyDirectionRepository strategyDirectionRepository;
    private final StrategyConfirmationRepository strategyConfirmationRepository;
    private final CurrentUserService currentUserService;
    private final TradeMapper tradeMapper;

    // -------- LISTAR --------

    @Transactional(readOnly = true)
    public PageResponse<TradeDto> findForCurrentUser(Pageable pageable) {

        Long currentUserId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        Page<Trade> page = isAdmin
                ? tradeRepository.findAll(pageable)
                : tradeRepository.findByUserId(currentUserId, pageable);

        Page<TradeDto> dtoPage = page.map(tradeMapper::toDto);
        return PageResponse.from(dtoPage);
    }

    @Transactional(readOnly = true)
    public TradeDto findById(Long id) {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trade not found with id: " + id));

        Long currentUserId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        if (!isAdmin && !trade.getUser().getId().equals(currentUserId)) {
            throw new SecurityException("You are not allowed to access this trade");
        }

        return tradeMapper.toDto(trade);
    }

    // -------- CREAR --------

    @Transactional
    public TradeDto create(TradeRequest request) {

        Account account = accountRepository.findById(request.accountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + request.accountId()));

        Long currentUserId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        // Usuario normal solo puede crear trades en sus cuentas
        if (!isAdmin && !account.getUser().getId().equals(currentUserId)) {
            throw new SecurityException("You are not allowed to create trades on this account");
        }

        Trade trade = tradeMapper.toEntity(request);

        // relaciones principales
        trade.setAccount(account);
        trade.setUser(account.getUser());

        // relaciones opcionales
        applyStrategyRelations(request, trade);

        Trade saved = tradeRepository.save(trade);
        return tradeMapper.toDto(saved);
    }

    // -------- ACTUALIZAR --------

    @Transactional
    public TradeDto update(Long id, TradeRequest request) {

        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trade not found with id: " + id));

        Long currentUserId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        if (!isAdmin && !trade.getUser().getId().equals(currentUserId)) {
            throw new SecurityException("You are not allowed to update this trade");
        }

        // campos simples
        tradeMapper.updateEntityFromRequest(request, trade);

        // si cambia la cuenta
        if (!trade.getAccount().getId().equals(request.accountId())) {
            Account newAccount = accountRepository.findById(request.accountId())
                    .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + request.accountId()));

            if (!isAdmin && !newAccount.getUser().getId().equals(currentUserId)) {
                throw new SecurityException("You are not allowed to move this trade to that account");
            }

            trade.setAccount(newAccount);
            trade.setUser(newAccount.getUser());
        }

        // relaciones opcionales
        applyStrategyRelations(request, trade);

        Trade saved = tradeRepository.save(trade);
        return tradeMapper.toDto(saved);
    }

    // -------- ELIMINAR --------

    @Transactional
    public void delete(Long id) {

        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trade not found with id: " + id));

        Long currentUserId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        if (!isAdmin && !trade.getUser().getId().equals(currentUserId)) {
            throw new SecurityException("You are not allowed to delete this trade");
        }

        tradeRepository.delete(trade);
    }

    // -------- Helper para relaciones de estrategia --------

    private void applyStrategyRelations(TradeRequest request, Trade trade) {

        // Strategy
        if (request.strategyId() != null) {
            Strategy strategy = strategyRepository.findById(request.strategyId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Strategy not found with id: " + request.strategyId()
                    ));
            trade.setStrategy(strategy);
        } else {
            trade.setStrategy(null);
        }

        // Direction
        if (request.directionId() != null) {
            StrategyDirection direction = strategyDirectionRepository.findById(request.directionId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Direction not found with id: " + request.directionId()
                    ));
            trade.setDirection(direction);
        } else {
            trade.setDirection(null);
        }

        // Confirmation
        if (request.confirmationId() != null) {
            StrategyConfirmation confirmation = strategyConfirmationRepository.findById(request.confirmationId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Confirmation not found with id: " + request.confirmationId()
                    ));
            trade.setConfirmation(confirmation);
        } else {
            trade.setConfirmation(null);
        }
    }
}
