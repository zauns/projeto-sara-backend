package sara.projeto.saraEmprega.ports;

import java.util.List;
import java.util.UUID;

import sara.projeto.saraEmprega.dto.ContaResponseDTO;
import sara.projeto.saraEmprega.dto.EmpresaRequestDTO;

public interface EmpresaServicePort extends ContaServicePort {
    List<ContaResponseDTO> getEmpresasNaoValidadas();
    ContaResponseDTO aprovarEmpresa(UUID id);
    ContaResponseDTO criar(EmpresaRequestDTO dto);
    ContaResponseDTO atualizar(UUID id, EmpresaRequestDTO dto);
    EmpresaRequestDTO getDados(UUID id);
}
