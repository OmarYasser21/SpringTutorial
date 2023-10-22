package CenterManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import CenterManagement.entities.InstructorDetails;

import java.util.UUID;

@Repository
public interface InstructorDetailsRepo extends JpaRepository<InstructorDetails, UUID> {
}
