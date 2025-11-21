package com.fxdidier.trading_portal.app.web.controller;

import com.fxdidier.trading_portal.app.service.StrategyService;
import com.fxdidier.trading_portal.app.web.model.StrategyDto;
import com.fxdidier.trading_portal.app.web.model.StrategyRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/strategies")
@RequiredArgsConstructor
public class StrategyController {

    private final StrategyService service;

    // USER: ve sus estrategias + globales
    // ADMIN: ve todas
    @GetMapping
    public ResponseEntity<List<StrategyDto>> findForCurrentUser() {
        return ResponseEntity.ok(service.findForCurrentUser());
    }

    // ADMIN: ver estrategias de un usuario específico
    @GetMapping("/by-user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StrategyDto>> findByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.findByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StrategyDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StrategyDto> create(@Valid @RequestBody StrategyRequest request) {
        StrategyDto dto = service.create(request);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StrategyDto> update(@PathVariable Long id,
                                              @Valid @RequestBody StrategyRequest request) {
        StrategyDto dto = service.update(id, request);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
