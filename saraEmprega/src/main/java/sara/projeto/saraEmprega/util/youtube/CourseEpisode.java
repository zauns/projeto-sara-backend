package sara.projeto.saraEmprega.util.youtube;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CourseEpisode {
    private String link;
    private String title;
    private int number;
}
