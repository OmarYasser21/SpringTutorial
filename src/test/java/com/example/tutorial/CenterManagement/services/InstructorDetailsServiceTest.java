package com.example.tutorial.CenterManagement.services;

import com.example.tutorial.CenterManagement.dtos.InstructorDTOProjection;
import com.example.tutorial.CenterManagement.dtos.InstructorDetailsDTO;
import com.example.tutorial.CenterManagement.entities.Instructor;
import com.example.tutorial.CenterManagement.entities.InstructorDetails;
import com.example.tutorial.CenterManagement.mappers.InstructorDetailsMapper;
import com.example.tutorial.CenterManagement.repositories.InstructorDetailsRepo;
import com.example.tutorial.CenterManagement.services.InstructorDetailsService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
@ExtendWith(MockitoExtension.class)
public class InstructorDetailsServiceTest {

    @Mock
    private InstructorDetailsRepo instructorDetailsRepo;

    @Mock
    private InstructorDetailsMapper instructorDetailsMapper;

    @InjectMocks
    private InstructorDetailsService instructorDetailsService;


    @Test
    public void testGetAllInstructorDetails() {
        // Arrange
        Instructor instructor1 = new Instructor(1, "Instructor1", null, null, null, null, null, null);
        InstructorDetails instructorDetails1 = new InstructorDetails(UUID.randomUUID(), "Hobby1", "YouTube1", instructor1);
        Instructor instructor2 = new Instructor(2, "Instructor2", null, null, null, null, null, null);
        InstructorDetails instructorDetails2 = new InstructorDetails(UUID.randomUUID(), "Hobby2", "YouTube2", instructor2);

        List<InstructorDetails> instructorList = Arrays.asList(instructorDetails1, instructorDetails2);

       doReturn(instructorList).when(instructorDetailsRepo).findAll();

        InstructorDetailsDTO instructorDTO1 = new InstructorDetailsDTO(instructorDetails1.getId(), instructorDetails1.getHobbies(), instructorDetails1.getYoutubeChannel(), new InstructorDTOProjection(1, "Instructor1", null, null, null, null));
        InstructorDetailsDTO instructorDTO2 = new InstructorDetailsDTO(instructorDetails2.getId(), instructorDetails2.getHobbies(), instructorDetails2.getYoutubeChannel(), new InstructorDTOProjection(2, "Instructor2", null, null, null, null));

        doReturn(Arrays.asList(instructorDTO1, instructorDTO2)).when(instructorDetailsMapper).toDTOs(instructorList);

        // Act
        List<InstructorDetailsDTO> result = instructorDetailsService.getAllInstructorDetails();

        // Assert
        assertEquals(2, result.size());
        assertEquals(instructorDTO1, result.get(0));
        assertEquals(instructorDTO2, result.get(1));
    }

    @Test
    public void testGetInstructorDetailsById() {
        // Arrange
        UUID id = UUID.randomUUID();
        Instructor instructor1 = new Instructor(1, "Instructor1", null, null, null, null, null, null);
        InstructorDetails instructorDetails = new InstructorDetails(id, "Hobby1", "YouTube1", instructor1);

        doReturn(Optional.of(instructorDetails)).when(instructorDetailsRepo).findById(id);

        InstructorDetailsDTO instructorDTO = new InstructorDetailsDTO(instructorDetails.getId(), instructorDetails.getHobbies(), instructorDetails.getYoutubeChannel(),  new InstructorDTOProjection(1, "Instructor1", null, null, null, null));
        doReturn(instructorDTO).when(instructorDetailsMapper).toDTO(instructorDetails);

        // Act
        InstructorDetailsDTO result = instructorDetailsService.getInstructorDetailsById(id);

        // Assert
        assertEquals(instructorDTO, result);
    }

    @Test
    public void testGetInstructorDetailsByIdNullResult() {
        // Arrange
        UUID id = UUID.randomUUID();
        // Mock the behavior of instructorDetailsRepo to return an empty Optional
        doReturn(Optional.empty()).when(instructorDetailsRepo).findById(id);

        // Act
        InstructorDetailsDTO result = instructorDetailsService.getInstructorDetailsById(id);

        // Assert
        assertNull(result);
    }

    @Test
    public void testSaveInstructorDetails() {
        // Arrange
        UUID id = UUID.randomUUID();
        InstructorDetailsDTO instructorDetailsDTO = new InstructorDetailsDTO(id, "Hobby", "YouTube", new InstructorDTOProjection(1, "Instructor", null, null, null, null));
        InstructorDetails instructorDetails = new InstructorDetails(id, "Hobby", "YouTube", new Instructor(1, "Instructor", null, null, null, null, null, null));

        doReturn(instructorDetails).when(instructorDetailsMapper).toEntity(instructorDetailsDTO);
        doReturn(instructorDetails).when(instructorDetailsRepo).save(instructorDetails);
        doReturn(instructorDetailsDTO).when(instructorDetailsMapper).toDTO(instructorDetails);

        // Act
        InstructorDetailsDTO result = instructorDetailsService.saveInstructorDetails(instructorDetailsDTO);

        // Assert
        assertEquals(instructorDetailsDTO, result);
//        assertNotNull(result);
//        assertEquals(id, result.getId());
//        assertEquals("Hobby", result.getHobbies());
//        assertEquals("YouTube", result.getYoutubeChannel());
    }

    @Test
    public void testUpdateInstructorDetails() {
        // Arrange
        UUID id = UUID.randomUUID();
        InstructorDetailsDTO updatedInstructorDetailsDTO = new InstructorDetailsDTO(id, "New Hobby", "New YouTube", new InstructorDTOProjection(1, "Instructor", null, null, null, null));
        InstructorDetails existingInstructorDetails = new InstructorDetails(id, "Hobby", "YouTube", new Instructor(1, "Instructor", null, null, null, null, null, null));

        doReturn(Optional.of(existingInstructorDetails)).when(instructorDetailsRepo).findById(id);
        doReturn(existingInstructorDetails).when(instructorDetailsRepo).save(existingInstructorDetails);
        doReturn(updatedInstructorDetailsDTO).when(instructorDetailsMapper).toDTO(existingInstructorDetails);

        // Act
        InstructorDetailsDTO result = instructorDetailsService.updateInstructorDetails(id, updatedInstructorDetailsDTO);

        // Assert
        assertEquals(updatedInstructorDetailsDTO, result);
//        assertNotNull(result);
//        assertEquals(id, result.getId());
//        assertEquals("New Hobby", result.getHobbies());
//        assertEquals("New YouTube", result.getYoutubeChannel());
    }

    @Test
    public void testUpdateInstructorDetailsEntityNotFound() {
        // Arrange: Prepare the test data
        UUID nonExistentId = UUID.randomUUID(); // An ID that doesn't exist in the repository

        // Create an instance of updatedInstructorDetailsDTO with the necessary data
        InstructorDetailsDTO updatedInstructorDetailsDTO = new InstructorDetailsDTO(UUID.randomUUID(), "New Hobbies", "New Youtube Channel", null);

        // Mock the instructorDetailsRepo to return an empty Optional when findById is called with nonExistentId
        doReturn(Optional.empty()).when(instructorDetailsRepo).findById(nonExistentId);

        // Act and Assert: Use assertThrows to check if the expected exception is thrown
        assertThrows(EntityNotFoundException.class, () -> instructorDetailsService.updateInstructorDetails(nonExistentId, updatedInstructorDetailsDTO));
    }
    @Test
    public void testDeleteInstructorDetails() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        instructorDetailsService.deleteInstructorDetails(id);

        // Assert
        // Verify that the delete method on the repository is called with the correct ID
        Mockito.verify(instructorDetailsRepo).deleteById(id);
    }

    @Test
    public void testDeleteInstructorDetails_Success() {
        // Arrange
        UUID id = UUID.randomUUID();
        Instructor instructor1 = new Instructor(1, "Instructor1", null, null, null, null, null, null);
        InstructorDetails instructorDetails = new InstructorDetails(id, "Hobby1", "YouTube1", instructor1);

        // Stub the repository to do nothing when deleteById is called
        doNothing().when(instructorDetailsRepo).deleteById(id);

        // Act
        instructorDetailsService.deleteInstructorDetails(id);

        // Assert
        // You can assert that the delete operation was successful here, e.g., no exceptions were thrown.
    }
}
