package com.example.tutorial.CenterManagement.repositories;

import com.example.tutorial.CenterManagement.dtos.CourseWithStudentsAndStartDateDTO;
import com.example.tutorial.CenterManagement.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


import java.util.UUID;
@Repository
public interface CourseRepo extends JpaRepository<Course, UUID> {
    @Query("SELECT c FROM Course c WHERE c.level = 'intermediate'")
    List<Course> findByLevel();

    @Query(" SELECT\n" +
            "             new com.example.tutorial.CenterManagement.dtos.CourseWithStudentsAndStartDateDTO(c.name, c.startDate, LISTAGG(s.firstName || ' ' || s.lastName, ', '))\n" +
            "             FROM Course c LEFT JOIN c.students s\n" +
            "             GROUP BY c")
    List<CourseWithStudentsAndStartDateDTO> getCoursesWithStudents();
}
