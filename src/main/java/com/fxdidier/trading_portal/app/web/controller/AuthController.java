package com.fxdidier.trading_portal.app.web.controller;


import com.fxdidier.trading_portal.app.web.model.*;
import com.fxdidier.trading_portal.configuration.security.JwtService;
import com.fxdidier.trading_portal.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.fxdidier.trading_portal.configuration.security.CurrentUserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final CurrentUserService currentUserService;

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

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest request) {
        UserDto created = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().build();
        }

        String oldToken = authHeader.substring(7);
        String username = jwtService.extractUsername(oldToken);

        String newToken = jwtService.generateToken(username);

        return ResponseEntity.ok(new AuthResponse(newToken));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {

        Long id = currentUserService.getId();
        // devolver en formato DTO
        UserDto dto = userService.findById(id);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest request) {

        Long currentUserId = currentUserService.getId();
        userService.changePassword(currentUserId, request);

        return ResponseEntity.noContent().build();
    }
}
