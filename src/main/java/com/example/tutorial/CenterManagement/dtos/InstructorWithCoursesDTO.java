package com.example.tutorial.CenterManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InstructorWithCoursesDTO {
    private String instructorName;
    private List<String> courses;
}
