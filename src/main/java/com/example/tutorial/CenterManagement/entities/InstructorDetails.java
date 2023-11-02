package com.example.tutorial.CenterManagement.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "instructor_details")
@Validated
public class InstructorDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "hobbies")
    @NotBlank
    @Size(min = 3, message = "Hobbies should be minimum 3 characters")
    private String hobbies;

    @Column(name = "youtube_channel")
    @NotBlank
    @Size(min = 3, message = "Hobbies should be minimum 3 characters")
    private String youtubeChannel;

    @OneToOne(mappedBy = "instructorDetails")
    private Instructor instructor;
}
