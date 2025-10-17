package sara.emprega.msusers.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sara.emprega.msusers.dto.CurriculumRequestDTO;
import sara.emprega.msusers.model.Curriculum;
import sara.emprega.msusers.util.Mapper;
import sara.emprega.msusers.util.Validate;

import java.io.IOException;

@RestController
@RequestMapping("api/curriculum")
public class CurriculumController {

    @PostMapping()
    public ResponseEntity<CurriculumRequestDTO> addCurriculum(@RequestParam("file") MultipartFile file)
            throws IOException {
            //TODO
            Validate.validatePDF(file);
            Mapper.mapToCurriculum(file);

            return ResponseEntity.ok();
        }
    }
}
