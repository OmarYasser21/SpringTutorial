package CenterManagement.controllers;

import CenterManagement.dtos.InstructorWithCoursesDTO;
import CenterManagement.dtos.InstructorWithStudentsDTO;
import CenterManagement.services.InstructorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import CenterManagement.dtos.InstructorDTO;

import java.util.List;

@RestController
@RequestMapping("/instructors")
public class InstructorController{
    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService){
        this.instructorService = instructorService;
    }

    @GetMapping
    public ResponseEntity<List<InstructorDTO>> getAllInstructors() {
        List<InstructorDTO> instructors = instructorService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorDTO> getInstructorById(@PathVariable int id) {
        InstructorDTO instructor = instructorService.getInstructorById(id);
        if (instructor != null) {
            return ResponseEntity.ok(instructor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<InstructorDTO> saveInstructor(@Valid @RequestBody InstructorDTO instructor) {
        InstructorDTO savedInstructor = instructorService.saveInstructor(instructor);
        return ResponseEntity.ok(savedInstructor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorDTO> updateInstructor(@PathVariable int id, @Valid @RequestBody InstructorDTO updatedInstructor) {
        InstructorDTO updatedInstructorDTO = instructorService.updateInstructor(id, updatedInstructor);
        if (updatedInstructorDTO != null) {
            return ResponseEntity.ok(updatedInstructorDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/courses")
    public ResponseEntity<List<InstructorWithCoursesDTO>> getAllInstructorsWithCourses() {
        List<InstructorWithCoursesDTO> result = instructorService.getAllInstructorsWithCourses();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/students")
    public ResponseEntity<List<InstructorWithStudentsDTO>> getAllInstructorsWithStudents() {
        List<InstructorWithStudentsDTO> instructorsWithStudents = instructorService.getAllInstructorsWithStudents();
        return ResponseEntity.ok(instructorsWithStudents);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable int id) {
        instructorService.deleteInstructor(id);
        return ResponseEntity.noContent().build();
    }
}
