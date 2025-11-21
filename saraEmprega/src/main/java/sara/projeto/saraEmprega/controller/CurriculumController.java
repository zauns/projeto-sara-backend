package sara.projeto.saraEmprega.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sara.projeto.saraEmprega.dto.CurriculumDTO;
import sara.projeto.saraEmprega.model.Document;
import sara.projeto.saraEmprega.ports.curriculum.CurriculumServicePort;
import sara.projeto.saraEmprega.util.Mapper;
import sara.projeto.saraEmprega.util.Validate;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("api/curriculum")
public class CurriculumController {

    CurriculumServicePort curriculumService;

    @PostMapping()
    public ResponseEntity<CurriculumDTO> saveCurriculum(@RequestParam("file") MultipartFile file
            ,Authentication auth, @RequestParam("name") String fileName) throws IOException {

            Validate.validatePDF(file);
            Document document = Mapper.mapToCurriculum(fileName);
            Jwt jwt = (Jwt) auth.getPrincipal();
            curriculumService.saveCurriculum(document, jwt.getSubject(),file);
            return ResponseEntity.ok().body(new CurriculumDTO(document.getPathR2(),document.getDocumentName()
                    , document.getDocumentType()));
        }

    @GetMapping
    public ResponseEntity<byte[]> getCurriculum(Authentication auth) {

        Jwt jwt = (Jwt) auth.getPrincipal();
        byte[] doc = curriculumService.getCurriculum(jwt.getSubject());

        //CurriculumDTO curriculumDTO = new CurriculumDTO(document.getPathR2(),document.getDocumentName()
        //        , document.getDocumentType());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION
                        , "inline; filename=\"curriculo.pdf\"")
                .body(doc);
    }
}
