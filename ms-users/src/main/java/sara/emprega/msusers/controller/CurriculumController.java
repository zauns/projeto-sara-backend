package sara.emprega.msusers.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sara.emprega.msusers.dto.CurriculumDTO;
import sara.emprega.msusers.model.Curriculum;
import sara.emprega.msusers.model.User;
import sara.emprega.msusers.ports.CurriculumServicePort;
import sara.emprega.msusers.util.Mapper;
import sara.emprega.msusers.util.Validate;
import sara.emprega.msusers.util.jwt.UserAuthenticated;

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
