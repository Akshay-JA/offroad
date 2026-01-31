package com.project.offroad.controller.auth;

import com.project.offroad.config.security.JwtTokenProvider;
import com.project.offroad.dto.request.LoginRequest;
import com.project.offroad.dto.request.RegisterRequest;
import com.project.offroad.dto.response.AuthResponse;
import com.project.offroad.dto.response.UserResponse;
import com.project.offroad.dto.mapper.UserMapper;
import com.project.offroad.domain.entity.User;
import com.project.offroad.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    // ---------- REGISTER ----------

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        User user = authService.register(
                request.fullName(),
                request.email(),
                request.password()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper.toResponse(user));
    }

    // ---------- LOGIN ----------

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(),
                                request.password()
                        )
                );

        String role = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        String token = jwtTokenProvider.generateToken(
                request.email(),
                role.replace("ROLE_", "")
        );

        return ResponseEntity.ok(
                new AuthResponse(token, "Bearer")
        );
    }
}