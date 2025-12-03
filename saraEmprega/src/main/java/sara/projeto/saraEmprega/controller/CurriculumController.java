package sara.projeto.saraEmprega.controller;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sara.projeto.saraEmprega.dto.CurriculumDTO;
import sara.projeto.saraEmprega.model.Document;
import sara.projeto.saraEmprega.ports.CurriculumServicePort;
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

            String userEmail = jwt.getClaimAsString("email");

            curriculumService.saveCurriculum(document, userEmail,file);
            return ResponseEntity.ok().body(new CurriculumDTO(document.getPathR2(),document.getDocumentName()
                    , document.getDocumentType()));
        }

    @GetMapping
    public ResponseEntity<byte[]> getCurriculum(Authentication auth) {

        Jwt jwt = (Jwt) auth.getPrincipal();

        String userEmail = jwt.getClaimAsString("email");

        byte[] doc = curriculumService.getCurriculum(userEmail);
        //CurriculumDTO curriculumDTO = new CurriculumDTO(document.getPathR2(),document.getDocumentName()
        //        , document.getDocumentType());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION
                        , "inline; filename=\"curriculo.pdf\"")
                .body(doc);
    }

    @GetMapping("/candidatas/{email}/curriculos")
    @PreAuthorize("hasRole('EMPRESA')")
    public ResponseEntity<byte[]> getCurriculumByMail(@PathVariable("email") String email) {
        byte[] doc = curriculumService.getCurriculum(email);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION
                        , "inline; filename=\"curriculo.pdf\"")
                .body(doc);
    }


}
