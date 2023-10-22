package CenterManagement.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class InstructorDTOProjection {
    private int id;

    @NotBlank
    @Size(min = 3, max = 10, message = "Name should be minimum 3 characters and maximum 10 characters")
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 10, message = "Name should be minimum 3 characters and maximum 10 characters")
    private String lastName;

    @Pattern(regexp = "\\d{11}", message = "Phone number must contain exactly 11 digits")
    private String phoneNumber;

    @Email(message = "Invalid email address")
    private String email;

    @NotBlank
    @Size(min = 2, max = 8, message = "Title should be minimum 2 characters and maximum 8 characters")
    private String title;
}
