package sara.projeto.saraEmprega.repository;

import java.util.List;
import java.util.Optional;

import sara.projeto.saraEmprega.model.Secretaria;

public interface SecretariaRepository extends ContaRepository<Secretaria> {
    Optional<Secretaria> findByEmail(String email);
    List<Secretaria> findByIsValidadaFalse();
}
