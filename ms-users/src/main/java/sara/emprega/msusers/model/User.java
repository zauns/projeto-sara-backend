package sara.emprega.msusers.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private Curriculum curriculum;

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
        if (curriculum != null) {
            curriculum.setUser(this);
        }
    }
}

