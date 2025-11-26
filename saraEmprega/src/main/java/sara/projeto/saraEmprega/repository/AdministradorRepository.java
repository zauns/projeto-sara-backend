package sara.projeto.saraEmprega.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import sara.projeto.saraEmprega.model.Administrador;

public interface AdministradorRepository extends ContaRepository<Administrador> {
    Optional<Administrador> findByEmail(String email);
}
