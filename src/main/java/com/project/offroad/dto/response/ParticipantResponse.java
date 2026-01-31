package com.project.offroad.dto.response;

public record ParticipantResponse(
        Long userId,
        String userName,
        String status
) {
}