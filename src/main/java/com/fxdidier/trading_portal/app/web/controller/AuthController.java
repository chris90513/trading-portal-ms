package com.fxdidier.trading_portal.app.web.controller;


import com.fxdidier.trading_portal.app.service.JwtService;
import com.fxdidier.trading_portal.app.service.UserService;
import com.fxdidier.trading_portal.app.web.model.AuthRequest;
import com.fxdidier.trading_portal.app.web.model.AuthResponse;
import com.fxdidier.trading_portal.app.web.model.RegisterRequest;
import com.fxdidier.trading_portal.app.web.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthResponse(token));
    }


    /**
     * Registro público: siempre ROLE_USER (la lógica está en UserService).
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest request) {
        UserDto created = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Refresh de token: recibe el viejo y genera uno nuevo si es válido
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }
        String oldToken = authHeader.substring(7);
        String username = jwtService.extractUsername(oldToken);

        // (Si quieres, aquí podrías validar expiración, etc.)
        String newToken = jwtService.generateToken(username);

        return ResponseEntity.ok(new AuthResponse(newToken));
    }

    /**
     * Usuario actual (cualquier usuario autenticado).
     * Puedes dejar esto aquí o moverlo a UserController, como prefieras.
     */
    @GetMapping("/me")
    public ResponseEntity<UserDto> me(Principal principal) {
        return ResponseEntity.ok(userService.getCurrentUser(principal));
    }
}
