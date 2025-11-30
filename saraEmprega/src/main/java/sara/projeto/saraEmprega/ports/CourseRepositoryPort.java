package sara.projeto.saraEmprega.ports;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import sara.projeto.saraEmprega.exception.UserNotFoundException;
import sara.projeto.saraEmprega.model.Course;
import sara.projeto.saraEmprega.repository.CourseRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class CourseRepositoryPort {
    private final CourseRepository courseRepository;

    public List<Course> findByName(String name) {
        List<Course> toReturn = courseRepository.findCoursesByNameIgnoreCase(name);
        if (toReturn.isEmpty()) throw new UserNotFoundException("No one Course Has This Name");
        return toReturn;
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
                .rating(0.0)
                .numberOfReviews(0)
                .build();

        courseRepository.save(course);
        return course;
    }

    public Course findById(UUID id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) throw new NoSuchElementException("No Course Found");
        return course.get();
    }

    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }
}