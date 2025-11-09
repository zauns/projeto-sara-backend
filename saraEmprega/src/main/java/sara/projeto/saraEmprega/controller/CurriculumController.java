package sara.projeto.saraEmprega.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
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
            Jwt jwt = (Jwt) auth.getPrincipal();
            curriculumService.setCurriculum(curriculum, jwt.getSubject());
            return ResponseEntity.ok().body(new CurriculumDTO(curriculum.getData()));
        }

    @GetMapping
    public ResponseEntity<CurriculumDTO> getCurriculum(Authentication auth) {

        Jwt jwt = (Jwt) auth.getPrincipal();;
        Curriculum curriculum = curriculumService.getCurriculum(jwt.getSubject());
        CurriculumDTO curriculumDTO = new CurriculumDTO(curriculum.getData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION
                        , "inline; filename=\"curriculo.pdf\"")
                .body(curriculumDTO);
    }
}
