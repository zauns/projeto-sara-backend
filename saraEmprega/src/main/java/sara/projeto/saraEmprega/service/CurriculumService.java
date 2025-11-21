package sara.projeto.saraEmprega.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import sara.projeto.saraEmprega.exception.UserNotFoundException;
import sara.projeto.saraEmprega.model.Document;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.ports.curriculum.CurriculumServicePort;
import sara.projeto.saraEmprega.ports.DocumentRepositoryPort;
import sara.projeto.saraEmprega.ports.UserServicePort;

@Transactional
@AllArgsConstructor
@Service
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

    public Document saveCurriculum(Document document, String mail, MultipartFile file) throws IOException  {
        User user = userService.getUserByMail(mail);
        String key = r2Service.upload(user.getId(),document.getDocumentType(),file, document.getDocumentName());
        document.setPathR2(key);
        document.setUser(user);
        userService.curriculumUpdate(user);
        return documentRepository.saveDocument(document);
    }

}
