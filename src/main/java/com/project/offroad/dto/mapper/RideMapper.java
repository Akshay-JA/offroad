package com.project.offroad.dto.mapper;


import com.project.offroad.domain.entity.Location;
import com.project.offroad.domain.entity.Ride;
import com.project.offroad.dto.request.RideCreateRequest;
import com.project.offroad.dto.response.RideResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RideMapper {

    // ---------- REQUEST → ENTITY ----------

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "organizer", ignore = true)
    @Mapping(target = "currentParticipants", ignore = true)
    @Mapping(target = "startLocation", expression = "java(toStartLocation(req))")
    @Mapping(target = "endLocation", expression = "java(toEndLocation(req))")
    Ride toEntity(RideCreateRequest req);

    // ---------- ENTITY → RESPONSE ----------

    @Mapping(target = "startCity", source = "startLocation.city")
    @Mapping(target = "startState", source = "startLocation.state")
    @Mapping(target = "startCountry", source = "startLocation.country")
    @Mapping(target = "endCity", source = "endLocation.city")
    @Mapping(target = "endState", source = "endLocation.state")
    @Mapping(target = "endCountry", source = "endLocation.country")
    @Mapping(target = "status", expression = "java(ride.getStatus().name())")
    RideResponse toResponse(Ride ride);

    // ---------- HELPERS ----------

    default Location toStartLocation(RideCreateRequest req) {
        Location loc = new Location();
        loc.setCity(req.startCity());
        loc.setState(req.startState());
        loc.setCountry(req.startCountry());
        return loc;
    }

    default Location toEndLocation(RideCreateRequest req) {
        Location loc = new Location();
        loc.setCity(req.endCity());
        loc.setState(req.endState());
        loc.setCountry(req.endCountry());
        return loc;
    }
}