package com.example.tutorial.CenterManagement.services;

import com.example.tutorial.CenterManagement.entities.Instructor;
import com.example.tutorial.CenterManagement.repositories.InstructorRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InstructorValidationTest {
    @Mock
    InstructorRepo instructorRepo;
    @InjectMocks
    InstructorValidation instructorValidation;
    @Test
    public void testIsPhoneNumberUnique_NumberUnique() {
        // Arrange
        String phoneNumber = "1234567890";
        doReturn(null).when(instructorRepo).findByPhoneNumber(phoneNumber);
//      when(instructorRepo.findByPhoneNumber(phoneNumber)).thenReturn(null);

        // Act
        boolean result = instructorValidation.isPhoneNumberUnique(phoneNumber);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsPhoneNumberUnique_NumberAlreadyExists() {
        // Arrange
        String phoneNumber = "1234567890";
        doReturn(new Instructor()).when(instructorRepo).findByPhoneNumber(phoneNumber);
//        when(instructorRepo.findByPhoneNumber(phoneNumber)).thenReturn(new Instructor());

        // Act
        boolean result = instructorValidation.isPhoneNumberUnique(phoneNumber);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsEmailValid_ValidEmail() {
        // Arrange
        String validEmail = "test@example.com";

        // Act
        boolean result = instructorValidation.isEmailValid(validEmail);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsEmailValid_InvalidEmail() {
        // Arrange
        String invalidEmail = "invalid-email";

        // Act
        boolean result = instructorValidation.isEmailValid(invalidEmail);

        // Assert
        assertFalse(result);
    }
}
