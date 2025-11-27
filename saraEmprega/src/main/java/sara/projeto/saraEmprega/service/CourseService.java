package sara.projeto.saraEmprega.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sara.projeto.saraEmprega.exception.UserNotFoundException;
import sara.projeto.saraEmprega.model.Course;
import sara.projeto.saraEmprega.ports.CourseRepositoryPort;
import sara.projeto.saraEmprega.ports.CourseServicePort;

import java.util.List;

@AllArgsConstructor
@Service
public class CourseService implements CourseServicePort {
    private CourseRepositoryPort courseRepository;

    @Override
    public Course findCourseByName(String name) {
        return courseRepository.findByName(name)
                .orElseThrow(() -> new UserNotFoundException("cant find course by those name"));
    }


    //TODO
    @Override
    public Course createCourse(String name, List<String> tags, String description, List<String> creators) {
    return null;
    }

}