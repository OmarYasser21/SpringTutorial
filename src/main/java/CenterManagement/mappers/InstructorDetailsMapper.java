package CenterManagement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;
import CenterManagement.dtos.InstructorDetailsDTO;
import CenterManagement.entities.InstructorDetails;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface InstructorDetailsMapper {
    @Mappings({
            @Mapping(target = "instructor", source = "instructor"),
    })
    InstructorDetailsDTO toDTO(InstructorDetails instructorDetails);
    @Mappings({
            @Mapping(target = "instructor", source = "instructor"),
    })
    InstructorDetails toEntity(InstructorDetailsDTO instructorDetailsDTO);
    List<InstructorDetailsDTO> toDTOs(List<InstructorDetails> instructorDetails);
}
