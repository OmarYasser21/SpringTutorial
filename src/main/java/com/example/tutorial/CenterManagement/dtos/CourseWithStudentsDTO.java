package com.example.tutorial.CenterManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseWithStudentsDTO {
    private String courseName;
    private List<String> enrolledStudents;
}
