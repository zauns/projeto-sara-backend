package sara.projeto.saraEmprega.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import sara.projeto.saraEmprega.model.Conta;

public interface ContaRepository<T extends Conta > extends JpaRepository<T, UUID> {
    Optional<T> findByEmail(String email);
}
