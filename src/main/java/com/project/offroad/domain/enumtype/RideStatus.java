package com.project.offroad.domain.enumtype;

public enum RideStatus {
    DRAFT,          // created but not published
    PUBLISHED,      // visible & joinable
    IN_PROGRESS,    // ride has started
    COMPLETED,      // ride finished successfully
    CANCELLED       // cancelled by organizer/admin
}
