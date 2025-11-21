package com.fxdidier.trading_portal.app.web.controller;

import com.fxdidier.trading_portal.app.service.TradeService;
import com.fxdidier.trading_portal.app.web.model.PageResponse;
import com.fxdidier.trading_portal.app.web.model.TradeDto;
import com.fxdidier.trading_portal.app.web.model.TradeRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    // LISTAR trades del usuario actual (o todos si es admin)
    @GetMapping
    public ResponseEntity<PageResponse<TradeDto>> findForCurrentUser(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(tradeService.findForCurrentUser(pageable));
    }

    // DETALLE
    @GetMapping("/{id}")
    public ResponseEntity<TradeDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tradeService.findById(id));
    }

    // CREAR
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TradeDto> create(@Valid @RequestBody TradeRequest request) {
        TradeDto dto = tradeService.create(request);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<TradeDto> update(@PathVariable Long id,
                                           @Valid @RequestBody TradeRequest request) {
        TradeDto dto = tradeService.update(id, request);
        return ResponseEntity.ok(dto);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tradeService.delete(id);
    }
}
