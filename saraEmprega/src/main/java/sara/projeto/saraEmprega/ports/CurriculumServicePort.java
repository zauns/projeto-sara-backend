package sara.projeto.saraEmprega.ports;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sara.projeto.saraEmprega.model.Document;
import sara.projeto.saraEmprega.repository.DocumentRepository;

import java.io.IOException;
import java.util.Optional;

public interface CurriculumServicePort {
    byte[] getCurriculum(String userMail);
    Document saveCurriculum(Document document, String mail, MultipartFile file) throws IOException;

    @Component
    @AllArgsConstructor
    class CurriculumRepositoryAdapter {
        private DocumentRepository documentRepository;

        public Document saveCurriculum(Document curriculum , String mail, MultipartFile file) {
            return documentRepository.save(curriculum);
        }

        public Optional<Document> loadCurriculumByMail(String mail) {
            return Optional.ofNullable(documentRepository.findByUserEmail(mail));
        }
    }
}
