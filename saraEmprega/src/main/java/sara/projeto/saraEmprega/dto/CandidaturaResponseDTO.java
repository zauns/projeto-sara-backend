package sara.projeto.saraEmprega.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sara.projeto.saraEmprega.enums.StatusCandidatura;
import sara.projeto.saraEmprega.model.Candidatura;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidaturaResponseDTO {
    private VagaResponseDTO vaga;
    private ContaResponseDTO user;
    private StatusCandidatura status;

    public CandidaturaResponseDTO(Candidatura candidatura) {
        this.vaga = new VagaResponseDTO(candidatura.getVaga());
        this.user = new ContaResponseDTO(candidatura.getUser());
        this.status = candidatura.getStatus();
    }
    
}
