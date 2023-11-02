package com.example.tutorial.CenterManagement.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class InstructorDetailsDTO {
    private UUID id;

    @NotBlank
    @Size(min = 3, message = "Hobbies should be minimum 3 characters")
    private String hobbies;

    @NotBlank
    @Size(min = 3, message = "Hobbies should be minimum 3 characters")
    private String youtubeChannel;

    private InstructorDTOProjection instructor;
}
