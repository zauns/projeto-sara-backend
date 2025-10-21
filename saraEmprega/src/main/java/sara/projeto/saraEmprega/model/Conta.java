package sara.projeto.saraEmprega.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_conta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Conta {

    @Id
    @GeneratedValue
    private UUID id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senha;
    private String endereco;
    private String telefone;
}
