package sara.projeto.saraEmprega.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "vagas")
@AllArgsConstructor
@NoArgsConstructor
public class Vaga {

    @Id
    @GeneratedValue
    private UUID id;

    private String titulo;
    private String descricao;
     
    // Relacionamento Many-to-One com Empresa
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false) // coluna com chave estrangeira
    private Empresa empresa; // Empresa que oferece a vaga
}
