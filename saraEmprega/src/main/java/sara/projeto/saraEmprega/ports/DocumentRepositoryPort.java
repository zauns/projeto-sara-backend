package sara.projeto.saraEmprega.ports;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import sara.projeto.saraEmprega.model.Document;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.repository.DocumentRepository;

@Component
@AllArgsConstructor
public class DocumentRepositoryPort {
    private final DocumentRepository documentRepository;

    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }

    public Document getDocumentByUser(User user) {
        return documentRepository.getDocumentByUser();
    }
}
