package sara.projeto.saraEmprega.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import sara.projeto.saraEmprega.model.Vaga;

public interface VagaRepository extends JpaRepository<Vaga, UUID> {
    List<Vaga> findByEmpresaId(UUID empresaId);
    List<Vaga> findAllByTagsIn(List<String> tags);
    List<Vaga> findAllByTagsContaining(String tag);
}
