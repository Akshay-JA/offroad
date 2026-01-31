package com.project.offroad.dto.response;

import java.time.OffsetDateTime;

public record RideResponse(

        Long id,
        String title,
        String description,

        String startCity,
        String startState,
        String startCountry,

        String endCity,
        String endState,
        String endCountry,

        Integer maxParticipants,
        Integer currentParticipants,

        String status,

        OffsetDateTime startTime,
        OffsetDateTime endTime
) {
}