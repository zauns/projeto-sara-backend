package sara.projeto.saraEmprega.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sara.projeto.saraEmprega.dto.CurriculumDTO;
import sara.projeto.saraEmprega.model.Curriculum;
import sara.projeto.saraEmprega.util.Mapper;
import sara.projeto.saraEmprega.util.Validate;

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
