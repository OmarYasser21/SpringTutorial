package CenterManagement.mappers;

import CenterManagement.entities.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;
import CenterManagement.dtos.InstructorDTO;

import java.util.List;
@Component
@Mapper(componentModel = "spring")
public interface InstructorMapper {
    @Mappings({
            @Mapping(target = "instructorDetails", source = "instructorDetails"),
            @Mapping(target = "courses", source = "courses"),
    })
    InstructorDTO toDTO(Instructor instructor);

    @Mappings({
            @Mapping(target = "instructorDetails", source = "instructorDetails"),
            @Mapping(target = "courses", source = "courses"),
    })    Instructor toEntity(InstructorDTO instructorDTO);
    List<InstructorDTO> toDTOs(List<Instructor> instructors);
}
