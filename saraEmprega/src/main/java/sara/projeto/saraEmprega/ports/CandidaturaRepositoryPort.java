package sara.projeto.saraEmprega.ports;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import sara.projeto.saraEmprega.enums.StatusCandidatura;
import sara.projeto.saraEmprega.model.Candidatura;

public interface CandidaturaRepositoryPort {
    Candidatura save(Candidatura candidatura);
    Optional <Candidatura> findById(UUID id);
    List<Candidatura> findAll();
    List<Candidatura> findByVagaId(UUID vagaId);
    List<Candidatura> findByUserId(UUID userId);
    List<Candidatura> findByStatus(StatusCandidatura status);
    boolean existsById(UUID id);
    void delete(UUID id);
}
