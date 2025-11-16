package sara.projeto.saraEmprega.ports;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.model.Conta;

public interface ContaServicePort {
    ContaResponseDTO buscarPorId(UUID id);
    Optional<Conta> buscarPorEmail(String email);
    List<ContaResponseDTO> buscarTodasAsContas();
    void excluirConta(UUID id);
}
