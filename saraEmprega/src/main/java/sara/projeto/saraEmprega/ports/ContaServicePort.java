package sara.projeto.saraEmprega.ports;

import java.util.List;
import java.util.UUID;

import sara.projeto.saraEmprega.dto.ContaResponseDTO;

public interface ContaServicePort {
    ContaResponseDTO buscarPorID(UUID id);
    List<ContaResponseDTO> buscarTodasAsContas();
    void excluirConta(UUID id);
}
