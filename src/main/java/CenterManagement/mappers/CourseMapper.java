package CenterManagement.mappers;

import CenterManagement.dtos.CourseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;
import CenterManagement.entities.Course;

import java.util.List;
@Component
@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mappings({
            @Mapping(target = "instructor", source = "instructor"),
            @Mapping(target = "students", source = "students")
    })
    CourseDTO toDTO(Course course);
    @Mappings({
            @Mapping(target = "instructor", source = "instructor"),
            @Mapping(target = "students", source = "students")
    })
    Course toEntity(CourseDTO courseDTO);
    List<CourseDTO> toDTOs(List<Course> courses);
}
