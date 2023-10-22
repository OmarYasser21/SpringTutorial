package CenterManagement.controllers;

import CenterManagement.dtos.CourseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import CenterManagement.dtos.CourseWithStudentsAndStartDateDTO;
import CenterManagement.dtos.CourseWithStudentsDTO;
import CenterManagement.services.CourseService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable UUID id) {
        CourseDTO course = courseService.getCourseById(id);
        if (course != null) {
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<CourseDTO> saveCourse(@Valid @RequestBody CourseDTO course) {
        CourseDTO savedCourse = courseService.saveCourse(course);
        return ResponseEntity.ok(savedCourse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable UUID id, @Valid @RequestBody CourseDTO updatedCourse) {
        CourseDTO updatedCourseDTO = courseService.updateCourse(id, updatedCourse);
        if (updatedCourseDTO != null) {
            return ResponseEntity.ok(updatedCourseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/assign-instructor")
    public ResponseEntity<CourseDTO> assignInstructorToCourse(@RequestParam UUID courseId, @RequestParam int instructorId) {
        CourseDTO courseDTO = courseService.assignInstructorToCourse(courseId, instructorId);
        return ResponseEntity.ok(courseDTO);
    }

    @GetMapping("/students")
    public ResponseEntity<List<CourseWithStudentsAndStartDateDTO>> getAllCoursesWithStudents() {
        List<CourseWithStudentsAndStartDateDTO> coursesWithStudents = courseService.getAllCoursesWithStudents();
        return ResponseEntity.ok(coursesWithStudents);
    }

    @GetMapping("/intermediate")
    public List<CourseWithStudentsDTO> getIntermediateCoursesWithStudents() {
        return courseService.getIntermediateCoursesWithStudents();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
