package com.fxdidier.trading_portal.app.web.controller;

import com.fxdidier.trading_portal.app.service.UserService;
import com.fxdidier.trading_portal.app.web.model.AdminCreateUserRequest;
import com.fxdidier.trading_portal.app.web.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.fxdidier.trading_portal.configuration.security.CurrentUserService;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CurrentUserService currentUserService;

    // Solo ADMIN puede listar usuarios
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * Obtener usuario por id – solo ADMIN.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }


    /**
     * Eliminar usuario – solo ADMIN.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {
        Long id = currentUserService.getId();
        UserDto dto = userService.findById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Crear usuario (puede ser ADMIN o USER) – solo ADMIN.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto createUser(@RequestBody AdminCreateUserRequest request) {
        return userService.createUserByAdmin(request);
    }
}
