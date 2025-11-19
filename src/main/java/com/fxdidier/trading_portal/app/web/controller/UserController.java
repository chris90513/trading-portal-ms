package com.fxdidier.trading_portal.app.web.controller;

import com.fxdidier.trading_portal.app.service.UserService;
import com.fxdidier.trading_portal.app.web.model.AdminCreateUserRequest;
import com.fxdidier.trading_portal.app.web.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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

    // Usuario actual (cualquier usuario logueado)
    @GetMapping("/me")
    public ResponseEntity<UserDto> me(Principal principal) {
        return ResponseEntity.ok(userService.getCurrentUser(principal));
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
