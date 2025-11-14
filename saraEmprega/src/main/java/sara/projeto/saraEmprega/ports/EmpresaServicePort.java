package sara.projeto.saraEmprega.ports;

import java.util.UUID;

import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.dto.EmpresaRequestDTO;

public interface EmpresaServicePort extends ContaServicePort {
    ContaResponseDTO criar(EmpresaRequestDTO dto);
    ContaResponseDTO atualizar(UUID id, EmpresaRequestDTO dto);
}
