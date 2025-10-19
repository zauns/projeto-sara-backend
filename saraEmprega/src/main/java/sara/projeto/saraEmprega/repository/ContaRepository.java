package sara.projeto.saraEmprega.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import sara.projeto.saraEmprega.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, UUID> {}
