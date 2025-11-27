package sara.projeto.saraEmprega.ports;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import sara.projeto.saraEmprega.model.Course;
import sara.projeto.saraEmprega.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CourseRepositoryPort {
    private final CourseRepository courseRepository;

    public Optional<Course> findByName(String name) {
        return Optional.ofNullable(courseRepository.findByCourseName(name));
    }
    public Course createCourse(
            String name,
            List<String> tags,
            String description,
            List<String> creators
    ) {
        Course course =Course.builder()
                .name(name)
                .tags(tags)
                .description(description)
                .creators(creators)
                .rating(0)
                .build();

        courseRepository.save(course);
        return course;
    }

    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }
}