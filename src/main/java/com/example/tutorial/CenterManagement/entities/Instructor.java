package com.example.tutorial.CenterManagement.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "instructor")
@Validated
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    @NotBlank
    @Size(min = 3, max = 10, message = "Name should be minimum 3 characters and maximum 10 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    @Size(min = 3, max = 10, message = "Name should be minimum 3 characters and maximum 10 characters")
    private String lastName;

    @Column(name = "phone_number")
    @Pattern(regexp = "\\d{11}", message = "Phone number must contain exactly 11 digits")
    private String phoneNumber;

    @Column(name = "email")
    @Email(message = "Invalid email address")
    private String email;

    @Column(name = "title")
    @NotBlank
    @Size(min = 2, max = 8, message = "Title should be minimum 2 characters and maximum 8 characters")
    private String title;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "instructor_details_id")
    private InstructorDetails instructorDetails;

    @OneToMany(mappedBy = "instructor", fetch = FetchType.EAGER)
    private List<Course> courses;
}
