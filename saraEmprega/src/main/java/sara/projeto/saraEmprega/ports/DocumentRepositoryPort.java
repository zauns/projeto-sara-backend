package sara.emprega.msusers.ports;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import sara.emprega.msusers.model.Document;
import sara.emprega.msusers.repository.DocumentRepository;

import java.util.Optional;

@Component
@AllArgsConstructor
public class DocumentRepositoryPort {
    private final DocumentRepository documentRepository;

    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }
}
