package sara.emprega.msusers.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @Builder.Default
    private UUID courseId = UUID.randomUUID();
    String name;
    @ElementCollection
    List<String> tags;
    String description;
    int length;
    @ElementCollection
    List<String> creators;
    int rating;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Module> modules = new ArrayList<>();
}
