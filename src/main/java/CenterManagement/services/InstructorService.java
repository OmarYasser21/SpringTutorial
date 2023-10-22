package CenterManagement.services;

import CenterManagement.dtos.InstructorWithCoursesDTO;
import CenterManagement.dtos.InstructorWithStudentsDTO;
import CenterManagement.entities.Course;
import CenterManagement.entities.Instructor;
import CenterManagement.entities.Student;
import CenterManagement.mappers.InstructorDetailsMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import CenterManagement.dtos.InstructorDTO;
import CenterManagement.dtos.InstructorDetailsDTOProjection;
import CenterManagement.entities.InstructorDetails;
import CenterManagement.mappers.InstructorDetailsProjectionMapper;
import CenterManagement.mappers.InstructorMapper;
import CenterManagement.repositories.InstructorRepo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InstructorService {
    private final InstructorRepo instructorRepo;
    private final InstructorMapper instructorMapper;
    @Autowired
    private InstructorDetailsMapper instructorDetailsMapper;
    @Autowired
    private InstructorDetailsProjectionMapper instructorDetailsProjectionMapper;

    @Autowired
    public InstructorService(InstructorRepo instructorRepo, InstructorMapper instructorMapper){
        this.instructorRepo = instructorRepo;
        this.instructorMapper = instructorMapper;
    }

    public List<InstructorDTO> getAllInstructors() {
        List<Instructor> instructors = instructorRepo.findAll();
        return instructorMapper.toDTOs(instructors);
    }

    public InstructorDTO getInstructorById(int id) {
        Instructor instructor = instructorRepo.findById(id).orElse(null);
        if (instructor != null) {
            return instructorMapper.toDTO(instructor);
        } else {
            return null;
        }
    }

    public InstructorDTO saveInstructor(InstructorDTO instructorDTO) {
        Instructor instructor = instructorMapper.toEntity(instructorDTO);
        Instructor savedInstructor = instructorRepo.save(instructor);
        return instructorMapper.toDTO(savedInstructor);
    }

    public InstructorDTO updateInstructor(int id, InstructorDTO updatedInstructorDTO) {
        Instructor existingInstructor = instructorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found with ID: " + id));

        existingInstructor.setFirstName(updatedInstructorDTO.getFirstName());
        existingInstructor.setLastName(updatedInstructorDTO.getLastName());
        existingInstructor.setPhoneNumber(updatedInstructorDTO.getPhoneNumber());
        existingInstructor.setEmail(updatedInstructorDTO.getEmail());
        existingInstructor.setTitle(updatedInstructorDTO.getTitle());

        // Update the associated instructor details
        InstructorDetails existingInstructorDetails = existingInstructor.getInstructorDetails();
        InstructorDetailsDTOProjection updatedInstructorDetailsProjection = updatedInstructorDTO.getInstructorDetails();

        // Update the instructor details fields
        existingInstructorDetails.setHobbies(updatedInstructorDetailsProjection.getHobbies());
        existingInstructorDetails.setYoutubeChannel(updatedInstructorDetailsProjection.getYoutubeChannel());

        Instructor updatedInstructor = instructorRepo.save(existingInstructor);

        return instructorMapper.toDTO(updatedInstructor);
    }

    public List<InstructorWithCoursesDTO> getAllInstructorsWithCourses() {
        List<Instructor> instructors = instructorRepo.findAll();
        List<InstructorWithCoursesDTO> result = new ArrayList<>();

        for (Instructor instructor : instructors) {
            String fullName = instructor.getFirstName() + " " + instructor.getLastName();
            List<String> courseNames = instructor.getCourses().stream()
                    .map(Course::getName)
                    .collect(Collectors.toList());

            InstructorWithCoursesDTO dto = new InstructorWithCoursesDTO(fullName, courseNames);
            result.add(dto);
        }

        return result;
    }

    public List<InstructorWithStudentsDTO> getAllInstructorsWithStudents() {
        List<InstructorWithStudentsDTO> instructorsWithStudents = new ArrayList<>();

        List<Instructor> instructors = instructorRepo.findAll();

        for (Instructor instructor : instructors) {
            String instructorName = instructor.getFirstName() + " " + instructor.getLastName();
            Set<String> studentNames = new HashSet<>(); // Use a Set to ensure uniqueness

            for (Course course : instructor.getCourses()) {
                for (Student student : course.getStudents()) {
                    String studentFullName = student.getFirstName() + " " + student.getLastName();
                    studentNames.add(studentFullName);
                }
            }

            InstructorWithStudentsDTO instructorWithStudents = new InstructorWithStudentsDTO(instructorName,
                    new ArrayList<>(studentNames));
            instructorsWithStudents.add(instructorWithStudents);
        }

        return instructorsWithStudents;
    }



    public void deleteInstructor(int id) {
        instructorRepo.deleteById(id);
    }

}
