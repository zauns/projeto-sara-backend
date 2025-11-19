/*package sara.emprega.msusers.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sara.emprega.msusers.dto.CurriculumDTO;
import sara.emprega.msusers.model.Document;
import sara.emprega.msusers.ports.ContentServicePort;
import sara.emprega.msusers.util.Mapper;
import sara.emprega.msusers.util.Validate;

import java.io.IOException;


@AllArgsConstructor
@RestController
@RequestMapping("api/content")
public class ContentController {

    ContentServicePort contentService;

    @PostMapping()
    public ResponseEntity<CurriculumDTO> saveContentPDF(@RequestParam("file") MultipartFile file
            , Authentication auth, @RequestParam("name") String fileName) throws IOException {

        Validate.validatePDF(file);
        Document document = Mapper.mapToContentPDF(fileName);
        Jwt jwt = (Jwt) auth.getPrincipal();
        curriculumService.saveCurriculum(document, jwt.getSubject(),file);
        return ResponseEntity.ok().body(new CurriculumDTO(document.getPathR2(),document.getDocumentName()
                , document.getDocumentType()));
    }

    @GetMapping
    public ResponseEntity<byte[]> getCurriculum(Authentication auth,) {
        byte[] doc = contentService.getDocs();

        //CurriculumDTO curriculumDTO = new CurriculumDTO(document.getPathR2(),document.getDocumentName()
        //        , document.getDocumentType());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION
                        , "inline; filename=\"curriculo.pdf\"")
                .body(doc);
    }

}


 */