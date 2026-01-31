package com.project.offroad.dto.response;

public record AuthResponse(
        String token,
        String tokenType
) {
}