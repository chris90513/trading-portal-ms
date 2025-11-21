package com.fxdidier.trading_portal.app.service;

import com.fxdidier.trading_portal.app.domain.entity.*;
import com.fxdidier.trading_portal.app.domain.repository.AccountRepository;
import com.fxdidier.trading_portal.app.web.mapper.AccountMapper;
import com.fxdidier.trading_portal.app.web.model.AccountDetailDto;
import com.fxdidier.trading_portal.app.web.model.AccountRequest;
import com.fxdidier.trading_portal.app.web.model.AccountSummaryDto;
import com.fxdidier.trading_portal.app.web.model.PageResponse;
import com.fxdidier.trading_portal.configuration.security.CurrentUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final BrokerService brokerService;
    private final AccountTypeService accountTypeService;
    private final EvaluationProgramService evaluationProgramService;
    private final EvaluationStepService evaluationStepService;
    private final AccountStatusService accountStatusService;
    private final UserService userService;
    private final CurrentUserService currentUserService;
    private final AccountMapper accountMapper;



    @Transactional(readOnly = true)
    public PageResponse<AccountSummaryDto> findSummariesForCurrentUser(Pageable pageable) {
        Long currentUserId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        Page<Account> page = isAdmin
                ? accountRepository.findAll(pageable)
                : accountRepository.findByUserId(currentUserId, pageable);

        Page<AccountSummaryDto> dtoPage = page.map(accountMapper::toSummaryDto);

        return PageResponse.from(dtoPage);
    }


    @Transactional(readOnly = true)
    public PageResponse<AccountSummaryDto> findByUser(Long userId, Pageable pageable) {
        Page<Account> page = accountRepository.findByUserId(userId, pageable);
        Page<AccountSummaryDto> dtoPage = page.map(accountMapper::toSummaryDto);
        return PageResponse.from(dtoPage);
    }

    @Transactional(readOnly = true)
    public AccountDetailDto findDetailById(Long id) {
        Account account = findById(id);
        return accountMapper.toDetailDto(account);
    }



    @Transactional
    public Account create(AccountRequest request) {
        if (accountRepository.existsByAccountNumber(request.accountNumber())) {
            throw new IllegalArgumentException("Account number already exists");
        }

        Account account = accountMapper.toEntity(request);
        applyRequestToEntity(account, request);

        return accountRepository.save(account);
    }


    @Transactional
    public Account update(Long id, AccountRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));

        Long currentUserId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        if (!isAdmin && !account.getUser().getId().equals(currentUserId)) {
            throw new SecurityException("You are not allowed to update this account");
        }

        if (!account.getAccountNumber().equals(request.accountNumber())
                && accountRepository.existsByAccountNumber(request.accountNumber())) {
            throw new IllegalArgumentException("Account number already exists");
        }

        accountMapper.updateEntityFromRequest(request, account);
        applyRequestToEntity(account, request);

        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public Account findById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));

        Long currentUserId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        if (!isAdmin && !account.getUser().getId().equals(currentUserId)) {
            throw new SecurityException("You are not allowed to access this account");
        }

        return account;
    }


    // -------- ELIMINAR --------

    @Transactional
    public void delete(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));

        Long currentUserId = currentUserService.getId();
        boolean isAdmin = currentUserService.isAdmin();

        if (!isAdmin && !account.getUser().getId().equals(currentUserId)) {
            throw new SecurityException("You are not allowed to delete this account");
        }

        accountRepository.delete(account);
    }

    // ---------- Helpers ----------

    private void applyRequestToEntity(Account account, AccountRequest request) {

        account.setDescription(request.description());
        account.setAccountNumber(request.accountNumber());

        if (request.initialBalance() != null) {
            account.setInitialBalance(request.initialBalance());
        }

        Broker broker = brokerService.getByIdOrThrow(request.brokerId());
        account.setBroker(broker);

        AccountType accountType = accountTypeService.getByIdOrThrow(request.accountTypeId());
        account.setAccountType(accountType);

        if (request.evaluationProgramId() != null) {
            EvaluationProgram evaluationProgram =
                    evaluationProgramService.getByIdOrThrow(request.evaluationProgramId());
            account.setEvaluationProgram(evaluationProgram);
        } else {
            account.setEvaluationProgram(null);
        }

        if (request.evaluationStepId() != null) {
            EvaluationStep step = evaluationStepService.getByIdOrThrow(request.evaluationStepId());
            account.setEvaluationStep(step);
        } else {
            account.setEvaluationStep(null);
        }

        AccountStatus status = accountStatusService.getByIdOrThrow(request.statusId());
        account.setStatus(status);

        // --- usuario según rol ---
        Long targetUserId;
        boolean isAdmin = currentUserService.isAdmin();

        if (isAdmin && request.userId() != null) {
            // Admin puede crear/editar cuentas para otro usuario
            targetUserId = request.userId();
        } else {
            // Usuario normal: siempre su propia cuenta
            targetUserId = currentUserService.getId();
        }

        User user = userService.getByIdOrThrow(targetUserId);
        account.setUser(user);

        if (request.previousAccountId() != null) {
            Account previous = accountRepository.findById(request.previousAccountId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Previous Account not found with id: " + request.previousAccountId()
                    ));
            account.setPreviousAccount(previous);
        } else {
            account.setPreviousAccount(null);
        }
    }
}
