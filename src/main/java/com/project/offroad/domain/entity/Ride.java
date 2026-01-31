package com.project.offroad.domain.entity;

import com.project.offroad.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "rides",
        indexes = {
                @Index(name = "idx_rides_start_time", columnList = "startTime")
        }
)
public class Ride extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String title;

    @Column(length = 500)
    private String description;

    @Embedded
    private Location startLocation;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "end_city")),
            @AttributeOverride(name = "state", column = @Column(name = "end_state")),
            @AttributeOverride(name = "country", column = @Column(name = "end_country")),
            @AttributeOverride(name = "latitude", column = @Column(name = "end_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "end_longitude"))
    })
    private Location endLocation;

    @Column(nullable = false)
    private Integer maxParticipants;

    @Column(nullable = false)
    private Integer currentParticipants = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RideStatus status;

    @Column(nullable = false)
    private OffsetDateTime startTime;

    @Column(nullable = false)
    private OffsetDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User organizer;
}
