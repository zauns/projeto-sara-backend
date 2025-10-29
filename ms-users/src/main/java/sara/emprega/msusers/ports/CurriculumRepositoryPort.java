package sara.emprega.msusers.ports;

import sara.emprega.msusers.model.Curriculum;

import java.util.Optional;
import java.util.UUID;

public interface CurriculumRepositoryPort {
    Optional<Curriculum> findByUserID(UUID userId);
    Curriculum saveCurriculum(Curriculum curriculum);
}
