package sara.emprega.msusers.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sara.emprega.msusers.exception.UserNotFoundException;
import sara.emprega.msusers.model.Document;
import sara.emprega.msusers.model.User;
import sara.emprega.msusers.ports.DocumentRepositoryPort;
import sara.emprega.msusers.ports.curriculum.CurriculumServicePort;
import sara.emprega.msusers.ports.UserServicePort;

import java.io.IOException;

@Transactional
@Service
@AllArgsConstructor
public class CurriculumService implements CurriculumServicePort {

    private UserServicePort userService;
    private R2Service r2Service;
    private DocumentRepositoryPort documentRepository;

    @Override
    public byte[] getCurriculum(String userMail) {
        User user = userService.getUserByMail(userMail);
        if (user.getDocument() == null){
                throw new UserNotFoundException("curriculo nao encontrado");
        }
        return r2Service.download(user.getDocument().getPathR2());
    }

    @Override
    public Document saveCurriculum(Document document, String mail, MultipartFile file) throws IOException {
        User user = userService.getUserByMail(mail);
        String key = r2Service.upload(user.getId(),document.getDocumentType(),file, document.getDocumentName());
        document.setPathR2(key);
        document.setUser(user);
        userService.curriculumUpdate(user);
        return documentRepository.saveDocument(document);
    }
}
