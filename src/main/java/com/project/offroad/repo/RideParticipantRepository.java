package com.project.offroad.repo;


import com.project.offroad.domain.entity.Ride;
import com.project.offroad.domain.entity.RideParticipant;
import com.project.offroad.domain.entity.User;
import com.project.offroad.domain.enumtype.ParticipantStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RideParticipantRepository
        extends JpaRepository<RideParticipant, Long> {

    Optional<RideParticipant> findByRideAndUser(Ride ride, User user);

    long countByRideAndStatus(Ride ride, ParticipantStatus status);

    List<RideParticipant> findByRideAndStatus(Ride ride, ParticipantStatus status);
}