package CenterManagement.mappers;

import CenterManagement.dtos.InstructorDetailsDTOProjection;
import CenterManagement.entities.InstructorDetails;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface InstructorDetailsProjectionMapper {
    InstructorDetailsDTOProjection toDTO(InstructorDetails instructorDetails);
    InstructorDetails toEntity(InstructorDetailsDTOProjection instructorDetailsDTOProjection);
}
