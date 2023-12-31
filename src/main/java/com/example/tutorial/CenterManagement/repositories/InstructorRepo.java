package com.example.tutorial.CenterManagement.repositories;

import com.example.tutorial.CenterManagement.dtos.InstructorWithStudentsDTO;
import com.example.tutorial.CenterManagement.entities.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorRepo extends JpaRepository<Instructor, Integer> {
    @Query("SELECT NEW com.example.tutorial.CenterManagement.dtos.InstructorWithStudentsDTO(CONCAT(i.firstName, ' ', i.lastName), " +
            "LISTAGG(s.firstName || ' ' || s.lastName, ', ')) " +
            "FROM Instructor i " +
            "LEFT JOIN i.courses c " +
            "LEFT JOIN c.students s " +
            "GROUP BY i")
    List<InstructorWithStudentsDTO> getAllInstructorsWithStudents();

    Instructor findByPhoneNumber(String phoneNumber);
}
