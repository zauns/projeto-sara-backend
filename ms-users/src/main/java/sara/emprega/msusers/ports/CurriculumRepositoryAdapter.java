package sara.emprega.msusers.ports;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import sara.emprega.msusers.exception.UserNotFoundException;
import sara.emprega.msusers.model.Curriculum;
import sara.emprega.msusers.repository.CurriculumRepository;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor

public class CurriculumRepositoryAdapter implements CurriculumRepositoryPort {
    private CurriculumRepository curriculumRepository ;

    public Optional<Curriculum> findByUserID(UUID userId) {
        return Optional.ofNullable(curriculumRepository.findByUser_Id(UUID.randomUUID()).orElseThrow(()
                -> new UserNotFoundException("curriculo não encontrado")));
    }

    @Override
    public Curriculum saveCurriculum(Curriculum curriculum) {
        return curriculumRepository.save(curriculum);
    }
}
