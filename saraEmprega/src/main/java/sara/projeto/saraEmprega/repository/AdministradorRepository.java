package sara.projeto.saraEmprega.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import sara.projeto.saraEmprega.model.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, UUID> {
}
