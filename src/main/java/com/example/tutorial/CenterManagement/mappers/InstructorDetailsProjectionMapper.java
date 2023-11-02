package com.example.tutorial.CenterManagement.mappers;

import com.example.tutorial.CenterManagement.dtos.InstructorDetailsDTOProjection;
import com.example.tutorial.CenterManagement.entities.InstructorDetails;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface InstructorDetailsProjectionMapper {
    InstructorDetailsDTOProjection toDTO(InstructorDetails instructorDetails);
    InstructorDetails toEntity(InstructorDetailsDTOProjection instructorDetailsDTOProjection);
}
