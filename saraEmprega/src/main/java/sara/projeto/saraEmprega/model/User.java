package sara.projeto.saraEmprega.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends Conta {

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Document document;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) 
    private Set<Candidatura> candidaturas;

    public void setDocument(Document doc) {
        this.document = doc;
        if (document != null) {
            document.setUser(this);
        }
    }
}
