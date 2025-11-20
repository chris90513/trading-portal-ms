package com.fxdidier.trading_portal.app.service;

import com.fxdidier.trading_portal.app.domain.entity.User;
import com.fxdidier.trading_portal.app.domain.repository.UserRepository;
import com.fxdidier.trading_portal.app.web.model.AdminCreateUserRequest;
import com.fxdidier.trading_portal.app.web.model.ChangePasswordRequest;
import com.fxdidier.trading_portal.app.web.model.RegisterRequest;
import com.fxdidier.trading_portal.app.web.model.UserDto;
import com.fxdidier.trading_portal.util.enums.Role;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setEnabled(true);

        user = userRepository.save(user);
        return toDto(user);
    }

    /**
     * Creación de usuarios desde el panel admin
     */
    public UserDto createUserByAdmin(AdminCreateUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setEnabled(request.isEnabled());

        user = userRepository.save(user);
        return toDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    // ========= Métodos de dominio usados por otros servicios =========

    @Transactional(readOnly = true)
    public User getByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public User getByUsernameOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
    }

    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = getByIdOrThrow(userId);

        // Validar contraseña actual
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta");
        }

        // Aquí podrías validar reglas de complejidad del newPassword si quieres
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }

    // ========= Mapper =========

    private UserDto toDto(User u) {
        UserDto dto = new UserDto();
        dto.setId(u.getId());
        dto.setUsername(u.getUsername());
        dto.setRole(u.getRole().name());
        dto.setEnabled(u.isEnabled());
        return dto;
    }
}
