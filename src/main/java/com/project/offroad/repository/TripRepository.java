package com.project.offroad.repository;

import com.project.offroad.enums.Difficulty;
import com.project.offroad.enums.TripStatus;
import com.project.offroad.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByStatus(TripStatus status);
    List<Trip> findByLocationContainingIgnoreCase(String location);
    List<Trip> findByDifficulty(Difficulty difficulty);
    List<Trip> findByCreatedBy_Id(Long userId);

}
