package sara.emprega.msusers.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sara.emprega.msusers.dto.CurriculumDTO;
import sara.emprega.msusers.model.Curriculum;
import sara.emprega.msusers.model.User;
import sara.emprega.msusers.ports.CurriculumServerPort;
import sara.emprega.msusers.util.Mapper;
import sara.emprega.msusers.util.Validate;
import sara.emprega.msusers.util.jwt.UserAuthenticated;

import java.io.IOException;

@RestController
@RequestMapping("api/curriculum")
public class CurriculumController {

    CurriculumServerPort curriculumServer;

    @PostMapping()
    public ResponseEntity<CurriculumDTO> saveCurriculum(@RequestParam("file") MultipartFile file
            ,Authentication authentication) throws IOException {

            //TODO security
            Validate.validatePDF(file);
            Curriculum curriculum = Mapper.mapToCurriculum(file);
            return ResponseEntity.ok().body(new CurriculumDTO(curriculum.getData()));
        }

    @GetMapping
    public ResponseEntity<CurriculumDTO> getCurriculo(Authentication auth) {

        UserAuthenticated authUser = (UserAuthenticated) auth.getPrincipal();
        User user= (User) authUser.getUser();
        curriculumServer.getCurriculum();

        CurriculumDTO curriculum = new CurriculumDTO(user.getCurriculum().getData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION
                        , "inline; filename=\"curriculo.pdf\"")
                .body(curriculum);
    }
}
