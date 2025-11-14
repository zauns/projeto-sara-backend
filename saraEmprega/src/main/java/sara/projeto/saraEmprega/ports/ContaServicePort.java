package sara.projeto.saraEmprega.ports;

import java.util.List;

import sara.projeto.saraEmprega.dto.ContaResponseDTO;

public interface ContaServicePort {
    ContaResponseDTO encontrarPorId();
    List<ContaResponseDTO> encontrarTudo();
    void deletar();
}
