package sara.projeto.saraEmprega.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import sara.projeto.saraEmprega.model.Empresa;
import sara.projeto.saraEmprega.model.Secretaria;

public interface SecretariaRepository extends JpaRepository<Secretaria, UUID> {
    Optional<Secretaria> findByEmail(String email);
    List<Secretaria> findByIsValidadaFalse();
}
