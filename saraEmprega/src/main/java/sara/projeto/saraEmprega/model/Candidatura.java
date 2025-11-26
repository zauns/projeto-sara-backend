package sara.projeto.saraEmprega.model;
import sara.projeto.saraEmprega.enums.StatusCandidatura;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "candidaturas")
public class Candidatura {

    @Id
    @GeneratedValue
    private UUID id;

    StatusCandidatura status;

    //Relacionamento Many-to-One com Vaga
    @ManyToOne
    @JoinColumn(name = "vaga_id", nullable = false)
    private Vaga vaga; // Vaga para a qual o usuário se candidatou

    //Relacionamento Many-to-One com User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true) // candidatura pode existir sem usuário para manter histórico, mas na criação deve ter usuário
    private User user; // Usuário que fez a candidatura
    
}
