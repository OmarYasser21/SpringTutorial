package com.example.tutorial.CenterManagement;


import com.example.tutorial.CenterManagement.dtos.CourseDTOProjection;
import com.example.tutorial.CenterManagement.dtos.StudentDTO;
import com.example.tutorial.CenterManagement.entities.Course;
import com.example.tutorial.CenterManagement.entities.Student;
import com.example.tutorial.CenterManagement.mappers.StudentMapper;
import com.example.tutorial.CenterManagement.repositories.CourseRepo;
import com.example.tutorial.CenterManagement.repositories.StudentRepo;
import com.example.tutorial.CenterManagement.services.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private CourseRepo courseRepo;

    @InjectMocks
    private StudentService studentService;

    @Test
    public void testGetAllStudents() {
        // Arrange
        List<Student> students = Arrays.asList(
                new Student(UUID.randomUUID(), "Student1", null, 21, null, null, null, null, null),
                new Student(UUID.randomUUID(), "Student2", null, 22, null, null, null, null, null)
        );
        // Use doReturn for the stubbing
        doReturn(students).when(studentRepo).findAll();


        // Map the expected courses to CourseDTO
        List<StudentDTO> studentDTOS = Arrays.asList(
                new StudentDTO(UUID.randomUUID(), "Student1", null, 21, null, null, null, null, null),
                new StudentDTO(UUID.randomUUID(), "Student2", null, 21, null, null, null, null, null)
        );

        doReturn(studentDTOS).when(studentMapper).toDTOs(students);

        // Act
        List<StudentDTO> result = studentService.getAllStudents();


        // Assert
        assertEquals(2, result.size());
        assertEquals(studentDTOS, result);
    }

    @Test
    public void testGetStudentById() {
        // Arrange
        Student student = new Student(UUID.randomUUID(), "Student1", null, 21, null, null, null, null, null);
        doReturn(Optional.of(student)).when(studentRepo).findById(student.getId());

        StudentDTO studentDTO = new StudentDTO(UUID.randomUUID(), student.getFirstName(), student.getLastName(), student.getAge(), student.getGender(), student.getEmail(), student.getPhoneNumber(), student.getNationalId(), null);

        doReturn(studentDTO).when(studentMapper).toDTO(student);


        // Act
        StudentDTO result = studentService.getStudentById(student.getId());

        // Assert
        assertEquals(studentDTO, result);
    }

    @Test
    public void testGetInstructorDetailsByIdNullResult() {
        // Arrange
        UUID id = UUID.randomUUID();
        // Mock the behavior of instructorDetailsRepo to return an empty Optional
        doReturn(Optional.empty()).when(studentRepo).findById(id);

        // Act
        StudentDTO result = studentService.getStudentById(id);

        // Assert
        assertNull(result);
    }

    @Test
    public void testSaveStudent() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO(UUID.randomUUID(), "Student1", null, 21, null, null, null, null, null);
        Student student = new Student(UUID.randomUUID(), "Student1", null, 21, null, null, null, null, null);

        doReturn(student).when(studentMapper).toEntity(studentDTO);
        doReturn(student).when(studentRepo).save(student);
        doReturn(studentDTO).when(studentMapper).toDTO(student);

        // Act
        StudentDTO result = studentService.saveStudent(studentDTO);

        // Assert
        assertEquals(studentDTO, result);
    }

    @Test
    public void testUpdateStudent() {
        // Arrange
        UUID studentId = UUID.randomUUID();
        Student existingStudent = new Student(studentId, "Student1", null, 21, null, null, null, null, null);
        StudentDTO updatedStudentDTO = new StudentDTO(UUID.randomUUID(), "Student1", null, 21, null, null, null, null, null);
        doReturn(Optional.of(existingStudent)).when(studentRepo).findById(studentId);
        doReturn(existingStudent).when(studentRepo).save(existingStudent);
        doReturn(updatedStudentDTO).when(studentMapper).toDTO(existingStudent);

        // Act
        StudentDTO result = studentService.updateStudent(studentId, updatedStudentDTO);

        // Assert
        assertEquals(updatedStudentDTO, result);
    }

    @Test
    public void testUpdateStudentEntityNotFound() {
        // Arrange: Prepare the test data
        UUID nonExistentId = UUID.randomUUID(); // An ID that doesn't exist in the repository

        // Create an instance of updatedInstructorDetailsDTO with the necessary data
        StudentDTO updatedStudentDTO = new StudentDTO(UUID.randomUUID(), "Student1", null, 21, null, null, null, null, null);

        // Mock the instructorDetailsRepo to return an empty Optional when findById is called with nonExistentId
        doReturn(Optional.empty()).when(studentRepo).findById(nonExistentId);

        // Act and Assert: Use assertThrows to check if the expected exception is thrown
        assertThrows(EntityNotFoundException.class, () -> studentService.updateStudent(nonExistentId, updatedStudentDTO));
    }

    @Test
    public void testEnrollStudentInCourse() {
        // Arrange
        UUID courseId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();

        Course course = new Course(courseId, "Course1", null, null, null, false, null, null);
        Student student = new Student(studentId, "Student1", null, 21, null, null, null, null, null);
        student.setCourses(new HashSet<>());
        student.getCourses().add(course);

        CourseDTOProjection courseDTOProjection = new CourseDTOProjection(courseId, "Course1", null, null, null, false);
        StudentDTO studentDTO = new StudentDTO(UUID.randomUUID(), "Student1", null, 21, null, null, null, null, null);
        studentDTO.setCourses(new HashSet<>());
        studentDTO.getCourses().add(courseDTOProjection);

        // Mock the necessary dependencies
        doReturn(Optional.of(student)).when(studentRepo).findById(studentId);
        doReturn(Optional.of(course)).when(courseRepo).findById(courseId);
        doReturn(student).when(studentRepo).save(student);
        doReturn(studentDTO).when(studentMapper).toDTO(student);

        // Act
        StudentDTO result = studentService.enrollStudentInCourse(studentId, courseId);

        // Assert
        assertNotNull(result);
        assertEquals(course.getName(), result.getCourses().iterator().next().getName());

    }

    @Test
    public void testEnrollStudentInCourseStudentNotFound() {
        // Arrange
        UUID courseId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();

        doReturn(Optional.empty()).when(studentRepo).findById(studentId);

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> studentService.enrollStudentInCourse(studentId, courseId));
    }

    @Test
    public void testEnrollStudentInCourseCourseNotFound() {
        // Arrange
        UUID courseId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();

        doReturn(Optional.of(new Student())).when(studentRepo).findById(studentId);
        doReturn(Optional.empty()).when(courseRepo).findById(courseId);

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> studentService.enrollStudentInCourse(studentId, courseId));
    }
    @Test
    public void testDeleteStudent() {
        // Arrange
        UUID studentId = UUID.randomUUID();
        doNothing().when(studentRepo).deleteById(studentId);

        // Act
        studentService.deleteStudent(studentId);

        // No need to assert, we're testing the void method
    }
}
