package sara.projeto.saraEmprega.ports;

import java.util.UUID;

import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.dto.SecretariaRequestDTO;

public interface SecretariaServicePort extends ContaServicePort {
    ContaResponseDTO criar(SecretariaRequestDTO dto);

    ContaResponseDTO atualizar(UUID id, SecretariaRequestDTO dto);
}
