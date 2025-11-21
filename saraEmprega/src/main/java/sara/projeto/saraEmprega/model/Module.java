package sara.projeto.saraEmprega.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sara.projeto.saraEmprega.util.youtube.CourseEpisode;
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
    @ElementCollection(fetch = FetchType.LAZY)
    private List<CourseEpisode> contentLinks = new ArrayList<>();
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Course course;

    public void addDocument(Document document) {
        documents.add(document);
        document.setModule(this);
    }

    public void removeDocument(Document document) {
        documents.remove(document);
        document.setModule(null);
    }
}
