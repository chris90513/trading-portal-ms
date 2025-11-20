package com.fxdidier.trading_portal.app.web.controller;

import com.fxdidier.trading_portal.app.domain.entity.Account;
import com.fxdidier.trading_portal.app.service.AccountService;
import com.fxdidier.trading_portal.app.web.model.AccountRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    // ========== LISTAR CUENTAS ==========
    /**
     * USER → ve solo sus cuentas
     * ADMIN → ve todas
     */
    @GetMapping
    public ResponseEntity<Page<Account>> getAccounts(Pageable pageable) {
        return ResponseEntity.ok(accountService.findForCurrentUser(pageable));
    }

    // ========== LISTAR CUENTAS POR USUARIO (ADMIN) ==========
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<Page<Account>> getAccountsByUser(
            @PathVariable Long userId,
            Pageable pageable) {

        return ResponseEntity.ok(accountService.findByUser(userId, pageable));
    }

    // ========== OBTENER UNA CUENTA ==========
    @GetMapping("/{id}")
    public ResponseEntity<Account> getById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.findById(id));
    }

    // ========== CREAR CUENTA ==========
    /**
     * USER → se asigna a sí mismo
     * ADMIN → puede asignar userId en el request
     */
    @PostMapping
    public ResponseEntity<Account> create(@Valid @RequestBody AccountRequest request) {
        Account created = accountService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ========== ACTUALIZAR CUENTA ==========
    @PutMapping("/{id}")
    public ResponseEntity<Account> update(
            @PathVariable Long id,
            @Valid @RequestBody AccountRequest request) {

        return ResponseEntity.ok(accountService.update(id, request));
    }

    // ========== ELIMINAR CUENTA ==========
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
