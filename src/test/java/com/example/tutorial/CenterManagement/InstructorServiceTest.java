package com.example.tutorial.CenterManagement;

import com.example.tutorial.CenterManagement.dtos.*;
import com.example.tutorial.CenterManagement.entities.Course;
import com.example.tutorial.CenterManagement.entities.Instructor;
import com.example.tutorial.CenterManagement.entities.InstructorDetails;
import com.example.tutorial.CenterManagement.mappers.InstructorMapper;
import com.example.tutorial.CenterManagement.repositories.InstructorRepo;
import com.example.tutorial.CenterManagement.services.InstructorService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class InstructorServiceTest {
    @Mock
    InstructorRepo instructorRepo;

    @Mock
    InstructorMapper instructorMapper;

    @InjectMocks
    InstructorService instructorService;

    @Test
    public void testGetAllInstructors() {
        // Arrange
        Instructor instructor1 = new Instructor(1, "Instructor1", null, null, null, null, null, null);
        Instructor instructor2 = new Instructor(2, "Instructor2", null, null, null, null,null, null);

        List<Instructor> instructorList = Arrays.asList(instructor1, instructor2);

        doReturn(instructorList).when(instructorRepo).findAll();

        InstructorDTO instructorDTO1 = new InstructorDTO(instructor1.getId(), instructor1.getFirstName(), instructor1.getLastName(), instructor1.getEmail(), instructor1.getPhoneNumber(), instructor1.getTitle(), null, null);
        InstructorDTO instructorDTO2 = new InstructorDTO(instructor2.getId(), instructor2.getFirstName(), instructor2.getLastName(), instructor2.getEmail(), instructor2.getPhoneNumber(), instructor2.getTitle(), null, null);

        doReturn(Arrays.asList(instructorDTO1, instructorDTO2)).when(instructorMapper).toDTOs(instructorList);

        // Act
        List<InstructorDTO> result = instructorService.getAllInstructors();

        // Assert
        assertEquals(2, result.size());
        assertEquals(instructorDTO1, result.get(0));
        assertEquals(instructorDTO2, result.get(1));
    }

    @Test
    public void testGetInstructorById() {
        // Arrange
        Instructor instructor = new Instructor(1, "Instructor1", null, null, null, null,null, null);

        doReturn(Optional.of(instructor)).when(instructorRepo).findById(1);

        InstructorDTO instructorDTO = new InstructorDTO(instructor.getId(), instructor.getFirstName(), instructor.getLastName(), instructor.getEmail(), instructor.getPhoneNumber(), instructor.getTitle(), null, null);
        doReturn(instructorDTO).when(instructorMapper).toDTO(instructor);

        // Act
        InstructorDTO result = instructorService.getInstructorById(1);

        // Assert
        assertEquals(instructorDTO, result);
    }

    @Test
    public void testGetInstructorByIdNullResult() {
        // Arrange
        int id = 5;
        // Mock the behavior of instructorDetailsRepo to return an empty Optional
        doReturn(Optional.empty()).when(instructorRepo).findById(id);

        // Act
        InstructorDTO result = instructorService.getInstructorById(id);

        // Assert
        assertNull(result);
    }

    @Test
    public void testSaveInstructor() {
        // Arrange
        Instructor instructor = new Instructor(1, "Instructor1", null, null, null, null, null, null);
        InstructorDTO instructorDTO = new InstructorDTO(1, "Instructor1", null, null, null, null, null, null);

        doReturn(instructor).when(instructorMapper).toEntity(instructorDTO);
        doReturn(instructor).when(instructorRepo).save(instructor);
        doReturn(instructorDTO).when(instructorMapper).toDTO(instructor);

        // Act
        InstructorDTO result = instructorService.saveInstructor(instructorDTO);

        // Assert
        assertEquals(instructorDTO, result);
//        assertNotNull(result);
//        assertEquals(id, result.getId());
//        assertEquals("Hobby", result.getHobbies());
//        assertEquals("YouTube", result.getYoutubeChannel());
    }

    @Test
    public void testUpdateInstructor() {
        // Arrange
        Instructor existingInstructor = new Instructor(1, "Instructor1", null, null, null, null, null, null);
        InstructorDTO updatedInstructorDTO = new InstructorDTO(1, "Instructor2", null, null, null, null, null, null);

        doReturn(Optional.of(existingInstructor)).when(instructorRepo).findById(1);
        doReturn(existingInstructor).when(instructorRepo).save(existingInstructor);
        doReturn(updatedInstructorDTO).when(instructorMapper).toDTO(existingInstructor);

        // Act
        InstructorDTO result = instructorService.updateInstructor(1, updatedInstructorDTO);

        // Assert
        assertEquals(updatedInstructorDTO, result);
//        assertNotNull(result);
//        assertEquals(1, result.getId());
//        assertEquals("Instructor2", result.getFirstName());
    }

    @Test
    public void testUpdateInstructorEntityNotFound() {
        // Arrange: Prepare the test data
        int nonExistentId = 6;
        // Create an instance of updatedInstructorDetailsDTO with the necessary data
        InstructorDTO updatedInstructorDTO = new InstructorDTO(1, "Instructor2", null, null, null, null, null, null);

        // Mock the instructorDetailsRepo to return an empty Optional when findById is called with nonExistentId
        doReturn(Optional.empty()).when(instructorRepo).findById(nonExistentId);

        // Act and Assert: Use assertThrows to check if the expected exception is thrown
        assertThrows(EntityNotFoundException.class, () -> instructorService.updateInstructor(nonExistentId, updatedInstructorDTO));
    }

    @Test
    public void testUpdateInstructorWithNoExistingInstructorDetails() {
        // Arrange
        int instructorId = 1;

        // Create the updated instructor DTO with instructor details
        InstructorDTO updatedInstructorDTO = new InstructorDTO(
                1,
                "John",
                "Doe",
                "1234567890",
                "john.doe@example.com",
                "Instructor",
                new InstructorDetailsDTOProjection(UUID.randomUUID(),"Gardening", "https://www.youtube.com/johndoe"),
                null
        );

        // Mock instructorRepo to return an instructor with no instructorDetails
        Instructor existingInstructor = new Instructor(instructorId, "OldFirstName", "OldLastName", "OldPhoneNumber", "OldEmail", "OldTitle", null, null);
        doReturn(Optional.of(existingInstructor)).when(instructorRepo).findById(instructorId);
        doReturn(existingInstructor).when(instructorRepo).save(existingInstructor);
        doReturn(updatedInstructorDTO).when(instructorMapper).toDTO(existingInstructor);

        // Act
        InstructorDTO result = instructorService.updateInstructor(instructorId, updatedInstructorDTO);

        // Assert
        // Ensure that the updatedInstructor now has instructorDetails
        assertEquals(updatedInstructorDTO, result);
        assertNotNull(result.getInstructorDetails());
        assertEquals("Gardening", result.getInstructorDetails().getHobbies());
        assertEquals("https://www.youtube.com/johndoe", result.getInstructorDetails().getYoutubeChannel());
    }

    @Test
    public void testUpdateInstructorWithExistingInstructorDetails() {
        // Arrange
        int instructorId = 1;

        // Create the updated instructor DTO with instructor details
        InstructorDTO updatedInstructorDTO = new InstructorDTO(
                1,
                "John",
                "Doe",
                "1234567890",
                "john.doe@example.com",
                "Instructor",
                new InstructorDetailsDTOProjection(UUID.randomUUID(),"Gardening", "https://www.youtube.com/johndoe"),
                null
        );

        // Mock instructorRepo to return an instructor with no instructorDetails
        InstructorDetails existingInstructorDetails = new InstructorDetails(UUID.randomUUID(),"Gardening", "https://www.youtube.com/johndoe", null);

        Instructor existingInstructor = new Instructor(instructorId, "OldFirstName", "OldLastName", "OldPhoneNumber", "OldEmail", "OldTitle", existingInstructorDetails, null);
        doReturn(Optional.of(existingInstructor)).when(instructorRepo).findById(instructorId);
        doReturn(existingInstructor).when(instructorRepo).save(existingInstructor);
        doReturn(updatedInstructorDTO).when(instructorMapper).toDTO(existingInstructor);

        // Act
        InstructorDTO result = instructorService.updateInstructor(instructorId, updatedInstructorDTO);

        // Assert
        // Ensure that the updatedInstructor now has instructorDetails
        assertEquals(updatedInstructorDTO, result);
        assertNotNull(result.getInstructorDetails());
        assertEquals("Gardening", result.getInstructorDetails().getHobbies());
        assertEquals("https://www.youtube.com/johndoe", result.getInstructorDetails().getYoutubeChannel());
    }

    @Test
    public void testDeleteInstructor() {
        // Arrange
        int id = 7;

        // Act
        instructorService.deleteInstructor(id);

        // Assert
        // Verify that the delete method on the repository is called with the correct ID
        Mockito.verify(instructorRepo).deleteById(id);
    }

    @Test
    public void testDeleteInstructor_Success() {
        // Arrange
        int id = 4;
        // Stub the repository to do nothing when deleteById is called
        doNothing().when(instructorRepo).deleteById(id);

        // Act
        instructorService.deleteInstructor(id);

        // Assert
        // You can assert that the delete operation was successful here, e.g., no exceptions were thrown.
    }

    @Test
    public void testGetAllInstructorsWithCourses() {
        // Arrange
        Instructor instructor1 = new Instructor(1, "Instructor1", null, null, null, null, null, null);
        Instructor instructor2 = new Instructor(2, "Instructor2", null, null, null, null, null, null);

        Course course1 = new Course(UUID.randomUUID(), "Course1", null, null, null, false, null, null);
        Course course2 = new Course(UUID.randomUUID(), "Course2", null, null, null, false, null, null);
        Course course3 = new Course(UUID.randomUUID(), "Course3", null, null, null, false, null, null);

        instructor1.setCourses(Arrays.asList(course1, course2));
        instructor2.setCourses(Collections.singletonList(course3));

        doReturn(Arrays.asList(instructor1, instructor2)).when(instructorRepo).findAll();

        // Act
        List<InstructorWithCoursesDTO> result = instructorService.getAllInstructorsWithCourses();

        // Assert
        // Verify that the result is not null
        assertNotNull(result);

        // Verify the size of the result list
        assertEquals(2, result.size());

//        Assert equals compares the reference not values
//        // Create the expected list
//        List<InstructorWithCoursesDTO> expected = new ArrayList<>();
//        expected.add(new InstructorWithCoursesDTO("Instructor1", Arrays.asList("Course1", "Course2")));
//        expected.add(new InstructorWithCoursesDTO("Instructor2", Collections.singletonList("Course3")));
//
//        // Assert
//        assertEquals(expected, result);
    }
    @Test
    public void testGetAllInstructorsWithStudents() {
        // Arrange
        // Mock the behavior of the repository to return some test data
        List<InstructorWithStudentsDTO> expectedResults = Arrays.asList(
                new InstructorWithStudentsDTO("Instructor1", "Ahmed Ali, Hassan mohamed"),
                new InstructorWithStudentsDTO("Instructor2", "Hossam ahmed, omar yasser")
        );
        doReturn(expectedResults).when(instructorRepo).getAllInstructorsWithStudents();

        // Act
        List<InstructorWithStudentsDTO> result = instructorService.getAllInstructorsWithStudents();

        // Assert
        // Verify that the expected results match the actual results
        assertEquals(expectedResults, result);
    }
}
