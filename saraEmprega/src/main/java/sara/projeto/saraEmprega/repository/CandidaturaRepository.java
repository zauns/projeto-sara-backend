package sara.projeto.saraEmprega.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import sara.projeto.saraEmprega.model.Candidatura;

public interface CandidaturaRepository extends JpaRepository<Candidatura, UUID> {
    List<Candidatura> findByVagaId(UUID vagaId);
    List<Candidatura> findByUserId(UUID userId);
}
