package com.fxdidier.trading_portal.app.web.controller;

import com.fxdidier.trading_portal.app.service.StrategyDirectionService;
import com.fxdidier.trading_portal.app.web.model.StrategyDirectionDto;
import com.fxdidier.trading_portal.app.web.model.StrategyDirectionRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/strategy-directions")
@RequiredArgsConstructor
public class StrategyDirectionController {

    private final StrategyDirectionService service;

    @GetMapping
    public ResponseEntity<List<StrategyDirectionDto>> findForCurrentUser() {
        return ResponseEntity.ok(service.findForCurrentUser());
    }

    @GetMapping("/by-strategy/{strategyId}")
    public ResponseEntity<List<StrategyDirectionDto>> findByStrategy(@PathVariable Long strategyId) {
        return ResponseEntity.ok(service.findByStrategy(strategyId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StrategyDirectionDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StrategyDirectionDto> create(
            @Valid @RequestBody StrategyDirectionRequest request
    ) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StrategyDirectionDto> update(
            @PathVariable Long id,
            @Valid @RequestBody StrategyDirectionRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
