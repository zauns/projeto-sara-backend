package sara.projeto.saraEmprega.service;

import lombok.AllArgsConstructor;
import org.bouncycastle.util.Bytes;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sara.projeto.saraEmprega.exception.UserNotFoundException;
import sara.projeto.saraEmprega.model.Course;
import sara.projeto.saraEmprega.model.Document;
import sara.projeto.saraEmprega.ports.CourseRepositoryPort;
import sara.projeto.saraEmprega.ports.CourseServicePort;

import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CourseService implements CourseServicePort {
    private CourseRepositoryPort courseRepository;
    private R2Service r2Service;

    @Override
    public List<Course> findCourseByName(String name) {
        return courseRepository.findByName(name);
    }

    //TODO
    @Override
    public Course createCourse(String name, List<String> tags, String description, List<String> creators) {
        return courseRepository.createCourse(name, tags, description, creators);
    }

    public byte[] getGuide(String path){
       return r2Service.download(path);
    }

    public Document addGuideToCourse(UUID id, MultipartFile file,Document doc) throws IOException {
        Course course = courseRepository.findById(id);
        r2Service.upload(id, doc.getDocumentType(), file,doc.getDocumentName());
        doc.setCourse(course);
        course.getDocs().add(doc);

        courseRepository.updateCourse(course);
        return doc;
    }

    public void rateCourse(UUID courseId,double rate) {
        Course course = courseRepository.findById(courseId);
        Double actualRate = course.getRating();
        course.setRating((actualRate + rate)/course.getNumberOfReviews()+1);
    }
}