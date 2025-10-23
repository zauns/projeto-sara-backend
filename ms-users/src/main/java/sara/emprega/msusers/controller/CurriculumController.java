package sara.emprega.msusers.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sara.emprega.msusers.dto.CurriculumDTO;
import sara.emprega.msusers.model.Curriculum;
import sara.emprega.msusers.util.Mapper;
import sara.emprega.msusers.util.Validate;

import java.io.IOException;

@RestController
@RequestMapping("api/curriculum")
public class CurriculumController {

    @PostMapping()
    public ResponseEntity<CurriculumDTO> saveCurriculum(@RequestParam("file") MultipartFile file)
            throws IOException {
            //TODO security
            Validate.validatePDF(file);
            Curriculum curriculum = Mapper.mapToCurriculum(file);
            //logica de upload do curriculo
            return ResponseEntity.ok().body(new CurriculumDTO(curriculum.getId(),curriculum.getData()));
        }

    @GetMapping
    public ResponseEntity<CurriculumDTO> getCurriculo(){
        //todo
        CurriculumDTO curriculum;

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION
                        , "inline; filename=\"curriculo.pdf\"")
                .body(curriculum);
    }
}
