package sara.projeto.saraEmprega.ports;
import sara.projeto.saraEmprega.model.Candidatura;

import java.util.UUID;

import sara.projeto.saraEmprega.dto.CandidaturaResponseDTO;
import sara.projeto.saraEmprega.enums.StatusCandidatura;
public interface CandidaturaServicePort {
    CandidaturaResponseDTO criar(Candidatura candidatura);
    CandidaturaResponseDTO atualizarStatus(UUID id, StatusCandidatura status);
    
}
