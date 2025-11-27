package sara.projeto.saraEmprega.ports;

import java.util.UUID;

import sara.projeto.saraEmprega.dto.AdministradorRequestDTO;
import sara.projeto.saraEmprega.dto.ContaResponseDTO;

public interface AdministradorServicePort extends ContaServicePort {

    ContaResponseDTO criar(AdministradorRequestDTO dto);
    AdministradorRequestDTO getDados(UUID id);
    ContaResponseDTO atualizar(UUID id, AdministradorRequestDTO dto);

}
