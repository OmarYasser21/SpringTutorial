package com.example.tutorial.CenterManagement.services;

import com.example.tutorial.CenterManagement.dtos.InstructorWithCoursesDTO;
import com.example.tutorial.CenterManagement.dtos.InstructorWithStudentsDTO;
import com.example.tutorial.CenterManagement.entities.Course;
import com.example.tutorial.CenterManagement.entities.Instructor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.tutorial.CenterManagement.dtos.InstructorDTO;
import com.example.tutorial.CenterManagement.dtos.InstructorDetailsDTOProjection;
import com.example.tutorial.CenterManagement.entities.InstructorDetails;
import com.example.tutorial.CenterManagement.mappers.InstructorMapper;
import com.example.tutorial.CenterManagement.repositories.InstructorRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstructorService {
    private final InstructorRepo instructorRepo;
    private final InstructorMapper instructorMapper;

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

        if (updatedInstructorDetailsProjection != null) {
            if (existingInstructorDetails == null) {
                existingInstructorDetails = new InstructorDetails();
                existingInstructor.setInstructorDetails(existingInstructorDetails);
            }

            existingInstructorDetails.setHobbies(updatedInstructorDetailsProjection.getHobbies());
            existingInstructorDetails.setYoutubeChannel(updatedInstructorDetailsProjection.getYoutubeChannel());
        }

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
        return instructorRepo.getAllInstructorsWithStudents();
    }

    public void deleteInstructor(int id) {
        instructorRepo.deleteById(id);
    }

}
