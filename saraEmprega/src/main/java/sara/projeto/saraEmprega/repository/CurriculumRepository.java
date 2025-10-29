package sara.projeto.saraEmprega.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sara.projeto.saraEmprega.model.Curriculum;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, UUID> {

    Optional<Curriculum> findByUser_Id(UUID userId);

}
