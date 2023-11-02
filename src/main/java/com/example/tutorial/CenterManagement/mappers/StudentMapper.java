package com.example.tutorial.CenterManagement.mappers;

import com.example.tutorial.CenterManagement.dtos.StudentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import com.example.tutorial.CenterManagement.entities.Student;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(target = "courses", source = "courses")
    StudentDTO toDTO(Student student);

    @Mapping(target = "courses", source = "courses")
    Student toEntity(StudentDTO studentDTO);
    List<StudentDTO> toDTOs(List<Student> students);
}
