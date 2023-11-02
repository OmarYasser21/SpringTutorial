package com.example.tutorial.CenterManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorWithStudentsDTO {
    private String instructorName;
    private String studentNames;
}
