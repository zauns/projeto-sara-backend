package sara.projeto.saraEmprega.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sara.projeto.saraEmprega.dto.CandidaturaRequestDTO;
import sara.projeto.saraEmprega.dto.CandidaturaResponseDTO;
import sara.projeto.saraEmprega.enums.StatusCandidatura;
import sara.projeto.saraEmprega.exception.VagaInativaException;
import sara.projeto.saraEmprega.model.Candidatura;
import sara.projeto.saraEmprega.model.User;
import sara.projeto.saraEmprega.model.Vaga;
import sara.projeto.saraEmprega.ports.CandidaturaRepositoryPort;
import sara.projeto.saraEmprega.ports.CandidaturaServicePort;
import sara.projeto.saraEmprega.ports.UserRepositoryPort;
import sara.projeto.saraEmprega.ports.VagaRepositoryPort;

// TODO: Implementar os métodos da interface
@Service
@RequiredArgsConstructor
public class CandidaturaService implements CandidaturaServicePort {

    // Ports
    private final CandidaturaRepositoryPort candidaturaRepositoryPort;
    private final VagaRepositoryPort vagaRepositoryPort; 
    private final UserRepositoryPort userRepositoryPort; 

    @Override
    public CandidaturaResponseDTO criar(CandidaturaRequestDTO dto) {
        
    }

    @Override
    public CandidaturaResponseDTO atualizarStatus(UUID id, StatusCandidatura status) {
    }

    @Override
    public CandidaturaResponseDTO desistir(UUID candidaturaId, UUID userId) {

    }

    @Override
    public CandidaturaResponseDTO buscarPorId(UUID id) {

    }

    @Override
    public List<CandidaturaResponseDTO> buscarPorUserId(UUID userId) {
    }

    @Override
    public List<CandidaturaResponseDTO> buscarPorVagaId(UUID vagaId) {
    }

    @Override
    public List<CandidaturaResponseDTO> buscarPorStatus(StatusCandidatura status) {
    }

    //MÉTODOS AUXILIARES
    private void mapear(CandidaturaRequestDTO dto, Candidatura candidatura) {
        Vaga vaga = vagaRepositoryPort.findById(dto.vagaId())
            .orElseThrow(() -> new IllegalArgumentException("Vaga não encontrada com o ID: " + dto.vagaId()));
        
        User user = userRepositoryPort.getUserById(dto.userId())
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + dto.userId()));

        if (!vaga.isAtiva()) {
            throw new VagaInativaException("A vaga não está ativa para candidaturas.");
        }
        candidatura.setVaga(vaga);
        candidatura.setUser(user);
        candidatura.setStatus(StatusCandidatura.PENDENTE);
    }
}
