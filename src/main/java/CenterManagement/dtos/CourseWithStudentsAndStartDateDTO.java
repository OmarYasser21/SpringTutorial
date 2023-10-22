package CenterManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseWithStudentsAndStartDateDTO {
    private String courseName;
    private Timestamp startDate;
    private List<String> enrolledStudents;
}
