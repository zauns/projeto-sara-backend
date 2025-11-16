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
    private Curriculum curriculum;

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
        if (curriculum != null) {
            curriculum.setUser(this);
        }
    }
}
