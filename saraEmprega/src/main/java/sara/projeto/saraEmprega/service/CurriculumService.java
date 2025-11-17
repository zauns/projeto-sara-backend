package sara.projeto.saraEmprega.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sara.projeto.saraEmprega.exception.UserNotFoundException;
import sara.projeto.saraEmprega.model.Curriculum;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.ports.CurriculumServicePort;
import sara.projeto.saraEmprega.ports.UserServicePort;

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
