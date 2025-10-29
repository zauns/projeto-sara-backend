package sara.projeto.saraEmprega.ports;

import sara.projeto.saraEmprega.model.Curriculum;

import java.util.Optional;
import java.util.UUID;

public interface CurriculumRepositoryPort {
    Optional<Curriculum> findByUserID(UUID userId);
    Curriculum saveCurriculum(Curriculum curriculum);
}
