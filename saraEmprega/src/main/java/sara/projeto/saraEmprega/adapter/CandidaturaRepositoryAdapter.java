package sara.projeto.saraEmprega.adapter;

import sara.projeto.saraEmprega.repository.CandidaturaRepository;
import sara.projeto.saraEmprega.enums.StatusCandidatura;
import sara.projeto.saraEmprega.model.Candidatura;
import sara.projeto.saraEmprega.ports.CandidaturaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CandidaturaRepositoryAdapter implements CandidaturaRepositoryPort {
    private final CandidaturaRepository candidaturaRepository;
    @Override
    public Candidatura save(Candidatura candidatura) {
        return candidaturaRepository.save(candidatura);
    }
    @Override
    public Optional<Candidatura> findById(UUID id) {
        return candidaturaRepository.findById(id);
    }
    @Override
    public List<Candidatura> findAll() {
        return candidaturaRepository.findAll();
    }
    @Override
    public List<Candidatura> findByVagaId(UUID vagaId) {
        return candidaturaRepository.findByVagaId(vagaId);
    }
    @Override
    public List<Candidatura> findByUserId(UUID userId) {
        return candidaturaRepository.findByUserId(userId);
    }
    @Override
    public boolean existsById(UUID id) {
        return candidaturaRepository.existsById(id);
    }
    @Override
    public void delete(UUID id) {
        candidaturaRepository.deleteById(id);
    }
    @Override
    public List<Candidatura> findByStatus(StatusCandidatura status) {
        return candidaturaRepository.findByStatus(status);
    }
    
    @Override
    public boolean existsByVagaIdAndUserIdAndStatusNotIn(UUID vagaId, UUID userId, List<StatusCandidatura> excludedStatuses) {
        return candidaturaRepository.existsByVagaIdAndUserIdAndStatusNotIn(vagaId, userId, excludedStatuses);
    }
}
    