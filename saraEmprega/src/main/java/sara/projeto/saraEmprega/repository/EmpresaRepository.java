package sara.projeto.saraEmprega.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import sara.projeto.saraEmprega.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {
     Optional<Empresa> findByEmail(String email);
     List<Empresa> findByIsValidadaFalse();
}
