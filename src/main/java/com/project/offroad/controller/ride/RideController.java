package com.project.offroad.controller.ride;

import com.project.offroad.domain.entity.Ride;
import com.project.offroad.domain.entity.User;
import com.project.offroad.dto.mapper.RideMapper;
import com.project.offroad.dto.request.RideCreateRequest;
import com.project.offroad.dto.response.RideResponse;
import com.project.offroad.service.ride.RideService;
import com.project.offroad.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;
    private final UserService userService;
    private final RideMapper rideMapper;

    // ---------- CREATE RIDE ----------

    @PostMapping
    public ResponseEntity<RideResponse> createRide(
            @Valid @RequestBody RideCreateRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User organizer = userService.getByEmail(userDetails.getUsername());

        Ride ride = rideMapper.toEntity(request);
        Ride savedRide = rideService.createRide(ride, organizer);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(rideMapper.toResponse(savedRide));
    }

    // ---------- JOIN RIDE ----------

    @PostMapping("/{rideId}/join")
    public ResponseEntity<Void> joinRide(
            @PathVariable Long rideId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userService.getByEmail(userDetails.getUsername());
        rideService.requestToJoin(rideId, user);

        return ResponseEntity.ok().build();
    }

    // ---------- APPROVE PARTICIPANT ----------

    @PostMapping("/{rideId}/approve/{userId}")
    public ResponseEntity<Void> approveParticipant(
            @PathVariable Long rideId,
            @PathVariable Long userId
    ) {
        rideService.approveParticipant(rideId, userId);
        return ResponseEntity.ok().build();
    }

    // ---------- COMPLETE RIDE ----------

    @PostMapping("/{rideId}/complete")
    public ResponseEntity<Void> completeRide(
            @PathVariable Long rideId
    ) {
        rideService.completeRide(rideId);
        return ResponseEntity.ok().build();
    }
}