package sara.projeto.saraEmprega.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sara.projeto.saraEmprega.model.Conta;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaResponseDTO {

    private UUID id;
    private String nome;
    private String email;
    private String tipoConta;

    public ContaResponseDTO(Conta conta) {
        this.id = conta.getId();
        this.nome = conta.getNome();
        this.email = conta.getEmail();
        this.tipoConta = conta.getClass().getSimpleName();
    }
}
