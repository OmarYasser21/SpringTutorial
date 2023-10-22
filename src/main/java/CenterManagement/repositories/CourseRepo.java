package CenterManagement.repositories;

import CenterManagement.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


import java.util.UUID;
@Repository
public interface CourseRepo extends JpaRepository<Course, UUID> {
    @Query("SELECT c FROM Course c WHERE c.level = 'intermediate'")
    List<Course> findByLevel();
}
