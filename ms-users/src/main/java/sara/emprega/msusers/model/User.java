package sara.emprega.msusers.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends Account {

    String firstName;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Document document;

    public void setDocument(Document doc) {
        this.document = doc;
        if (document != null) {
            document.setUser(this);
        }
    }
}

