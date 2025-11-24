package sara.projeto.saraEmprega.service;

import java.util.List;
import java.util.UUID;
import java.util.Collections;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import sara.projeto.saraEmprega.dto.CandidaturaRequestDTO;
import sara.projeto.saraEmprega.dto.CandidaturaResponseDTO;
import sara.projeto.saraEmprega.enums.StatusCandidatura;
import sara.projeto.saraEmprega.exception.DuplicidadeCandidaturaException;
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
    @Transactional
    public CandidaturaResponseDTO criar(CandidaturaRequestDTO dto) {

        List<StatusCandidatura> statusQuePermitemReaplicacao = Collections.singletonList(StatusCandidatura.REJEITADA);
        if (candidaturaRepositoryPort.existsByVagaIdAndUserIdAndStatusNotIn(
                dto.vagaId(), 
                dto.userId(), 
                statusQuePermitemReaplicacao
            )) {
            throw new DuplicidadeCandidaturaException("Usuário já possui uma candidatura ativa para esta vaga.");
        }

        Candidatura candidatura = new Candidatura();
        mapear(dto, candidatura);
        
        Candidatura novaCandidatura = candidaturaRepositoryPort.save(candidatura);
        return new CandidaturaResponseDTO(novaCandidatura);
    }

    @Override
    public CandidaturaResponseDTO atualizarStatus(UUID id, StatusCandidatura status) {
        Candidatura candidatura = candidaturaRepositoryPort.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Candidatura não encontrada com o ID: " + id));
        
        candidatura.setStatus(status);
        Candidatura candidaturaAtualizada = candidaturaRepositoryPort.save(candidatura);
        return new CandidaturaResponseDTO(candidaturaAtualizada);
    }

    //DESISTIR DA CANDIDATURA
    @Override
    @Transactional
    public CandidaturaResponseDTO desistir(UUID candidaturaId, UUID userId) {

        Candidatura candidatura = candidaturaRepositoryPort.findById(candidaturaId)
            .orElseThrow(() -> new EntityNotFoundException("Candidatura não encontrada com o ID: " + candidaturaId));
        
        if (!candidatura.getUser().getId().equals(userId)) {
             throw new IllegalStateException("Ação não permitida: A candidatura pertence a outro usuário."); 
        }

        if (candidatura.getStatus() != StatusCandidatura.PENDENTE) {
             throw new IllegalStateException("Não é permitido desistir de candidaturas que não estejam em status PENDENTE. Status atual: " + candidatura.getStatus()); 
        }

        candidaturaRepositoryPort.delete(candidaturaId);
        return new CandidaturaResponseDTO(candidatura); 
    }

    //BUSCAR CANDIDATURA POR ID
    @Override
    @Transactional(readOnly = true)
    public CandidaturaResponseDTO buscarPorId(UUID id) {
        Candidatura candidatura = candidaturaRepositoryPort.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Candidatura não encontrada com o ID: " + id));
        return new CandidaturaResponseDTO(candidatura);
    }

    //BUSCAR CANDIDATURAS POR USER ID
    @Override
    @Transactional(readOnly = true)
    public List<CandidaturaResponseDTO> buscarPorUserId(UUID userId) {
        if(!userRepositoryPort.existsById(userId)) {
            throw new EntityNotFoundException("Usuário não encontrado com o ID: " + userId);
        }
        return candidaturaRepositoryPort.findByUserId(userId)
            .stream()
            .map(CandidaturaResponseDTO::new)
            .toList();
    }

    //BUSCAR CANDIDATURAS POR VAGA ID
    @Override
    @Transactional(readOnly = true)
    public List<CandidaturaResponseDTO> buscarPorVagaId(UUID vagaId) {
        if(!vagaRepositoryPort.existsById(vagaId)) {
            throw new EntityNotFoundException("Vaga não encontrada com o ID: " + vagaId);
        }
        return candidaturaRepositoryPort.findByVagaId(vagaId)
            .stream()
            .map(CandidaturaResponseDTO::new)
            .toList();
    }

    //BUSCAR CANDIDATURAS POR STATUS
    @Override
    @Transactional(readOnly = true)
    public List<CandidaturaResponseDTO> buscarPorStatus(StatusCandidatura status) {
        return candidaturaRepositoryPort.findByStatus(status)
            .stream()
            .map(CandidaturaResponseDTO::new)
            .toList();
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
