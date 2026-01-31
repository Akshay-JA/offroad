package com.project.offroad.service.ride;


import com.project.offroad.domain.entity.Ride;
import com.project.offroad.domain.entity.RideParticipant;
import com.project.offroad.domain.entity.User;
import com.project.offroad.domain.enumtype.ParticipantStatus;
import com.project.offroad.domain.enumtype.RideStatus;
import com.project.offroad.exception.BusinessException;
import com.project.offroad.exception.ResourceNotFoundException;
import com.project.offroad.repo.RideParticipantRepository;
import com.project.offroad.repo.RideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final RideParticipantRepository rideParticipantRepository;

    // ---------------- CREATE RIDE ----------------

    @Transactional
    public Ride createRide(Ride ride, User organizer) {

        ride.setOrganizer(organizer);
        ride.setStatus(RideStatus.PUBLISHED);
        ride.setCurrentParticipants(0);

        if (ride.getStartTime().isBefore(OffsetDateTime.now())) {
            throw new BusinessException("Ride start time must be in the future");
        }

        return rideRepository.save(ride);
    }

    // ---------------- JOIN RIDE ----------------

    @Transactional
    public void requestToJoin(Long rideId, User user) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found"));

        if (ride.getStatus() != RideStatus.PUBLISHED) {
            throw new BusinessException("Ride is not open for joining");
        }

        rideParticipantRepository.findByRideAndUser(ride, user)
                .ifPresent(rp -> {
                    throw new BusinessException("User already associated with this ride");
                });

        RideParticipant rp = new RideParticipant();
        rp.setRide(ride);
        rp.setUser(user);
        rp.setStatus(ParticipantStatus.REQUESTED);

        rideParticipantRepository.save(rp);
    }

    // ---------------- APPROVE PARTICIPANT ----------------

    @Transactional
    public void approveParticipant(Long rideId, Long userId) {

        RideParticipant rp = rideParticipantRepository.findByRideAndUser(
                        getRideOrThrow(rideId),
                        new User() {{ setId(userId); }}
                )
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found"));

        if (rp.getStatus() != ParticipantStatus.REQUESTED) {
            throw new BusinessException("Invalid participant state");
        }

        Ride ride = rp.getRide();

        if (ride.getCurrentParticipants() >= ride.getMaxParticipants()) {
            throw new BusinessException("Ride capacity exceeded");
        }

        rp.setStatus(ParticipantStatus.APPROVED);
        ride.setCurrentParticipants(ride.getCurrentParticipants() + 1);
    }

    // ---------------- COMPLETE RIDE ----------------

    @Transactional
    public void completeRide(Long rideId) {

        Ride ride = getRideOrThrow(rideId);

        ride.setStatus(RideStatus.COMPLETED);

        rideParticipantRepository
                .findByRideAndStatus(ride, ParticipantStatus.APPROVED)
                .forEach(rp -> rp.setStatus(ParticipantStatus.COMPLETED));
    }

    // ---------------- HELPER ----------------

    private Ride getRideOrThrow(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found"));
    }
}