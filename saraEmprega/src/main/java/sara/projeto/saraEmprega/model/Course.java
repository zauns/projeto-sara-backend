package sara.projeto.saraEmprega.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @ElementCollection(fetch = FetchType.LAZY)
    List<String> tags;
    String description;
    int length;
    @ElementCollection(fetch = FetchType.LAZY)
    List<String> creators;
    Double rating ;
    int numberOfReviews = 0;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Document> docs = new ArrayList<>();
}
