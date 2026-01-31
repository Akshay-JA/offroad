package com.project.offroad.controller.user;

import com.project.offroad.domain.entity.User;
import com.project.offroad.dto.mapper.UserMapper;
import com.project.offroad.dto.response.UserResponse;
import com.project.offroad.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    // ---------- CURRENT USER PROFILE ----------

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userService.getByEmail(userDetails.getUsername());
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    // ---------- GET USER BY ID (ADMIN ONLY) ----------

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id
    ) {
        User user = userService.getById(id);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }
}