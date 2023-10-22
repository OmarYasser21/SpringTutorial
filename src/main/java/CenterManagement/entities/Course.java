package CenterManagement.entities;

import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import org.springframework.validation.annotation.Validated;
import CenterManagement.enums.Level;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course")
@Validated
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    @NotBlank
    @Size(min = 3, max = 20, message = "Course name should be minimum 3 characters and maximum 20 characters")
    private String name;

    @Column(name = "start_date")
    @Future(message = "Start date must be in the future")
    private Timestamp startDate;

    @Column(name = "end_date")
    @Future(message = "End date must be in the future")
    private Timestamp endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private Level level;

    @Column(name = "is_started")
    @NotNull
    private boolean isStarted;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;


    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
    private Set<Student> students;
}
