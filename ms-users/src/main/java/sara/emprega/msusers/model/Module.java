package sara.emprega.msusers.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Module {
    @Id
    private UUID uuid = UUID.randomUUID();
    private String name;
    private int length;
    @ElementCollection
    private List<String> contentLinks = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}