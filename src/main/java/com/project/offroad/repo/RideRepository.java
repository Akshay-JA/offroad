package com.project.offroad.repo;

import com.project.offroad.domain.entity.Ride;
import com.project.offroad.domain.enumtype.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {

    List<Ride> findByStatus(RideStatus status);

    List<Ride> findByStartTimeAfterAndStatus(
            OffsetDateTime time,
            RideStatus status
    );
}
