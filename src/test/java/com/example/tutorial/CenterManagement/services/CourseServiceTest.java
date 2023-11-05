package com.example.tutorial.CenterManagement.services;

import com.example.tutorial.CenterManagement.dtos.*;
import com.example.tutorial.CenterManagement.entities.Course;
import com.example.tutorial.CenterManagement.entities.Instructor;
import com.example.tutorial.CenterManagement.entities.Student;
import com.example.tutorial.CenterManagement.mappers.CourseMapper;
import com.example.tutorial.CenterManagement.repositories.CourseRepo;
import com.example.tutorial.CenterManagement.repositories.InstructorRepo;
import com.example.tutorial.CenterManagement.services.CourseService;
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
public class CourseServiceTest {

    @Mock
    private CourseRepo courseRepo;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private InstructorRepo instructorRepo;

    @InjectMocks
    private CourseService courseService;

    @Test
    public void testGetAllCourses() {
        // Arrange
        List<Course> courses = Arrays.asList(
                new Course(UUID.randomUUID(), "Course1", null, null, null, false, null, null),
                new Course(UUID.randomUUID(), "Course2", null, null, null, false, null, null)
        );
        // Use doReturn for the stubbing
        doReturn(courses).when(courseRepo).findAll();


//        List<CourseDTO> expected = courses.stream()   null
//                .map(courseMapper::toDTO)
//                .collect(Collectors.toList());

        // Map the expected courses to CourseDTO
        List<CourseDTO> courseDTOS = Arrays.asList(
                new CourseDTO(UUID.randomUUID(), "Course1", null, null, null, false, null, null),
                new CourseDTO(UUID.randomUUID(), "Course2", null, null, null, false, null, null)
        );

        doReturn(courseDTOS).when(courseMapper).toDTOs(courses);

        // Act
        List<CourseDTO> result = courseService.getAllCourses();


        // Assert
        assertEquals(2, result.size());
        assertEquals(courseDTOS, result);
    }

    @Test
    public void testGetCourseById() {
        // Arrange
        Course course = new Course(UUID.randomUUID(), "Course1", null, null, null, false, null, null);
        doReturn(Optional.of(course)).when(courseRepo).findById(course.getId());

        CourseDTO courseDTO = new CourseDTO(course.getId(), course.getName(), course.getStartDate(), course.getEndDate(), course.getLevel(), course.isStarted(), null, null);

        doReturn(courseDTO).when(courseMapper).toDTO(course);


        // Act
        CourseDTO result = courseService.getCourseById(course.getId());

        // Assert
        assertEquals(courseDTO, result);
    }

    @Test
    public void testGetCourseByIdNullResult() {
        // Arrange
        UUID id = UUID.randomUUID();
        // Mock the behavior of instructorDetailsRepo to return an empty Optional
        doReturn(Optional.empty()).when(courseRepo).findById(id);

        // Act
        CourseDTO result = courseService.getCourseById(id);

        // Assert
        assertNull(result);
    }

    @Test
    public void testSaveCourse() {
        // Arrange
        CourseDTO courseDTO = new CourseDTO(UUID.randomUUID(), "Course1", null, null, null, false, null, null);
        Course course = new Course(UUID.randomUUID(), "Course1", null, null, null, false, null, null);

        doReturn(course).when(courseMapper).toEntity(courseDTO);
        doReturn(course).when(courseRepo).save(course);
        doReturn(courseDTO).when(courseMapper).toDTO(course);

        // Act
        CourseDTO result = courseService.saveCourse(courseDTO);

        // Assert
        assertEquals(courseDTO, result);
    }

    @Test
    public void testUpdateCourse() {
        // Arrange
        UUID courseId = UUID.randomUUID();
        Course existingCourse = new Course(courseId, "Course1", null, null, null, false, null, null);
        CourseDTO updatedCourseDTO = new CourseDTO(UUID.randomUUID(), "Course2", null, null, null, false, null, null);
        doReturn(Optional.of(existingCourse)).when(courseRepo).findById(courseId);
        doReturn(existingCourse).when(courseRepo).save(existingCourse);
        doReturn(updatedCourseDTO).when(courseMapper).toDTO(existingCourse);

        // Act
        CourseDTO result = courseService.updateCourse(courseId, updatedCourseDTO);

        // Assert
        assertEquals(updatedCourseDTO, result);
    }

    @Test
    public void testUpdateCourseEntityNotFound() {
        // Arrange: Prepare the test data
        UUID nonExistentId = UUID.randomUUID(); // An ID that doesn't exist in the repository

        // Create an instance of updatedInstructorDetailsDTO with the necessary data
        CourseDTO updatedCourseDTO = new CourseDTO(UUID.randomUUID(), "Course2", null, null, null, false, null, null);

        // Mock the instructorDetailsRepo to return an empty Optional when findById is called with nonExistentId
        doReturn(Optional.empty()).when(courseRepo).findById(nonExistentId);

        // Act and Assert: Use assertThrows to check if the expected exception is thrown
        assertThrows(EntityNotFoundException.class, () -> courseService.updateCourse(nonExistentId, updatedCourseDTO));
    }

    @Test
    public void testAssignInstructorToCourse() {
        // Arrange
        UUID courseId = UUID.randomUUID();
        int instructorId = 1;
        Course course = new Course(courseId, "Course1", null, null, null, false, null, null);
        Instructor instructor = new Instructor(instructorId, "Mohamed", "Ahmed", null, null, null, null, null);
        course.setInstructor(instructor);
        CourseDTO courseDTO = new CourseDTO(courseId, "Course1", null, null, null, false, new InstructorDTOProjection(instructorId, "Mohamed", "Ahmed", null, null, null), null);

        doReturn(Optional.of(course)).when(courseRepo).findById(courseId);
        doReturn(Optional.of(instructor)).when(instructorRepo).findById(instructorId);
        doReturn(course).when(courseRepo).save(course);
        doReturn(courseDTO).when(courseMapper).toDTO(course);

        // Act
        CourseDTO result = courseService.assignInstructorToCourse(courseId, instructorId);

        // Assert
        assertNotNull(result);
        assertEquals(instructor.getFirstName(), result.getInstructor().getFirstName());
        assertEquals(instructor.getLastName(), result.getInstructor().getLastName());
    }

    @Test
    public void testAssignInstructorToCourseCourseNotFound() {
        // Arrange
        UUID courseId = UUID.randomUUID();
        int instructorId = 1;

        doReturn(Optional.empty()).when(courseRepo).findById(courseId);

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> courseService.assignInstructorToCourse(courseId, instructorId));
    }

    @Test
    public void testAssignInstructorToCourseInstructorNotFound() {
        // Arrange
        UUID courseId = UUID.randomUUID();
        int instructorId = 1;

        doReturn(Optional.of(new Course())).when(courseRepo).findById(courseId);
        doReturn(Optional.empty()).when(instructorRepo).findById(instructorId);

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> courseService.assignInstructorToCourse(courseId, instructorId));
    }

    @Test
    public void testGetAllCoursesWithStudents() {
        // Arrange
        List<CourseWithStudentsAndStartDateDTO> expected = Arrays.asList(
                new CourseWithStudentsAndStartDateDTO("Course1", null, "Ali, Ahmed"),
                new CourseWithStudentsAndStartDateDTO("Course2", null,"Omar, Mohamed")
        );
        doReturn(expected).when(courseRepo).getCoursesWithStudents();

        // Act
        List<CourseWithStudentsAndStartDateDTO> result = courseService.getAllCoursesWithStudents();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void testGetIntermediateCoursesWithStudents() {
        // Arrange
        // Create sample courses with associated students
        Course course1 = new Course(UUID.randomUUID(), "IntermediateCourse1", null, null, null, false, null, new HashSet<>(Arrays.asList(
                new Student(UUID.randomUUID(), "Student1", "Lastname1", 21, null, null, null, null, null),
                new Student(UUID.randomUUID(), "Student2", "Lastname2", 22, null, null, null, null, null)
        )));

        Course course2 = new Course(UUID.randomUUID(), "IntermediateCourse2", null, null, null, false, null, new HashSet<>(Collections.singletonList(
                new Student(UUID.randomUUID(), "Student3", "Lastname3", 23, null, null, null, null, null)
        )));

        // Mock the courseRepo's findByLevel method to return the sample courses
        doReturn(Arrays.asList(course1, course2)).when(courseRepo).findByLevel();

        // Act
        List<CourseWithStudentsDTO> result = courseService.getIntermediateCoursesWithStudents();

        // Assert
        // Verify that there are 2 results
        assertEquals(2, result.size());

        // Check if the result contains the expected data
        assertTrue(result.stream().anyMatch(dto ->
                dto.getCourseName().equals("IntermediateCourse1") &&
                        dto.getEnrolledStudents().containsAll(Arrays.asList("Student1 Lastname1", "Student2 Lastname2"))
        ));

        assertTrue(result.stream().anyMatch(dto ->
                dto.getCourseName().equals("IntermediateCourse2") &&
                        dto.getEnrolledStudents().contains("Student3 Lastname3")
        ));
    }


    @Test
    public void testDeleteCourse() {
        // Arrange
        UUID courseId = UUID.randomUUID();
        doNothing().when(courseRepo).deleteById(courseId);

        // Act
        courseService.deleteCourse(courseId);

        // No need to assert, we're testing the void method
    }
}
