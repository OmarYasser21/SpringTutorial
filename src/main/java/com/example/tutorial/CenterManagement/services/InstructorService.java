package com.example.tutorial.CenterManagement.services;

import com.example.tutorial.CenterManagement.dtos.InstructorWithCoursesDTO;
import com.example.tutorial.CenterManagement.dtos.InstructorWithStudentsDTO;
import com.example.tutorial.CenterManagement.entities.Course;
import com.example.tutorial.CenterManagement.entities.Instructor;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    private final InstructorValidation instructorValidation;
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    public InstructorService(InstructorRepo instructorRepo, InstructorMapper instructorMapper, InstructorValidation instructorValidation){
        this.instructorRepo = instructorRepo;
        this.instructorMapper = instructorMapper;
        this.instructorValidation = instructorValidation;
    }

    public boolean isPhoneNumberUniqueWrapper(String phoneNumber) {
        return instructorValidation.isPhoneNumberUnique(phoneNumber);
    }

    public boolean isEmailValidWrapper(String email) {
        return instructorValidation.isEmailValid(email);
    }

    @Cacheable(value = "instructors")
    public List<InstructorDTO> getAllInstructors() {
        List<Instructor> instructors = instructorRepo.findAll();
        return instructorMapper.toDTOs(instructors);
    }

    @Cacheable(value = "instructors", key = "#id")
    public InstructorDTO getInstructorById(int id) {
        Instructor instructor = instructorRepo.findById(id).orElse(null);
        if (instructor != null) {
            return instructorMapper.toDTO(instructor);
        } else {
            return null;
        }
    }

    public InstructorDTO saveInstructor(InstructorDTO instructorDTO) {
        if (!isPhoneNumberUniqueWrapper(instructorDTO.getPhoneNumber())) {
            throw new ValidationException("Phone number is not unique. Another instructor has the same phone number.");
        }

        if (!isEmailValidWrapper(instructorDTO.getEmail())) {
            throw new ValidationException("Invalid email format.");
        }

        Instructor instructor = instructorMapper.toEntity(instructorDTO);
        Instructor savedInstructor = instructorRepo.save(instructor);
        return instructorMapper.toDTO(savedInstructor);
    }

    @CachePut(value = "instructors", key = "#id")
    public InstructorDTO updateInstructor(int id, InstructorDTO updatedInstructorDTO) {

        Instructor existingInstructor = instructorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found with ID: " + id));

        if (!isPhoneNumberUniqueWrapper(updatedInstructorDTO.getPhoneNumber())) {
            throw new ValidationException("Phone number is not unique. Another instructor has the same phone number.");
        }

        if (!isEmailValidWrapper(updatedInstructorDTO.getEmail())) {
            throw new ValidationException("Invalid email format.");
        }

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
        cacheManager.getCache("instructors").clear();

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

    @CacheEvict(value = "instructors", key = "#id", allEntries = true)
    public void deleteInstructor(int id) {
        instructorRepo.deleteById(id);
    }

}
