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
import sara.projeto.saraEmprega.model.Course;
import sara.projeto.saraEmprega.model.Document;
import sara.projeto.saraEmprega.service.CourseService;
import sara.projeto.saraEmprega.util.Mapper;
import sara.projeto.saraEmprega.util.Validate;


import java.io.IOException;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@RestController
@RequestMapping("api/content")
public class ContentController {

    CourseService courseService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("{courseId}/guides")
    public ResponseEntity<CurriculumDTO> saveGuideInCourse(@RequestParam("file") MultipartFile file
            , @PathVariable UUID courseId, @RequestParam("name") String fileName) throws IOException {

        Validate.validatePDF(file);
        Document document = Mapper.mapToContentPDF(fileName);
        courseService.addGuideToCourse(courseId,file,document);
        return ResponseEntity.ok().body(new CurriculumDTO(document.getPathR2(),document.getDocumentName()
                , document.getDocumentType()));
    }

    @GetMapping("/guide")
    public ResponseEntity<byte[]> loadGuideInCourses(@RequestParam String pathR2) {
        byte[] toReturn = courseService.getGuide(pathR2);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION
                        , "inline; filename=\"curriculo.pdf\"")
                .body(toReturn);
    }

    @GetMapping
    public ResponseEntity<List<Course>> getCourses(@RequestParam String courseName) {
        return ResponseEntity.ok().body(courseService.findCourseByName(courseName));
    }

    @PutMapping("/{courseId}/rating")
    public ResponseEntity<Void> rateCourse(@RequestParam String courseId, @RequestBody double rate){
        courseService.rateCourse(UUID.fromString(courseId),rate);
        return ResponseEntity.ok().build();
    }

}
