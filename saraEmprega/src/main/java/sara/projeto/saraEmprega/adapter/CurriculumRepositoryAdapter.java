package sara.projeto.saraEmprega.adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import sara.projeto.saraEmprega.model.Curriculum;
import sara.projeto.saraEmprega.ports.CurriculumRepositoryPort;
import sara.projeto.saraEmprega.repository.CurriculumRepository;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class CurriculumRepositoryAdapter implements CurriculumRepositoryPort {
    private CurriculumRepository curriculumRepository ;

    public Optional<Curriculum> findByUserID(UUID userId) {
        return curriculumRepository.findByUserId(userId);
    }

    @Override
    public Curriculum saveCurriculum(Curriculum curriculum) {
        return curriculumRepository.save(curriculum);
    }
}
