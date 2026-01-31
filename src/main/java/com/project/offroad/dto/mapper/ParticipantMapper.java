package com.project.offroad.dto.mapper;


import com.project.offroad.domain.entity.RideParticipant;
import com.project.offroad.dto.response.ParticipantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.fullName")
    @Mapping(target = "status", expression = "java(rp.getStatus().name())")
    ParticipantResponse toResponse(RideParticipant rp);
}