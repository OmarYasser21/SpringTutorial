package CenterManagement.entities;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import CenterManagement.enums.Gender;

import jakarta.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")
@Validated
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name")
    @NotBlank
    @Size(min = 3, max = 10, message = "Name should be minimum 3 characters and maximum 10 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    @Size(min = 3, max = 10, message = "Name should be minimum 3 characters and maximum 10 characters")
    private String lastName;

    @Column(name = "age")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 35, message = "Age must be 35 or less")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "email")
    @Email(message = "Invalid email address")
    private String email;

    @Column(name = "phone_number")
    @Pattern(regexp = "^[0-9]{11}$", message = "Phone number must contain 11 digits")
    private String phoneNumber;

    @Column(name = "national_id")
    private Long nationalId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses;
}
