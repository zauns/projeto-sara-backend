package sara.projeto.saraEmprega.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
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

    public void setDocument(Document doc) {
        this.document = doc;
        if (document != null) {
            document.setUser(this);
        }
    }
}
