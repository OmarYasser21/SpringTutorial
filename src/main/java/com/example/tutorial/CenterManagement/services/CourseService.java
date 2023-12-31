package com.example.tutorial.CenterManagement.services;

import com.example.tutorial.CenterManagement.dtos.CourseDTO;
import com.example.tutorial.CenterManagement.entities.Course;
import com.example.tutorial.CenterManagement.mappers.CourseMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.tutorial.CenterManagement.dtos.CourseWithStudentsAndStartDateDTO;
import com.example.tutorial.CenterManagement.dtos.CourseWithStudentsDTO;
import com.example.tutorial.CenterManagement.entities.Instructor;
import com.example.tutorial.CenterManagement.entities.Student;
import com.example.tutorial.CenterManagement.repositories.CourseRepo;
import com.example.tutorial.CenterManagement.repositories.InstructorRepo;

import java.util.*;

@Service
public class CourseService {
    private final CourseRepo courseRepo;
    private final CourseMapper courseMapper;
    private final InstructorRepo instructorRepo;

    @Autowired
    public CourseService(CourseRepo courseRepo, CourseMapper courseMapper, InstructorRepo instructorRepo) {
        this.courseRepo = courseRepo;
        this.courseMapper = courseMapper;
        this.instructorRepo = instructorRepo;
    }
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepo.findAll();
        return courseMapper.toDTOs(courses);
    }

    public CourseDTO getCourseById(UUID id) {
        Course course = courseRepo.findById(id).orElse(null);
        if (course != null) {
            return courseMapper.toDTO(course);
        } else {
            return null;
        }
    }

    public CourseDTO saveCourse(CourseDTO courseDTO) {
        Course course = courseMapper.toEntity(courseDTO);
        Course savedCourse = courseRepo.save(course);
        return courseMapper.toDTO(savedCourse);
    }

    public CourseDTO updateCourse(UUID id, CourseDTO updatedCourseDTO) {
        Course existingCourse = courseRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + id));

        existingCourse.setName(updatedCourseDTO.getName());
        existingCourse.setStartDate(updatedCourseDTO.getStartDate());
        existingCourse.setEndDate(updatedCourseDTO.getEndDate());
        existingCourse.setLevel(updatedCourseDTO.getLevel());
        existingCourse.setStarted(updatedCourseDTO.isStarted());

        Course updatedCourse = courseRepo.save(existingCourse);

        return courseMapper.toDTO(updatedCourse);
    }

    public CourseDTO assignInstructorToCourse(UUID courseId, int instructorId) {
        // Retrieve the Course entity by ID
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + courseId));

        // Retrieve the Instructor entity by ID
        Instructor instructor = instructorRepo.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found with ID: " + instructorId));

        // Associate the instructor with the course
        course.setInstructor(instructor);

        // Save the updated course
        Course savedCourse = courseRepo.save(course);

        return courseMapper.toDTO(savedCourse);
    }

    public List<CourseWithStudentsAndStartDateDTO> getAllCoursesWithStudents() {
       return courseRepo.getCoursesWithStudents();
    }

    public List<CourseWithStudentsDTO> getIntermediateCoursesWithStudents() {
        List<CourseWithStudentsDTO> intermediateCoursesWithStudents = new ArrayList<>();

        List<Course> intermediateCourses = courseRepo.findByLevel();

        for (Course course : intermediateCourses) {

            Set<String> studentNames = new HashSet<>();

            for (Student student : course.getStudents()) {
                String studentFullName = student.getFirstName() + " " + student.getLastName();
                studentNames.add(studentFullName);
            }

            CourseWithStudentsDTO courseWithStudents = new CourseWithStudentsDTO(course.getName(), new ArrayList<>(studentNames));
            intermediateCoursesWithStudents.add(courseWithStudents);
        }

        return intermediateCoursesWithStudents;
    }
    public void deleteCourse(UUID id) {
        courseRepo.deleteById(id);
    }
}
