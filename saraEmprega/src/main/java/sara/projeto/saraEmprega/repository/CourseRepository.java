package sara.projeto.saraEmprega.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sara.projeto.saraEmprega.model.Course;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    @Query("SELECT c from Course c where c.name = :name")
    Course findByCourseName(@Param("name") String name);
}
