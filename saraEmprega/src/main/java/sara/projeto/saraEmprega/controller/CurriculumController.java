package sara.projeto.saraEmprega.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sara.projeto.saraEmprega.dto.CurriculumDTO;
import sara.projeto.saraEmprega.model.Curriculum;
import sara.projeto.saraEmprega.ports.CurriculumServicePort;
import sara.projeto.saraEmprega.util.Mapper;
import sara.projeto.saraEmprega.util.Validate;
import sara.projeto.saraEmprega.util.jwt.UserAuthenticated;

import java.io.IOException;

@RestController
@RequestMapping("api/curriculum")
public class CurriculumController {

    CurriculumServicePort curriculumService;

    @PostMapping()
    public ResponseEntity<CurriculumDTO> saveCurriculum(@RequestParam("file") MultipartFile file
            ,Authentication auth) throws IOException {

            Validate.validatePDF(file);
            Curriculum curriculum = Mapper.mapToCurriculum(file);
        UserAuthenticated userAuth = (UserAuthenticated) auth.getPrincipal();
            curriculumService.setCurriculum(curriculum, userAuth);
            return ResponseEntity.ok().body(new CurriculumDTO(curriculum.getData()));
        }

    @GetMapping
    public ResponseEntity<CurriculumDTO> getCurriculum(Authentication auth) {

        UserAuthenticated authUser = (UserAuthenticated) auth.getPrincipal();
        Curriculum curriculum = curriculumService.getCurriculum(authUser);
        CurriculumDTO curriculumDTO = new CurriculumDTO(curriculum.getData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION
                        , "inline; filename=\"curriculo.pdf\"")
                .body(curriculumDTO);
    }
}
