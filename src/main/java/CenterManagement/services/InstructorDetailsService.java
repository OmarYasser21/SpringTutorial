package CenterManagement.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import CenterManagement.dtos.InstructorDetailsDTO;
import CenterManagement.entities.InstructorDetails;
import CenterManagement.mappers.InstructorDetailsMapper;
import CenterManagement.repositories.InstructorDetailsRepo;

import java.util.List;
import java.util.UUID;

@Service
public class InstructorDetailsService {
    private final InstructorDetailsRepo instructorDetailsRepo;
    private final InstructorDetailsMapper instructorDetailsMapper;

    @Autowired
    public InstructorDetailsService(InstructorDetailsRepo instructorDetailsRepo, InstructorDetailsMapper instructorDetailsMapper) {
        this.instructorDetailsRepo = instructorDetailsRepo;
        this.instructorDetailsMapper = instructorDetailsMapper;
    }

    public List<InstructorDetailsDTO> getAllInstructorDetails() {
        List<InstructorDetails> instructorDetailsList = instructorDetailsRepo.findAll();
        return instructorDetailsMapper.toDTOs(instructorDetailsList);
    }

    public InstructorDetailsDTO getInstructorDetailsById(UUID id) {
        InstructorDetails instructorDetails = instructorDetailsRepo.findById(id).orElse(null);
        if (instructorDetails != null) {
            return instructorDetailsMapper.toDTO(instructorDetails);
        } else {
            return null;
        }
    }

    public InstructorDetailsDTO saveInstructorDetails(InstructorDetailsDTO instructorDetailsDTO) {
        InstructorDetails instructorDetails = instructorDetailsMapper.toEntity(instructorDetailsDTO);
        InstructorDetails savedInstructorDetails = instructorDetailsRepo.save(instructorDetails);
        return instructorDetailsMapper.toDTO(savedInstructorDetails);
    }

    public InstructorDetailsDTO updateInstructorDetails(UUID id, InstructorDetailsDTO updatedInstructorDetailsDTO) {
        InstructorDetails existingInstructorDetails = instructorDetailsRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instructor Details not found with ID: " + id));

        existingInstructorDetails.setHobbies(updatedInstructorDetailsDTO.getHobbies());
        existingInstructorDetails.setYoutubeChannel(updatedInstructorDetailsDTO.getYoutubeChannel());

        InstructorDetails updatedInstructorDetails = instructorDetailsRepo.save(existingInstructorDetails);

        return instructorDetailsMapper.toDTO(updatedInstructorDetails);
    }

    public void deleteInstructorDetails(UUID id) {
        instructorDetailsRepo.deleteById(id);
    }
}
