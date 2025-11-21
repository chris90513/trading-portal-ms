package com.fxdidier.trading_portal.app.web.controller;

import com.fxdidier.trading_portal.app.service.StrategyConfirmationService;
import com.fxdidier.trading_portal.app.web.model.StrategyConfirmationDto;
import com.fxdidier.trading_portal.app.web.model.StrategyConfirmationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/strategy-confirmations")
@RequiredArgsConstructor
public class StrategyConfirmationController {

    private final StrategyConfirmationService service;

    @GetMapping
    public ResponseEntity<List<StrategyConfirmationDto>> findForCurrentUser() {
        return ResponseEntity.ok(service.findForCurrentUser());
    }

    @GetMapping("/by-strategy/{strategyId}")
    public ResponseEntity<List<StrategyConfirmationDto>> findByStrategy(@PathVariable Long strategyId) {
        return ResponseEntity.ok(service.findByStrategy(strategyId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StrategyConfirmationDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StrategyConfirmationDto> create(
            @Valid @RequestBody StrategyConfirmationRequest request
    ) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StrategyConfirmationDto> update(
            @PathVariable Long id,
            @Valid @RequestBody StrategyConfirmationRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
