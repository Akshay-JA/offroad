package com.project.offroad.model;

import com.project.offroad.enums.Difficulty;
import com.project.offroad.enums.TripStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String location;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    private TripStatus status;

    @Column(nullable = false)
    private int maxRiders;

    @Column(nullable = false)
    private int currentRiders;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by")
    private User createdBy;
}