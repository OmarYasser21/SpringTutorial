package com.example.tutorial.CenterManagement.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import com.example.tutorial.CenterManagement.enums.Level;

import java.sql.Timestamp;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class CourseDTOProjection {
    private UUID id;

    @NotBlank
    @Size(min = 3, max = 20, message = "Course name should be minimum 3 characters and maximum 20 characters")
    private String name;

    @Future(message = "Start date must be in the future")
    private Timestamp startDate;

    @Future(message = "End date must be in the future")
    private Timestamp endDate;

    private Level level;

    @NotNull
    private boolean isStarted;

}
