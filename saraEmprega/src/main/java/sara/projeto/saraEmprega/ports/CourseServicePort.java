package sara.projeto.saraEmprega.ports;

import sara.projeto.saraEmprega.model.Course;
import java.util.List;
public interface CourseServicePort {

    List<Course> findCourseByName(String courseName);

    Course createCourse(
            String name,
            List<String> tags,
            String description,
            List<String> creators
    );
}
