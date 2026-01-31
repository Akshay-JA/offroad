package com.project.offroad.domain.entity;

import com.project.offroad.domain.base.BaseEntity;
import com.project.offroad.domain.enumtype.ParticipantStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "ride_participants",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_ride_user",
                        columnNames = {"ride_id", "user_id"}
                )
        }
)
public class RideParticipant extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ride_id", nullable = false)
    private Ride ride;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ParticipantStatus status;
}
