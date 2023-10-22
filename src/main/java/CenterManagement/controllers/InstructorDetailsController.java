package CenterManagement.controllers;

import CenterManagement.dtos.InstructorDetailsDTO;
import CenterManagement.services.InstructorDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/instructor-details")
public class InstructorDetailsController {
    private final InstructorDetailsService instructorDetailsService;

    @Autowired
    public InstructorDetailsController(InstructorDetailsService instructorDetailsService) {
        this.instructorDetailsService = instructorDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<InstructorDetailsDTO>> getAllInstructorDetails() {
        List<InstructorDetailsDTO> instructorDetailsList = instructorDetailsService.getAllInstructorDetails();
        return ResponseEntity.ok(instructorDetailsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorDetailsDTO> getInstructorDetailsById(@PathVariable UUID id) {
        InstructorDetailsDTO instructorDetails = instructorDetailsService.getInstructorDetailsById(id);
        if (instructorDetails != null) {
            return ResponseEntity.ok(instructorDetails);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<InstructorDetailsDTO> saveInstructorDetails(@Valid @RequestBody InstructorDetailsDTO instructorDetails) {
        InstructorDetailsDTO savedInstructorDetails = instructorDetailsService.saveInstructorDetails(instructorDetails);
        return ResponseEntity.ok(savedInstructorDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorDetailsDTO> updateInstructorDetails(@PathVariable UUID id, @Valid @RequestBody InstructorDetailsDTO updatedInstructorDetails) {
        InstructorDetailsDTO updatedInstructorDetailsDTO = instructorDetailsService.updateInstructorDetails(id, updatedInstructorDetails);
        if (updatedInstructorDetailsDTO != null) {
            return ResponseEntity.ok(updatedInstructorDetailsDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructorDetails(@PathVariable UUID id) {
        instructorDetailsService.deleteInstructorDetails(id);
        return ResponseEntity.noContent().build();
    }
}
