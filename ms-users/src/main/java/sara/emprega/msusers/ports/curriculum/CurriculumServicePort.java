package sara.emprega.msusers.ports.curriculum;

import org.springframework.web.multipart.MultipartFile;
import sara.emprega.msusers.model.Document;

import java.io.IOException;

public interface CurriculumServicePort {
    byte[] getCurriculum(String userMail);
    Document saveCurriculum(Document document, String mail, MultipartFile file) throws IOException;
}
