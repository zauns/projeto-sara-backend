package sara.projeto.saraEmprega.ports;

import sara.projeto.saraEmprega.dto.CandidaturaRequestDTO; // 1. NOVO IMPORT
import sara.projeto.saraEmprega.dto.CandidaturaResponseDTO;
import sara.projeto.saraEmprega.enums.StatusCandidatura;

import java.util.List;
import java.util.UUID;

public interface CandidaturaServicePort {

    CandidaturaResponseDTO criar(CandidaturaRequestDTO dto); 
    CandidaturaResponseDTO atualizarStatus(UUID id, StatusCandidatura status);
    CandidaturaResponseDTO desistir(UUID candidaturaId, UUID userId); 
    CandidaturaResponseDTO buscarPorId(UUID id);
    List<CandidaturaResponseDTO> buscarPorUserId(UUID userId);
    List<CandidaturaResponseDTO> buscarPorVagaId(UUID vagaId);
    List<CandidaturaResponseDTO> buscarPorStatus(StatusCandidatura status);
    
}