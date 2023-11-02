package com.example.tutorial.CenterManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseWithStudentsAndStartDateDTO {
    private String courseName;
    private Timestamp startDate;
    private String enrolledStudents;
}
