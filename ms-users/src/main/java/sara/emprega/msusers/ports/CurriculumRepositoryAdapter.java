package sara.emprega.msusers.ports;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import sara.emprega.msusers.model.Document;
import sara.emprega.msusers.repository.DocumentRepository;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CurriculumRepositoryAdapter {
    private DocumentRepository documentRepository;

    public Document saveCurriculum(Document curriculum) {
        return documentRepository.save(curriculum);
    }

    public Optional<Document> loadCurriculumByMail(String mail) {
        return Optional.ofNullable(documentRepository.findByUserEmail(mail));
    }
}
