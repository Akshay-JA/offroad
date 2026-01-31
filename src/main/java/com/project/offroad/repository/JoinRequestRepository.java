package com.project.offroad.repository;

import com.project.offroad.enums.JoinStatus;
import com.project.offroad.model.JoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    Optional<JoinRequest> findByUser_IdAndTrip_Id(Long userId, Long tripId);
    List<JoinRequest> findByTrip_Id(Long tripId);
    List<JoinRequest> findByTrip_IdAndStatus(Long tripId, JoinStatus status);
    List<JoinRequest> findByUser_Id(Long userId);
}
