package sara.projeto.saraEmprega.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import sara.projeto.saraEmprega.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, UUID> {
    Optional<Conta> findByEmail(String email);
}
