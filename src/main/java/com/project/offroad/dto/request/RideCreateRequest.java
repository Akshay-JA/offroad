package com.project.offroad.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;

public record RideCreateRequest(

        @NotBlank(message = "Title is required")
        String title,

        String description,

        @NotBlank
        String startCity,

        @NotBlank
        String startState,

        @NotBlank
        String startCountry,

        @NotBlank
        String endCity,

        @NotBlank
        String endState,

        @NotBlank
        String endCountry,

        @NotNull
        @Positive
        Integer maxParticipants,

        @NotNull
        @Future(message = "Start time must be in the future")
        OffsetDateTime startTime,

        @NotNull
        @Future(message = "End time must be in the future")
        OffsetDateTime endTime
) {
}