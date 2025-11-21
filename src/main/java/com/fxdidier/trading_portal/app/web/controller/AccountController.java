package com.fxdidier.trading_portal.app.web.controller;

import com.fxdidier.trading_portal.app.domain.entity.Account;
import com.fxdidier.trading_portal.app.service.AccountService;
import com.fxdidier.trading_portal.app.web.mapper.AccountMapper;
import com.fxdidier.trading_portal.app.web.model.AccountDetailDto;
import com.fxdidier.trading_portal.app.web.model.AccountRequest;
import com.fxdidier.trading_portal.app.web.model.AccountSummaryDto;
import com.fxdidier.trading_portal.app.web.model.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    // ------------------- USER / ADMIN: cuentas del usuario actual -------------------
    // USER normal: solo sus cuentas
    // ADMIN: ve todas (la lógica está en el service)
    @GetMapping
    public ResponseEntity<PageResponse<AccountSummaryDto>> findForCurrentUser(@ParameterObject Pageable pageable) {
        PageResponse<AccountSummaryDto> response = accountService.findSummariesForCurrentUser(pageable);
        return ResponseEntity.ok(response);
    }

    // ------------------- USER / ADMIN: detalle de una cuenta -------------------
    // La validación de acceso está en AccountService.findById(...)
    @GetMapping("/{id}")
    public ResponseEntity<AccountDetailDto> findById(@PathVariable Long id) {
        AccountDetailDto dto = accountService.findDetailById(id);
        return ResponseEntity.ok(dto);
    }

    // ------------------- ADMIN: cuentas por usuario específico -------------------
    @GetMapping("/by-user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageResponse<AccountSummaryDto>> findByUser(@PathVariable Long userId,
                                                                      @ParameterObject Pageable pageable) {
        PageResponse<AccountSummaryDto> response = accountService.findByUser(userId, pageable);
        return ResponseEntity.ok(response);
    }

    // ------------------- CREAR CUENTA -------------------
    // USER: crea cuenta para sí mismo
    // ADMIN: puede crear cuenta para otro userId (viene en AccountRequest)
    @PostMapping
    public ResponseEntity<AccountDetailDto> create(@Valid @RequestBody AccountRequest request) {
        Account account = accountService.create(request);
        AccountDetailDto dto = accountMapper.toDetailDto(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // ------------------- ACTUALIZAR CUENTA -------------------
    // USER: sólo puede actualizar sus cuentas
    // ADMIN: puede actualizar cualquier cuenta
    @PutMapping("/{id}")
    public ResponseEntity<AccountDetailDto> update(@PathVariable Long id,
                                                   @Valid @RequestBody AccountRequest request) {
        Account account = accountService.update(id, request);
        AccountDetailDto dto = accountMapper.toDetailDto(account);
        return ResponseEntity.ok(dto);
    }

    // ------------------- ELIMINAR CUENTA -------------------
    // USER: sólo puede eliminar sus cuentas
    // ADMIN: puede eliminar cualquier cuenta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
