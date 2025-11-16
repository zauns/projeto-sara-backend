package sara.projeto.saraEmprega.ports;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import sara.projeto.saraEmprega.model.Vaga;

public interface VagaRepositoryPort {
    Vaga save(Vaga vaga);
    Optional<Vaga> findById(UUID id);
    List<Vaga> findAll();
    List<Vaga> findByEmpresaId(UUID empresaId);
    boolean existsById(UUID id);
    void delete(UUID id);
}
