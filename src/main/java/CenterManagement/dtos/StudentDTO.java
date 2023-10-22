package CenterManagement.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import CenterManagement.enums.Gender;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class StudentDTO {
    private UUID id;

    @NotBlank
    @Size(min = 3, max = 10, message = "Name should be minimum 3 characters and maximum 10 characters")
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 10, message = "Name should be minimum 3 characters and maximum 10 characters")
    private String lastName;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 35, message = "Age must be 35 or less")
    private int age;

    private Gender gender;

    @Email(message = "Invalid email address")
    private String email;

    @Pattern(regexp = "^[0-9]{11}$", message = "Phone number must contain 11 digits")
    private String phoneNumber;

    private Long nationalId;

    private Set<CourseDTOProjection> courses;
}
