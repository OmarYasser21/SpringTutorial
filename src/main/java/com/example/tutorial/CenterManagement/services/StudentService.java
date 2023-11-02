package com.example.tutorial.CenterManagement.services;

import com.example.tutorial.CenterManagement.entities.Course;
import com.example.tutorial.CenterManagement.repositories.CourseRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.tutorial.CenterManagement.dtos.StudentDTO;
import com.example.tutorial.CenterManagement.entities.Student;
import com.example.tutorial.CenterManagement.mappers.StudentMapper;
import com.example.tutorial.CenterManagement.repositories.StudentRepo;

import java.util.*;

@Service
public class StudentService {
    private final StudentRepo studentRepo;
    private final StudentMapper studentMapper;
    private final CourseRepo courseRepo;

    @Autowired
    public StudentService(StudentRepo studentRepo, StudentMapper studentMapper, CourseRepo courseRepo) {
        this.studentRepo = studentRepo;
        this.studentMapper = studentMapper;
        this.courseRepo = courseRepo;
    }

    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepo.findAll();
        return studentMapper.toDTOs(students);
    }

    public StudentDTO getStudentById(UUID id) {
        Student student = studentRepo.findById(id).orElse(null);
        if (student != null) {
            return studentMapper.toDTO(student);
        } else {
            return null;
        }
    }

    public StudentDTO saveStudent(StudentDTO studentDTO) {
        Student student = studentMapper.toEntity(studentDTO);
        Student savedStudent = studentRepo.save(student);
        return studentMapper.toDTO(savedStudent);
    }

    public StudentDTO updateStudent(UUID id, StudentDTO updatedStudentDTO) {
        Student existingStudent = studentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + id));

        existingStudent.setFirstName(updatedStudentDTO.getFirstName());
        existingStudent.setLastName(updatedStudentDTO.getLastName());
        existingStudent.setAge(updatedStudentDTO.getAge());
        existingStudent.setEmail(updatedStudentDTO.getEmail());
        existingStudent.setPhoneNumber(updatedStudentDTO.getPhoneNumber());
        existingStudent.setNationalId(updatedStudentDTO.getNationalId());
        existingStudent.setGender(updatedStudentDTO.getGender());

        Student updatedStudent = studentRepo.save(existingStudent);

        return studentMapper.toDTO(updatedStudent);
    }

    public StudentDTO enrollStudentInCourse(UUID studentId, UUID courseId) {
        // Retrieve the Student entity by ID
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));

        // Retrieve the Course entity by ID
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + courseId));

        // Enroll the student in the course (Add the course to the student's courses)
        student.getCourses().add(course);

        // Save the updated student
        Student enrolledStudent = studentRepo.save(student);

        return studentMapper.toDTO(enrolledStudent);
    }

    public void deleteStudent(UUID id) {
        studentRepo.deleteById(id);
    }
}
