package com.example.tutorial.CenterManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.tutorial.CenterManagement.entities.Student;

import java.util.UUID;
@Repository
public interface StudentRepo extends JpaRepository<Student, UUID> {
}
