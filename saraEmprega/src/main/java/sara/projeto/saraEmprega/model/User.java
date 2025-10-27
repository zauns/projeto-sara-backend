package sara.projeto.saraEmprega.model;

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
<<<<<<< HEAD:saraEmprega/src/main/java/sara/projeto/saraEmprega/model/User.java
public class User { // depois faz o User extends Conta e apaga os atributos repetidos
    @Id
    private UUID id = UUID.randomUUID();
    private String firstName;
    private String email;
    private String hashedPassword;
=======
@AllArgsConstructor
@SuperBuilder
public class User extends Account {
>>>>>>> 06f735752fbcc028c83f7c3fb527063abf02ce34:ms-users/src/main/java/sara/emprega/msusers/model/User.java

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

